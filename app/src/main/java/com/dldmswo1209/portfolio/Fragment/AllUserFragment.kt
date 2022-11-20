package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.adapter.UserListAdapter
import com.dldmswo1209.portfolio.databinding.FragmentAllUserBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel

class AllUserFragment : Fragment() {
    private lateinit var binding: FragmentAllUserBinding
    private val viewModel : MainViewModel by activityViewModels()
    private val userAdapter = UserListAdapter{ user-> // 클릭 이벤트 처리
        val bottomSheet = UserDetailBottomSheet(user) // user 의 프로필을 보여주는 bottomSheetDialog 생성
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

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
            userAdapter.submitList(it)
        }

        binding.userRecyclerView.adapter = userAdapter

    }

}