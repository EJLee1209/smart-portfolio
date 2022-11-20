package com.dldmswo1209.portfolio.Fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.ChatActivity
import com.dldmswo1209.portfolio.Model.ChatRoom
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.SplashActivity
import com.dldmswo1209.portfolio.SuperActivity
import com.dldmswo1209.portfolio.databinding.FragmentUserDetailBottomSheetBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UserDetailBottomSheet(val user: User) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentUserDetailBottomSheetBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var chatRooms = mutableListOf<ChatRoom>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme).apply {
            behavior.state =
                BottomSheetBehavior.STATE_EXPANDED // bottomSheetDialog 가 완전히 펼쳐진 상태로 보여지게 됨
            behavior.skipCollapsed = true // 드래그하면 dialog 가 바로 닫힘
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailBottomSheetBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = (activity as SuperActivity).currentUser

        binding.closeButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.chatButton.setOnClickListener {
            var key : String? = null

            chatRooms.forEach { room->
                if(currentUser.uid == room.sender.uid && user.uid == room.receiver.uid){
                    key = room.key
                    return@forEach
                }
            }
            val intent = Intent(requireContext(), ChatActivity::class.java)
            intent.putExtra("sender", currentUser)
            intent.putExtra("receiver", user)
            if(key != null){
                viewModel.getRoom(currentUser.uid, key!!).observe(viewLifecycleOwner){ room ->
                    // 채팅방이 이미 존재하는 경우
                    intent.putExtra("key", room.key)
                    startActivity(intent)
                }
            }else{
                viewModel.createChatRoom(currentUser, user).observe(viewLifecycleOwner){ newKey ->
                    // 채팅방이 존재하지 않아 새로 생성
                    intent.putExtra("key", newKey)
                    startActivity(intent)
                }
            }

        }


        viewModel.getChatRooms(currentUser).observe(viewLifecycleOwner){
            chatRooms = it
        }



        binding.callButton.setOnClickListener {

        }
        binding.showPortfolioButton.setOnClickListener {
            val sharedPreferences = (activity as SuperActivity).getSharedPreferences("superMode", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("showUid", user.uid) // 채용 인재의 uid 를 저장
            editor.apply()

            val intent = Intent(requireContext(), SplashActivity::class.java)
            intent.putExtra("isSuperShow",true) // 채용담당자가 보는 것임을 알리는 플래그를 전달
            intent.putExtra("showUser", user) // 채용 인재의 유저 정보를 전달

            startActivity(intent)

        }

    }
    private fun initView(){
        binding.nameTextView.text = user.name
        binding.stateMsgTextView.text = user.profile?.introduce
        Glide.with(this)
            .load(user.profile?.image)
            .circleCrop()
            .into(binding.profileImageView)
    }
}