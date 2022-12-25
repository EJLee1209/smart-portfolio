package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dldmswo1209.portfolio.MainActivity
import com.dldmswo1209.portfolio.databinding.FragmentChatBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.dldmswo1209.portfolio_compose.ChatList

// 채팅 리스트를 보여주는 fragment
class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var uid = ""
    private var isSuperShow = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uid = (activity as MainActivity).uid
        isSuperShow = (activity as MainActivity).isSuperShow

        binding.composeView.setContent {
            ChatList(viewModel = viewModel, uid = uid, isSuperShow = isSuperShow)
        }

    }
}