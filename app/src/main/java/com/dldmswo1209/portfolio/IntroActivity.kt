package com.dldmswo1209.portfolio

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.databinding.ActivityIntroBinding
import com.dldmswo1209.portfolio.entity.UserEntity
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.dldmswo1209.portfolio.viewModel.UserInfoViewModel

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    lateinit var viewModel: UserInfoViewModel
    // 기본으로 넣을 내 사진(resId) -> Uri 변환
    private val defaultImageUri = Uri.parse(
        "android.resource://com.dldmswo1209.portfolio/"
                + R.drawable.my_profile_image)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]

        binding.startButton.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }

        viewModel.getAllUser()

        viewModel.allUser.observe(this, Observer {
            if(it.isEmpty()){
                // 유저 정보를 담는 db가 비어있으면
                // 디폴트 값 저장
                viewModel.insertUser(UserEntity(0,"이은재",defaultImageUri.toString(),
                    "010-5878-1209","항상 새로운 것을 공부하고자 하는 열망이 있는 안드로이드 신입 개발자 이은재 입니다.", "dldmswo1209@gmail.com","강원도 춘천시"))

            }else {
                it.first().let { userEntity ->
                    // 마이페이지에서 유저 정보 변경시 업데이트
                    binding.introTextView.text = userEntity.intro
                    binding.nameTextView.text = "Name : ${userEntity.name}"
                    binding.phoneTextView.text = "Phone : ${userEntity.phone}"
                    binding.emailTextView.text = "Email : ${userEntity.email}"
                    binding.addressTextView.text = "Address : ${userEntity.address}"
                }
            }


        })

        viewModel.userInfo.observe(this, Observer {

        })


    }
}