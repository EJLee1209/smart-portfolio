package com.dldmswo1209.portfolio.repository

import android.content.Context
import android.net.Uri
import android.os.Message
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dldmswo1209.portfolio.Model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.FileNotFoundException

class Repository() {
    val database = Firebase.database.reference
    val storage = FirebaseStorage.getInstance().reference

    // 모든 일반 사용자 정보 가져오기(채용 담당자 제외)
    fun getAllUser() : LiveData<MutableList<User>>{
        val userList = MutableLiveData<MutableList<User>>()

        val dbRef = database.child("User")
        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = mutableListOf<User>()
                if(snapshot.exists()){
                    snapshot.children.forEach { data->
                        val user = data.getValue(User::class.java)?:return@forEach
                        if(!user.isSuperUser) // 일반 사용자만
                            dataList.add(user)
                    }
                }
                userList.postValue(dataList)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        return userList
    }

    // 유저 정보 가져오기
    fun getUser(uid: String): LiveData<User>{
        val user = MutableLiveData<User>()

        val dbRef = database.child("User/${uid}")
        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    user.value = snapshot.getValue(User::class.java)
                }
            }
            override fun onCancelled(error: DatabaseError) {}

        })

        return user
    }

    // 프로필 수정하기
    fun updateUser(user: User, imageUri: Uri?){
        Log.d("testt", "updateUser: ${imageUri}")
        if(imageUri == null){ // 프로필 사진이 아예 없음
            val mapData = mapOf<String, Any?>(
                "name" to user.name,
                "profile" to user.profile
            )
            database.child("User/${user.uid}").updateChildren(mapData)
        }
        else{
            val imgFileName = "${imageUri.lastPathSegment}.png"
            val imagePath = "Profile_Images/${user.uid}/${imgFileName}"

            storage.child(imagePath).putFile(imageUri) // 이미지 업로드
                .addOnSuccessListener {
                    Log.d("testt", "updateUser imagePath: ${user.profile!!.imageUri}")
                    storage.child(imagePath).downloadUrl.addOnSuccessListener { uri ->
                        // 이미지 다운로드
                        Log.d("testt", "download image : ${uri}")
                        val newProfile = user
                        newProfile.profile?.image = uri.toString() // 다운로드 받은 이미지 uri 저장

                        val mapData = mapOf<String, Any?>(
                            "name" to newProfile.name,
                            "profile" to newProfile.profile
                        )
                        database.child("User/${newProfile.uid}").updateChildren(mapData) // 새로운 데이터 저장
                    }
                }
        }
    }

    fun updatePrivacyInfo(uid: String, profile: UserProfile){
        database.child("User/${uid}/profile").setValue(profile)
    }

    // 타임라인 가져오기
    fun getTimeLine(uid: String) : LiveData<MutableList<TimeLine>> {
        val timeLines = MutableLiveData<MutableList<TimeLine>>()

        database.child("Portfolio/${uid}/TimeLine")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataList = mutableListOf<TimeLine>()
                    if(snapshot.exists()){
                        snapshot.children.forEach {
                            val data = it.getValue(TimeLine::class.java) ?: return@forEach
                            dataList.add(data)
                        }
                    }
                    timeLines.postValue(dataList)
                }
                override fun onCancelled(error: DatabaseError) {}
            })

        return timeLines
    }

    // 타임라인 생성
    fun createTimeLine(uid: String, timeLine: TimeLine){
        val db = database.child("Portfolio/${uid}/TimeLine").push()
        val key = db.key // 새로운 키 생성

        timeLine.key = key.toString()
        db.setValue(timeLine)
    }

    // 타임라인 삭제
    fun deleteTimeLine(uid: String, key: String){
        database.child("Portfolio/${uid}/TimeLine/${key}").removeValue()
    }

    // 타임라인 수정
    fun updateTimeLine(uid: String, timeLine: TimeLine){
        val updateData = mapOf<String, Any?>(
            "content" to timeLine.content,
            "date" to timeLine.date,
            "selected" to false
        )

        database.child("Portfolio/${uid}/TimeLine/${timeLine.key}").updateChildren(updateData)
    }

    fun getChat(uid: String): LiveData<MutableList<Chat>> {
        val chatList = MutableLiveData<MutableList<Chat>>()

        database.child("Portfolio/${uid}/Chat")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataList = mutableListOf<Chat>()
                    if(snapshot.exists()){
                        snapshot.children.forEach {
                            val data = it.getValue(Chat::class.java) ?: return@forEach
                            dataList.add(data)
                        }
                    }
                    chatList.postValue(dataList)
                }
                override fun onCancelled(error: DatabaseError) {}
            })

        return chatList
    }

    // 채팅 포트폴리오 아이템 생성
    fun sendChat(uid: String, chat: Chat){
        val db = database.child("Portfolio/${uid}/Chat").push()
        val key = db.key // 새로운 키 생성

        chat.key = key.toString()
        db.setValue(chat)
    }

    // 채팅 삭제
    fun deleteChat(uid: String, key: String){
        database.child("Portfolio/${uid}/Chat/${key}").removeValue()
    }

    // 채팅 수정
    fun modifyChat(uid: String, chat: Chat){
        database.child("Portfolio/${uid}/Chat/${chat.key}").setValue(chat)
    }

    // 채팅 새로고침
    fun refreshChat(uid: String){
        val db = database.child("Portfolio/${uid}/Chat").push()
        val key = db.key.toString() // 새로운 키 생성

        val dummyChat = Chat("",0,key)
        db.setValue(dummyChat)
            .addOnSuccessListener {
                db.removeValue()
            }
    }

    // 카드 가져오기
    fun getCard(uid: String) : LiveData<MutableList<Card>> {
        val cardList = MutableLiveData<MutableList<Card>>()

        database.child("Portfolio/${uid}/Card")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataList = mutableListOf<Card>()
                    if(snapshot.exists()){
                        snapshot.children.forEach {
                            val data = it.getValue(Card::class.java) ?: return@forEach
                            dataList.add(data)
                        }
                    }
                    cardList.postValue(dataList)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

        return cardList
    }

    // 카드 생성
    fun createCard(uid: String, card: Card){
        val db = database.child("Portfolio/${uid}/Card").push()
        val key = db.key // 새로운 키 생성
        card.key = key.toString()

        if(card.imageUri == null){ // 사진이 아예 없음
            db.setValue(card) // 새로운 데이터 저장
        }
        else{
            val imgFileName = "${card.imageUri?.toUri()?.lastPathSegment}.png"
            val imagePath = "Portfolio_Images/${uid}/${imgFileName}"

            storage.child(imagePath).putFile(card.imageUri!!.toUri()) // 이미지 업로드
                .addOnSuccessListener {
                    storage.child(imagePath).downloadUrl.addOnSuccessListener { uri ->
                        // 이미지 다운로드
                        val newCard = card
                        newCard.image = uri.toString() // 다운로드 받은 이미지 uri 저장
                        db.setValue(card) // 새로운 데이터 저장
                    }

                }
        }
    }

    // 카드 삭제
    fun deleteCard(uid: String, card: Card){
        database.child("Portfolio/${uid}/Card/${card.key}").removeValue()
        // 사진도 지워야 함
        if(card.imageUri != "" && card.imageUri != null){
            val imgFileName = "${card.imageUri?.toUri()?.lastPathSegment}.png"
            val imagePath = "Portfolio_Images/${uid}/${imgFileName}"
            storage.child(imagePath).delete()
        }
    }

    // 카드 수정
    fun updateCard(uid: String, card: Card){
        val db = database.child("Portfolio/${uid}/Card/${card.key}")

        if(card.imageUri == null){ // 사진이 아예 없음
            db.setValue(card) // 새로운 데이터 저장
        }
        else{
            val imgFileName = "${card.imageUri?.toUri()?.lastPathSegment}.png"
            val imagePath = "Portfolio_Images/${uid}/${imgFileName}"

            storage.child(imagePath).putFile(card.imageUri!!.toUri()) // 이미지 업로드
                .addOnSuccessListener {
                    storage.child(imagePath).downloadUrl.addOnSuccessListener { uri ->
                        // 이미지 다운로드
                        val newCard = card
                        newCard.image = uri.toString() // 다운로드 받은 이미지 uri 저장
                        db.setValue(card) // 새로운 데이터 저장
                    }
                }
                .addOnFailureListener{
                    // 다른 핸드폰에서 이미지 카드를 업로드 했는데
                    // 또 다른 핸드폰에서 수정하려 할 경우
                    // 해당 핸드폰에는 저장되어있는 Uri 경로를 통해 사진을 불러올 수 없어서 FileNotFoundException 발생
                    db.setValue(card)
                }
        }
    }

    // 내 모든 채팅방 가져오기
    fun getChatRooms(user: User): LiveData<MutableList<ChatRoom>> {
        val rooms = MutableLiveData<MutableList<ChatRoom>>()

        database.child("User/${user.uid}/chatRooms")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataList = mutableListOf<ChatRoom>()
                    if(snapshot.exists()){
                        snapshot.children.forEach {
                            val data = it.getValue(ChatRoom::class.java) ?: return@forEach
                            dataList.add(data)
                        }
                    }
                    rooms.postValue(dataList)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

        return rooms
    }

    fun getRoom(uid: String, key: String): LiveData<ChatRoom>{
        val room = MutableLiveData<ChatRoom>()

        database.child("User/${uid}/chatRooms/${key}")
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        room.postValue(snapshot.getValue(ChatRoom::class.java))
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        return room
    }

    // 채팅방 생성
    fun createChatRooms(sender: User, receiver: User): LiveData<String>{
        val createdRoomKey = MutableLiveData<String>()

        val path = "${sender.uid}_${receiver.uid}"
        val db = database.child("ChatRooms/${path}/info")

        val room = ChatRoom(sender, receiver, key = path)

        db.setValue(room).addOnCompleteListener {
            if(it.isSuccessful){ // 채팅방 생성
                // sender 와 receiver 에 채팅방 정보를 저장해줘야 함(path 저장)
                createdRoomKey.postValue(path)
                database.child("User/${sender.uid}/chatRooms/$path").setValue(room)
                database.child("User/${receiver.uid}/chatRooms/$path").setValue(room)
            }
        }

        return createdRoomKey
    }

    // 모든 채팅 리스트 가져오기
    fun getAllChat(key: String): LiveData<MutableList<RealChat>>{
        val chats = MutableLiveData<MutableList<RealChat>>()

        database.child("ChatRooms/${key}/comment")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataList = mutableListOf<RealChat>()
                    if(snapshot.exists()){
                        snapshot.children.forEach {
                            val data = it.getValue(RealChat::class.java) ?: return@forEach
                            dataList.add(data)
                        }
                    }
                    chats.postValue(dataList)
                }
                override fun onCancelled(error: DatabaseError) {}
            })

        return chats
    }

    // 메세지 보내기
    fun sendMessage(chat: RealChat, key: String){
        val db = database.child("ChatRooms/${key}/comment").push()
        val chatKey = db.key
        chat.key = chatKey.toString()

        db.setValue(chat)

        val updateData = mapOf<String, Any?>(
            "lastMessage" to chat.message,
            "lastTime" to chat.date_time
        )

        // 유저 데이터에도 정보를 업데이트 시켜줘야 함
        database.child("User/${chat.sender.uid}/chatRooms/$key").updateChildren(updateData)
        database.child("User/${chat.receiver.uid}/chatRooms/$key").updateChildren(updateData)

    }

}