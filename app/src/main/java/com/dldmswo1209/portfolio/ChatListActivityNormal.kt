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

class ChatListActivityNormal : AppCompatActivity() {
    private val binding by lazy{
        ActivityChatListNormalBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    lateinit var currentUser : User
    var uid = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        uid = sharedPreferences.getString("uid","").toString()
        currentUser = intent.getSerializableExtra("currentUser") as User

        val roomAdapter = ChatRoomAdapter(currentUser){
            // 채팅방 클릭 이벤트 처리
//            sender = intent.getSerializableExtra("sender") as User
//            receiver = intent.getSerializableExtra("receiver") as User
//            key = intent.getStringExtra("key").toString()

            val intent = Intent(this, ChatActivity::class.java)
            // 채용 담당자가 채팅방을 생성해야만 채팅방이 생성됨
            // 그러므로 it.sender 에는 채용담당자가, it.receiver 에는 채용인이 저장되어 있음
            intent.putExtra("sender", it.receiver)
            intent.putExtra("receiver", it.sender)
            intent.putExtra("key", it.key)
            startActivity(intent)
        }
        binding.roomRecyclerView.adapter = roomAdapter
        viewModel.getChatRooms(currentUser).observe(this){
            roomAdapter.submitList(it)
        }

    }
}