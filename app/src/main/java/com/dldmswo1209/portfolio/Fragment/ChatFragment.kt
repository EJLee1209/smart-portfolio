package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.dldmswo1209.portfolio.adapter.ChatListAdapterModeManagement
import com.dldmswo1209.portfolio.adapter.ChatListAdapterModeOrigin
import com.dldmswo1209.portfolio.databinding.FragmentChatBinding
import com.dldmswo1209.portfolio.entity.ChatEntity
import com.dldmswo1209.portfolio.entity.MY_CHAT
import com.dldmswo1209.portfolio.entity.OTHER_CHAT
import com.dldmswo1209.portfolio.viewModel.MainViewModel

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var type = MY_CHAT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 모드 변경 버튼 클릭 이벤트
        binding.changeModeButton.setOnClickListener {
            type = if(type == MY_CHAT){
                binding.modeTextView.text = "관리자 모드"
                OTHER_CHAT
            } else{
                binding.modeTextView.text = "일반 모드"
                MY_CHAT
            }
            // ui 업데이트를 위해 모든 데이터를 다시 가져온다. -> 아이템이 서로 반대로 뒤바뀌게 됨
            viewModel.getAllChat()
        }

        binding.inputEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // editText 가 비어있으면 sendButton 을 비활성화함
                binding.sendButton.isEnabled = !binding.inputEditText.text.isBlank()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.sendButton.setOnClickListener {
            // 보내기 버튼 클릭 이벤트
            val message = binding.inputEditText.text.toString()
            // 데이터 삽입
            viewModel.insertChat(ChatEntity(0,message,type))
            binding.inputEditText.text.clear()
            // 모든 채팅 데이터 가져오기
            viewModel.getAllChat()
        }

        // 모든 데이터 가져오기(초기화)
        viewModel.getAllChat()

        // LiveData 관찰자
        viewModel.chatList.observe(viewLifecycleOwner, Observer {
            if(type == MY_CHAT){// 현재 type 이 MY_CHAT 이라면
                val chatListAdapter = ChatListAdapterModeOrigin() // 원래의 어답터를 적용하면 됨
                binding.chatRecyclerView.adapter = chatListAdapter
                chatListAdapter.apply {
                    submitList(it)
                    notifyDataSetChanged()
                }
            }else{ // 현재 type 이 Other_Chat 이라면
                val chatListAdapter = ChatListAdapterModeManagement() // 이 어답터를 적용함
                // 이 어답터를 적용하면, 상대방의 입장에서 채팅창 UI 를 보게 됨
                binding.chatRecyclerView.adapter = chatListAdapter
                chatListAdapter.apply {
                    submitList(it)
                    notifyDataSetChanged()
                }
            }

            binding.chatRecyclerView.scrollToPosition(it.size-1)

        })
    }
}