package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.databinding.FragmentHomeBinding
import com.dldmswo1209.portfolio.viewModel.UserInfoViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val userInfoViewModel : UserInfoViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userInfoViewModel.getAllUser()

        userInfoViewModel.allUser.observe(viewLifecycleOwner, Observer {
            it.first().let { userEntity->
                binding.introTextView.text = userEntity.intro
                binding.nameTextView.text = "Name : ${userEntity.name}"
                binding.phoneTextView.text = "Phone : ${userEntity.phone}"
                binding.emailTextView.text = "Email : ${userEntity.email}"
                binding.addressTextView.text = "Address : ${userEntity.address}"
            }

        })
    }


}