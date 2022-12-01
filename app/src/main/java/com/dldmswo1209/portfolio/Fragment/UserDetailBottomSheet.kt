package com.dldmswo1209.portfolio.Fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        // 채팅방 목록 가져오기
        viewModel.getChatRooms(currentUser).observe(viewLifecycleOwner){
            chatRooms = it
        }

        // x 버튼
        binding.closeButton.setOnClickListener {
            dialog?.dismiss()
        }

        // 채팅 버튼
        binding.chatButton.setOnClickListener {
            var key : String? = null

            // 이미 개설되어 있는 채팅방들 중에서
            chatRooms.forEach { room->
                if(currentUser.uid == room.sender.uid && user.uid == room.receiver.uid){ // 현재 채팅하고자 하는 사람과의 채팅방을 찾아
                    key = room.key // 채팅방 고유 key 값을 저장
                    return@forEach // 반복 종료
                }
            }
            val intent = Intent(requireContext(), ChatActivity::class.java)
            intent.putExtra("sender", currentUser) // 현재 사용자(채용 담당자) 전달
            intent.putExtra("receiver", user) // 채팅 상대(일반 사용자) 전달
            if(key != null){
                // 채팅방이 이미 존재하는 경우
                intent.putExtra("key", key)
                startActivity(intent)
            }else{
                viewModel.createChatRoom(currentUser, user).observe(viewLifecycleOwner){ newKey ->
                    // 채팅방이 존재하지 않아 새로 생성
                    intent.putExtra("key", newKey)
                    startActivity(intent)
                }
            }

        }

        // 전화 버튼
        binding.callButton.setOnClickListener {
            if(user.profile == null || user.profile?.phone == ""){ // 프로필이 존재하지 않거나 전화번호를 등록하지 않은 경우
                Toast.makeText(requireContext(), "전화번호를 등록하지 않은 사용자 입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Uri를 이용해서 정보 저장
            val myUri = Uri.parse("tel:${user.profile?.phone}")
            // 전환할 정보 설정 - ACTION_DIAL
            val myIntent = Intent(Intent.ACTION_DIAL, myUri)
            // 이동
            startActivity(myIntent)
        }
        // 포트폴리오 열람 버튼
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
    // 뷰 초기화
    private fun initView(){
        binding.nameTextView.text = user.name
        binding.stateMsgTextView.text = user.profile?.introduce
        if(user.profile != null){ // 아예 프로필 설정을 안한 경우 (회원가입 후 초기 상태)
            if(user.profile?.image == "" || user.profile?.image == null){
                // 프로필은 있지만, 사진이 없는 경우
                Glide.with(this)
                    .load(R.drawable.profile)
                    .circleCrop()
                    .into(binding.profileImageView)
            }else{
                Glide.with(this)
                    .load(user.profile?.image)
                    .circleCrop()
                    .into(binding.profileImageView)
            }

        }else{
            Glide.with(this)
                .load(R.drawable.profile)
                .circleCrop()
                .into(binding.profileImageView)
        }
    }
}