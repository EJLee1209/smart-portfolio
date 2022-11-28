package com.dldmswo1209.portfolio

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.databinding.ActivitySplashBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
// Splash 화면

// 요구사항
// Thread 를 활용하여 ProgressBar 를 0~100 까지 로딩 할 수 있도록 구현
// 로딩 시간은 1~3초 랜덤하게
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val dbRef = Firebase.database.reference.child("User")
    val DURATION = randomSec() // 로딩 시간
    private var isSuperShow = false // 채용 담당자가 보고있는지?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        var uid = sharedPreferences.getString("uid", "").toString()

        isSuperShow = intent.getBooleanExtra("isSuperShow", false) // 채용 담당자가 보는 중 인지?

        if(isSuperShow){ // 채용 담당자가 보는 중
            val sharedPreferences2 = getSharedPreferences("superMode", Context.MODE_PRIVATE)
            uid = sharedPreferences2.getString("showUid","").toString() // uid 변경(채용 담당자 UID 로)
        }

        binding.lottieAnimationView.playAnimation() // lottie 애니메이션 재생

        // 코루틴을 사용해서 로딩 구현
        CoroutineScope(Dispatchers.IO).launch {
            // 비동기 처리
            async(Dispatchers.IO) {
                for (i in 1..100) {
                    loading() // 프로그레스바 증가
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
                            intent.putExtra("isSuperShow", isSuperShow)
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
        delay(DURATION/100) // 총 100 번 반복하므로 로딩시간/100 만큼 딜레이를 줌
    }
}