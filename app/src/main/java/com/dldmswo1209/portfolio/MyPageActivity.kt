package com.dldmswo1209.portfolio

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.Fragment.EditUserInfoFragment
import com.dldmswo1209.portfolio.Fragment.MyProfileBottomSheetFragment
import com.dldmswo1209.portfolio.databinding.ActivityMyPageBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// 마이페이지 액티비티
// 로그아웃, 프로필 수정 기능 포함

class MyPageActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMyPageBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    var uid = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        uid = sharedPreferences.getString("uid","").toString()

        val sharedPreferences2 = getSharedPreferences("superMode", Context.MODE_PRIVATE)

        viewModel.getUser(uid).observe(this){ // 유저 정보를 가져옴.
            // 유저 정보가 변경되면 알아서 가져와짐
            binding.profileName.text = it.name
            if(it.profile != null){ // 아예 프로필 설정을 안한 경우 (회원가입 후 초기 상태)
                if(it.profile?.image == "" || it.profile?.image == null){
                    // 프로필은 있지만, 사진이 없는 경우
                    Glide.with(this)
                        .load(R.drawable.profile)
                        .circleCrop()
                        .into(binding.profileImage)
                }else{
                    Glide.with(this)
                        .load(it.profile?.image)
                        .circleCrop()
                        .into(binding.profileImage)
                }
            }else{
                Glide.with(this)
                    .load(R.drawable.profile)
                    .circleCrop()
                    .into(binding.profileImage)
            }
        }

        // 로그아웃 클릭
        binding.logoutTextView.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("uid","").apply() // 저장되어있던 uid 삭제
            val editor2 = sharedPreferences2.edit()
            editor2.putString("showUid", "").apply()

            val auth = Firebase.auth
            auth.signOut()

            viewModel.removeToken(uid)
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


    }
}