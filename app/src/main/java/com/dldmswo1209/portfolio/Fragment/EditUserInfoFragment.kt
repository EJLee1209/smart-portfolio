package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.Model.UserProfile
import com.dldmswo1209.portfolio.MyPageActivity
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.databinding.FragmentEditUserInfoBinding
import com.dldmswo1209.portfolio.entity.UserEntity
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// 개인 정보 수정 클릭시 나타나는 bottomSheetDialog
class EditUserInfoFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditUserInfoBinding
    private lateinit var user : User
    private val viewModel : MainViewModel by activityViewModels()

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

        viewModel.getUser((activity as MyPageActivity).uid).observe(this){
            user = it
            binding.phoneEditText.setText(it.profile?.phone)
            binding.AddressEditText.setText(it.profile?.address)
            binding.resumeEditText.setText(it.profile?.resumeUrl)
            binding.emailEditText.setText(it.profile?.email)
        }

        binding.okButton.setOnClickListener {
            val phone = binding.phoneEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val address = binding.AddressEditText.text.toString()
            val resume = binding.resumeEditText.text.toString()

            val newPrivacy = UserProfile(user.profile?.image, user.profile?.imageUri, resume, user.profile?.introduce, phone, email, address )
            viewModel.updatePrivacyInfo(user.uid, newPrivacy)

            dialog?.dismiss()
        }
        binding.closeButton.setOnClickListener {
            dialog?.dismiss()
        }

    }


}