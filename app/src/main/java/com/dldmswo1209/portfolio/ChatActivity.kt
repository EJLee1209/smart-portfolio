package com.dldmswo1209.portfolio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.dldmswo1209.portfolio.Model.RealChat
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.adapter.RealChatListAdapter
import com.dldmswo1209.portfolio.databinding.ActivityChatBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import java.time.LocalDateTime

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sender = intent.getSerializableExtra("sender") as User
        receiver = intent.getSerializableExtra("receiver") as User
        key = intent.getStringExtra("key").toString()

        val chatAdapter = RealChatListAdapter(sender)
        binding.chatRecyclerView.adapter = chatAdapter

        viewModel.getAllChat(key).observe(this){
            chatAdapter.submitList(it)
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
            val message = binding.inputEditText.text.toString()
            val date = LocalDateTime.now().toString()
            val chat = RealChat(message, date, sender, receiver, key)
            Log.d("testt", "send : ${message}")

            viewModel.sendMessage(chat,key)
            binding.inputEditText.text.clear()
        }
    }
}