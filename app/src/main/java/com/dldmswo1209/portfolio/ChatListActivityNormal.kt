package com.dldmswo1209.portfolio

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.adapter.ChatRoomAdapter
import com.dldmswo1209.portfolio.databinding.ActivityChatListNormalBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel
// 일반 사용자를 위한 채팅방 목록 화면
class ChatListActivityNormal : AppCompatActivity() {
    private val binding by lazy{
        ActivityChatListNormalBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    lateinit var currentUser : User
    var uid = ""
    var superUid = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        uid = sharedPreferences.getString("uid","").toString()
        currentUser = intent.getSerializableExtra("currentUser") as User

        val sharedPreferences2 = getSharedPreferences("superMode", Context.MODE_PRIVATE)
        superUid = sharedPreferences2.getString("showUid","").toString()

        val roomAdapter = ChatRoomAdapter(currentUser){
            // 채팅방 클릭 이벤트 처리
            val intent = Intent(this, ChatActivity::class.java)
            // 채용 담당자가 채팅방을 생성해야만 채팅방이 생성됨
            // 그러므로 it.sender 에는 채용담당자가, it.receiver 에는 채용인이 저장되어 있음
            if(superUid != "") { // 채용 담당자가 보고있는 경우
                intent.putExtra("sender", it.sender)
                intent.putExtra("receiver", it.receiver)
            }else{ // 일반 사용자
                intent.putExtra("sender", it.receiver)
                intent.putExtra("receiver", it.sender)
            }
            intent.putExtra("key", it.key)
            startActivity(intent)
        }
        binding.roomRecyclerView.adapter = roomAdapter
        viewModel.getChatRooms(currentUser).observe(this){
            roomAdapter.submitList(it)
        }

    }
}