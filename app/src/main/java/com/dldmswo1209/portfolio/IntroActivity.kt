package com.dldmswo1209.portfolio

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.data.defaultImageUri
import com.dldmswo1209.portfolio.data.defaultUserInfo
import com.dldmswo1209.portfolio.databinding.ActivityIntroBinding
import com.dldmswo1209.portfolio.entity.UserEntity
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.dldmswo1209.portfolio.viewModel.UserInfoViewModel
import kotlinx.coroutines.*

// 앱 실행시 보여지는 첫번째 화면
class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    lateinit var viewModel: UserInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }

        binding.registerButton.setOnClickListener {
            // 회원가입 버튼
            startActivity(Intent(this, SignUpActivity::class.java))
        }


    }
}