package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dldmswo1209.portfolio.MainActivity
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.SuperActivity
import com.dldmswo1209.portfolio.adapter.UserListAdapter
import com.dldmswo1209.portfolio.databinding.FragmentAllUserBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel
// 채용 담당자 권한이 있는 사용자만 볼 수 있는 화면
class AllUserFragment : Fragment() {
    private lateinit var binding: FragmentAllUserBinding
    private val viewModel : MainViewModel by activityViewModels()
    private val userAdapter = UserListAdapter{ user-> // 클릭 이벤트 처리
        val bottomSheet = UserDetailBottomSheet(user) // user 의 프로필을 보여주는 bottomSheetDialog 생성
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }
    private var allUser = mutableListOf<User>() // 모든 일반 사용자
    lateinit var currentUser: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllUser().observe(viewLifecycleOwner){
            var updatedUser= User()

            userAdapter.submitList(it)
            if(it.size == allUser.size){ // 유저는 추가되지 않았지만, 프로필 수정을 한 것
                val chatRooms = (activity as SuperActivity).chatRooms
                // 프로필을 변경한 유저를 찾아야 함
                // 그 유저와 대화한 채팅방이 있으면 채팅방을 업데이트 해줘야 함
                for (i in 0 until it.size){
                    if(it[i].profile == null && allUser[i].profile == null && it[i].name != allUser[i].name) {
                        // 프로필이 없고, 이름만 변경한 경우
                        updatedUser = it[i]
                        break
                    }else if(it[i].profile != null && allUser[i].profile != null){
                        // 프로필이 있고, 이름을 변경한 경우
                        if(it[i].name != allUser[i].name){
                            updatedUser = it[i]
                            break
                        }else if(!it[i].profile!!.equals(allUser[i].profile)) {
                            updatedUser = it[i]
                            break
                        }
                    } else if(it[i].profile != null && allUser[i].profile == null) { // 원래 프로필이 없었는데 프로필을 만든경우
                        updatedUser = it[i]
                        break
                    }
                }
                chatRooms.forEach { room->
                    if(room.receiver.uid == updatedUser.uid){ // room.receiver 에는 무조건 일반 사용자가 저장되어 있음
                        // 해당 채팅방의 정보를 업데이트
                        currentUser = (activity as SuperActivity).currentUser
                        viewModel.updateMyChatRoom(currentUser, room.key, updatedUser)
                    }
                }

            }

            allUser = it
        }

        binding.userRecyclerView.adapter = userAdapter

    }

}