package com.dldmswo1209.portfolio.Fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dldmswo1209.portfolio.MainActivity
import com.dldmswo1209.portfolio.MyPageActivity
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.databinding.FragmentMyProfileBottomSheetBinding
import com.dldmswo1209.portfolio.entity.UserEntity
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.dldmswo1209.portfolio.viewModel.UserInfoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.selects.select

class MyProfileBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMyProfileBottomSheetBinding
    private val userInfoViewModel : UserInfoViewModel by activityViewModels()
    private var imageUri: Uri? = null

    companion object{
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1
    }

    // 이미지를 결과값으로 받는 변수
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result ->
        if((result.resultCode == Activity.RESULT_OK)){
            // 이미지를 받으면 imageUri 변수에 저장 후 imageView 에 적용
            imageUri = result.data?.data

            imageUri.let {
                Glide.with(this)
                    .load(imageUri)
                    .circleCrop()
                    .into(binding.profileImageView)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyProfileBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        binding.profileImageView.setOnClickListener {
            selectGallery()
        }
        binding.okButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val intro = binding.introEditText.text.toString()
            val userInfo = userInfoViewModel.allUser.value?.first() ?: return@setOnClickListener
            val newUserInfo = UserEntity(userInfo.id, name, imageUri.toString(), userInfo.phone, intro, userInfo.email, userInfo.address)

            userInfoViewModel.updateUser(newUserInfo)

            dialog?.dismiss()
        }


    }
    private fun initView(){
        userInfoViewModel.getAllUser()

        userInfoViewModel.allUser.observe(this, Observer {
            val user = it.first()

            Glide.with(this)
                .load(user.profileImage?.toUri())
                .circleCrop()
                .into(binding.profileImageView)
            binding.nameEditText.setText(user.name)
            binding.introEditText.setText(user.intro)

        })
    }
    // 갤러리를 부르는 메서드
    private fun selectGallery(){
        val writePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        // 권한 확인
        if(writePermission == PackageManager.PERMISSION_DENIED ||
            readPermission == PackageManager.PERMISSION_DENIED){
            // 권한이 없는 경우 권한 요청
            ActivityCompat.requestPermissions((activity as MyPageActivity),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE),
                AddPortfolioBottomSheet.REQ_GALLERY)
        }else{ // 권한이 있는 경우
            // 갤러리 실행
            val intent = Intent(Intent.ACTION_PICK)
            // intent 의 data와 type 을 동시에 설정하기
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )

            imageResult.launch(intent)
        }

    }
}