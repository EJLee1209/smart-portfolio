package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.databinding.FragmentEditUserInfoBinding
import com.dldmswo1209.portfolio.entity.UserEntity
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.dldmswo1209.portfolio.viewModel.UserInfoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditUserInfoFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditUserInfoBinding
    private val viewModel : UserInfoViewModel by activityViewModels()
    private lateinit var user : UserEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllUser()
        viewModel.allUser.observe(this, Observer {
            user = it.first()
            binding.phoneEditText.setText(user.phone)
            binding.emailEditText.setText(user.email)
            binding.AddressEditText.setText(user.address)
        })

        binding.okButton.setOnClickListener {
            val phone = binding.phoneEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val address = binding.AddressEditText.text.toString()

            val newUserInfo = UserEntity(user.id, user.name, user.profileImage, phone, user.intro, email, address )
            viewModel.updateUser(newUserInfo)
            dialog?.dismiss()
        }
        binding.closeButton.setOnClickListener {
            dialog?.dismiss()
        }

    }


}