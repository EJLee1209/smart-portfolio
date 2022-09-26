package com.dldmswo1209.portfolio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.Fragment.AddPortfolioBottomSheet
import com.dldmswo1209.portfolio.Fragment.EditUserInfoFragment
import com.dldmswo1209.portfolio.Fragment.MyProfileBottomSheetFragment
import com.dldmswo1209.portfolio.databinding.ActivityMainBinding
import com.dldmswo1209.portfolio.databinding.ActivityMyPageBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.dldmswo1209.portfolio.viewModel.UserInfoViewModel

// 마이페이지 액티비티
// 로그아웃, 프로필 수정 기능 포함

class MyPageActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMyPageBinding.inflate(layoutInflater)
    }
    lateinit var userInfoViewModel: UserInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // ViewModel 가져오기
        userInfoViewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]
        userInfoViewModel.getAllUser()

        // 로그아웃 클릭
        binding.logoutTextView.setOnClickListener {
            startActivity(Intent(this, IntroActivity::class.java)) // 초기화면으로 이동
            finish()// 현재 액티비티 종료
        }

        // editImageView 클릭시
        binding.editImageView.setOnClickListener {
            // 프로필 수정을 위한 bottomSheetDialog 를 띄움
            val bottomSheet = MyProfileBottomSheetFragment()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        //infoEditTextView 클릭시
        binding.infoEditTextView.setOnClickListener {
            // 개인 정보를 수정하기 위한 bottomSheetDialog 를 띄움
            val bottomSheet = EditUserInfoFragment()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        userInfoViewModel.allUser.observe(this, Observer {
            val user = it.first()
            binding.profileName.text = user.name
            Glide.with(this)
                .load(user.profileImage?.toUri())
                .circleCrop()
                .into(binding.profileImage)
        })

        userInfoViewModel.userInfo.observe(this, Observer {
            binding.profileName.text = it.name
            Glide.with(this)
                .load(it.profileImage?.toUri())
                .circleCrop()
                .into(binding.profileImage)
        })


    }
}