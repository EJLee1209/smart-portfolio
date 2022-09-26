package com.dldmswo1209.portfolio

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.data.defaultImageUri
import com.dldmswo1209.portfolio.data.defaultUserInfo
import com.dldmswo1209.portfolio.databinding.ActivityIntroBinding
import com.dldmswo1209.portfolio.entity.UserEntity
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.dldmswo1209.portfolio.viewModel.UserInfoViewModel
// 앱 실행시 보여지는 첫번째 화면
class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    lateinit var viewModel: UserInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]

        binding.startButton.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
        viewModel.getAllUser() // 모든 유저 정보를 가져옴

        viewModel.allUser.observe(this, Observer {
            // 위에서 모든 유저 정보를 가져왔기 때문에 관찰자가 데이터를 확인합니다.
            if(it.isEmpty()){
                // 유저 정보를 담는 db가 비어있으면
                // 디폴트 값 저장
                viewModel.insertUser(defaultUserInfo)

            }else { // 유저 정보가 존재하면
                it.first().let { userEntity -> // 첫번째 데이터를 가져와서 ui를 업데이트 해줍니다.
                    // 마이페이지에서 유저 정보 변경시 업데이트
                    binding.introTextView.text = userEntity.intro
                    binding.nameTextView.text = "Name : ${userEntity.name}"
                    binding.phoneTextView.text = "Phone : ${userEntity.phone}"
                    binding.emailTextView.text = "Email : ${userEntity.email}"
                    binding.addressTextView.text = "Address : ${userEntity.address}"
                }
            }
        })

    }
}