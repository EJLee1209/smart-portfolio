package com.dldmswo1209.portfolio

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dldmswo1209.portfolio.databinding.ActivityIntroBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

// 앱 실행시 보여지는 첫번째 화면
class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if(email == "" || password == ""){
                Toast.makeText(this, "이메일과 패스워드를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password) // 로그인
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        // 로그인 성공
                        // 자동로그인을 위해 uid 를 sharedPreferences 에 저장함
                        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("uid", auth.currentUser!!.uid)
                        editor.apply()

                        startActivity(Intent(this, SplashActivity::class.java))
                        finish()
                    }else{
                        // 로그인 실패
                        Toast.makeText(this, "이메일 또는 패스워드를 확인해주세요", Toast.LENGTH_SHORT).show()
                    }
                }


        }

        binding.registerButton.setOnClickListener {
            // 회원가입 버튼
            startActivity(Intent(this, SignUpActivity::class.java))
        }


    }
}