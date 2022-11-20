package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dldmswo1209.portfolio.Model.ChatRoom
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.SuperActivity
import com.dldmswo1209.portfolio.adapter.ChatRoomAdapter
import com.dldmswo1209.portfolio.databinding.FragmentChatListBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel

class ChatListFragment : Fragment() {
    private lateinit var binding: FragmentChatListBinding
    private lateinit var roomAdapter : ChatRoomAdapter
    private lateinit var currentUser: User
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentUser = (activity as SuperActivity).currentUser
        roomAdapter = ChatRoomAdapter(currentUser){
            // 채팅방 클릭 이벤트
        }
        binding.roomRecyclerView.adapter = roomAdapter

        viewModel.getChatRooms(currentUser).observe(viewLifecycleOwner){
            roomAdapter.submitList(it)
            Log.d("testt", "onViewCreated: ${it}")
        }



    }

}