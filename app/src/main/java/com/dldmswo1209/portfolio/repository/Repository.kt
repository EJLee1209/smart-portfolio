package com.dldmswo1209.portfolio.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dldmswo1209.portfolio.MainActivity
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.Model.UserProfile
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class Repository() {
    val database = Firebase.database
    val storage = FirebaseStorage.getInstance().reference

    // 유저 정보 가져오기
    fun getUser(uid: String): LiveData<User>{
        val user = MutableLiveData<User>()

        val dbRef = database.reference.child("User/${uid}")
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
            Log.d("testt", "updateUser: ${user}")
            database.reference.child("User/${user.uid}").setValue(user) // 새로운 데이터 저장
        }
        else{
            val imgFileName = "${imageUri.lastPathSegment}.png"
            val imagePath = "Profile_Images/${user.uid}/${imgFileName}.png"

            storage.child(imagePath).putFile(imageUri) // 이미지 업로드
                .addOnSuccessListener {
                    Log.d("testt", "updateUser imagePath: ${user.profile!!.imageUri}")
                    storage.child(imagePath).downloadUrl.addOnSuccessListener { uri ->
                        // 이미지 다운로드
                        Log.d("testt", "download image : ${uri}")
                        val newProfile = user
                        newProfile.profile?.image = uri.toString() // 다운로드 받은 이미지 uri 저장
                        database.reference.child("User/${newProfile.uid}").setValue(newProfile) // 새로운 데이터 저장

                    }

                }
        }
    }

    fun updatePrivacyInfo(uid: String, profile: UserProfile){
        database.reference.child("User/${uid}/profile").setValue(profile)
    }


}