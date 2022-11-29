package com.dldmswo1209.portfolio

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.dldmswo1209.portfolio.KeyboardState.KeyboardVisibilityUtils
import com.dldmswo1209.portfolio.Model.RealChat
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.adapter.RealChatListAdapter
import com.dldmswo1209.portfolio.databinding.ActivityChatBinding
import com.dldmswo1209.portfolio.fcm.MyFirebaseMessagingService.Companion.CHANNEL_ID
import com.dldmswo1209.portfolio.retrofitApi.PushBody
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import kotlinx.coroutines.*
import java.time.LocalDateTime
// 채팅방 화면
class ChatActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityChatBinding.inflate(layoutInflater)
    }
    private lateinit var sender: User
    private lateinit var receiver: User
    private var key = ""
    private val viewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private var chatList = mutableListOf<RealChat>()
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils
    private lateinit var pref : SharedPreferences
    private var uid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getSharedPreferences("isChatting", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("isChatting", true) // 채팅 중임
            .apply()

        sender = intent.getSerializableExtra("sender") as User // 채용 담당자
        receiver = intent.getSerializableExtra("receiver") as User // 채용인
        key = intent.getStringExtra("key").toString()

        pref = getSharedPreferences("login", Context.MODE_PRIVATE)
        uid = pref.getString("uid","").toString()

        binding.titleTextView.text = receiver.name

        val chatAdapter = RealChatListAdapter(sender)
        binding.chatRecyclerView.adapter = chatAdapter

        viewModel.getAllChat(key).observe(this){
            // 이렇게 안하면, it.size 와 chatAdapter.currentList.size 가 달라서
            // 리사이클러뷰를 스크롤 할 수 없음..
            CoroutineScope(Dispatchers.Main).launch{
                async {
                    chatAdapter.submitList(it) // submitList 하는데 시간이 오래걸리는건가..?
                    delay(100)
                }.await()
                binding.chatRecyclerView.scrollToPosition(it.size-1)
            }

            chatList = it
        }

        binding.inputEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // editText 가 비어있으면 sendButton 을 비활성화함
                binding.sendButton.isEnabled = binding.inputEditText.text.isNotBlank()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.sendButton.setOnClickListener {
            var chat: RealChat
            val message = binding.inputEditText.text.toString()
            val localDateTime = LocalDateTime.now().toString().split("T")
            val date = localDateTime[0]
            val time = localDateTime[1].split(":")
            val hour = time[0]
            val min = time[1]
            var realTime = ""
            if(hour.toInt() >= 12){
                realTime = "$date \n오후 ${hour.toInt()-12}:$min"
            }else{
                realTime = "$date \n오전 $hour:$min"
            }

            if(uid == sender.uid){
                chat = RealChat(message, realTime, sender, receiver, key)
            }else{
                chat = RealChat(message, realTime, receiver, sender, key)
            }
            viewModel.sendMessage(chat,key)

            viewModel.getUser(chat.receiver.uid).observe(this){ receiver->
                // chat.receiver.token 값이 아니라 유저 데이터 자체에 저장되어 있는 token 값을 사용
                // 푸시 메세지 버그를 해결하기 위한 해결 방안1
                val pushBody = PushBody(receiver.token, chat.sender.name, chat.message)
                Log.d("testt", "push body: ${pushBody}")
                viewModel.sendPushMessage(pushBody)
            }

            binding.inputEditText.text.clear()
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        keyboardVisibilityUtils = KeyboardVisibilityUtils(window,
            onShowKeyboard = { // 키보드가 올라왔을 때
                binding.chatRecyclerView.scrollToPosition(chatList.size-1) // 리사이클러 뷰 맨 밑으로 스크롤
            },
            onHideKeyboard = {}
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        getSharedPreferences("isChatting", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("isChatting", false) // 채팅방 나감
            .apply()
    }

}