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
import com.dldmswo1209.portfolio.Fragment.MyProfileBottomSheetFragment
import com.dldmswo1209.portfolio.databinding.ActivityMainBinding
import com.dldmswo1209.portfolio.databinding.ActivityMyPageBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.dldmswo1209.portfolio.viewModel.UserInfoViewModel

class MyPageActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMyPageBinding.inflate(layoutInflater)
    }
    lateinit var userInfoViewModel: UserInfoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userInfoViewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]
        userInfoViewModel.getAllUser()

        binding.logoutTextView.setOnClickListener {
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }

        binding.editImageView.setOnClickListener {
            val bottomSheet = MyProfileBottomSheetFragment()
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