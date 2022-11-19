package com.dldmswo1209.portfolio

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.data.defaultCardList
import com.dldmswo1209.portfolio.data.defaultChatList
import com.dldmswo1209.portfolio.data.defaultImageUri
import com.dldmswo1209.portfolio.data.timeLineList
import com.dldmswo1209.portfolio.databinding.ActivitySplashBinding
import com.dldmswo1209.portfolio.entity.CardEntity
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.lang.Math.random
import java.lang.String
import java.time.Duration
import kotlin.Throwable
import kotlin.concurrent.thread
// Splash 화면

// 요구사항
// Thread 를 활용하여 ProgressBar 를 0~100 까지 로딩 할 수 있도록 구현
// 로딩 시간은 1~3초 랜덤하게
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val dbRef = Firebase.database.reference.child("User")
    val DURATION = randomSec() // 로딩 시간

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "").toString()

        binding.lottieAnimationView.playAnimation() // lottie 애니메이션 재생

        // 코루틴을 사용해서 로딩 구현
        CoroutineScope(Dispatchers.IO).launch {
            // 비동기 처리로 로딩과 job 을 동시에 수행
            async(Dispatchers.IO) {
                for (i in 0..100) {
                    loading()
                }
            }
            async{
                delay(DURATION)
            }.await() // 다 수행할 때까지 기다림

            // 로딩시간이 끝나면 메인화면 or 로그인 화면으로 이동
            if(uid != ""){ // 이전에 로그인을 했었음
                // 일반 사용자인지 채용 담당자인지에 따라 다른 화면을 보여줘야 함
                dbRef.child(uid).get()
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val user = it.result.getValue(User::class.java) ?: return@addOnCompleteListener
                            var intent : Intent
                            if(user.isSuperUser){ // 채용 담당자
                                intent = Intent(this@SplashActivity, SuperActivity::class.java)
                            }else{ // 일반 사용자
                                intent = Intent(this@SplashActivity, MainActivity::class.java)
                            }
                            startActivity(intent)
                        }
                    }

            }else{ // 로그인을 안했음
                val intent = Intent(this@SplashActivity, IntroActivity::class.java)
                startActivity(intent)
            }

        }


        Log.d("testt", "Duration : ${DURATION}")
    }
    private fun randomSec(): Long{
        return (1000..3000).random().toLong() // 1000~3000 랜덤하게 리턴(ms)
    }
    suspend fun loading(){
        binding.progressBar.incrementProgressBy(1) // 프로그레스바 1증가
        runOnUiThread {
            binding.progressTextView.text = "${binding.progressBar.progress} %"
        }
        delay(DURATION/110) // 총 100 번 반복하므로 로딩시간과 비슷한 시점에 작업이 끝나도록 딜레이를 줌
    }
}