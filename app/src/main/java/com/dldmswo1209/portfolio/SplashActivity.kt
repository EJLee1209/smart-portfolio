package com.dldmswo1209.portfolio

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
import com.dldmswo1209.portfolio.data.defaultCardList
import com.dldmswo1209.portfolio.data.defaultChatList
import com.dldmswo1209.portfolio.data.defaultImageUri
import com.dldmswo1209.portfolio.databinding.ActivitySplashBinding
import com.dldmswo1209.portfolio.entity.CardEntity
import com.dldmswo1209.portfolio.viewModel.MainViewModel
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
    private lateinit var viewModel : MainViewModel
    private lateinit var binding: ActivitySplashBinding
    val DURATION = randomSec() // 로딩 시간

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뷰모델 가져오기
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getAllCard() // 모든 카드 리스트
        viewModel.getAllChat() // 모든 채팅 리스트

        viewModel.chatList.observe(this, Observer {
            if(it.isEmpty()){ // 초기 실행시 포트폴리오 채팅이 비어있으면
                // 기본 채팅 리스트를 데이터베이스에 추가함
                defaultChatList.forEach { chatEntity ->
                    viewModel.insertChat(chatEntity)
                }

            }
        })

        viewModel.cardList.observe(this, Observer {
            if(it.isEmpty()){ // 초기 실행시 포트폴리오 카드가 비어있으면
                // 기본 카드 리스트를 데이터베이스에 추가함
                defaultCardList.forEach {  cardEntity->
                    viewModel.insertCard(cardEntity)
                }
            }
        })

        // 코루틴을 사용해서 로딩 구현
        CoroutineScope(Dispatchers.IO).launch {
            // 비동기 처리로 로딩과 job 을 동시에 수행
            async(Dispatchers.IO) {
                for (i in 0..100) {
                    loading()
                }
            }
            val job = async{
                delay(DURATION)
            }
            job.await() // job 을 다 수행할 때까지 기다림

            // 로딩시간이 끝나면 메인화면으로 이동
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
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