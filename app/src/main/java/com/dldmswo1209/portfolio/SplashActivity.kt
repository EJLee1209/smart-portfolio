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

class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel : MainViewModel
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getAllCard()
        viewModel.getAllChat()



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

        val DURATION = randomSec()

        CoroutineScope(Dispatchers.IO).launch {
            async {
                for (i in 0..100) {
                    binding.progressBar.incrementProgressBy(1)
                    runOnUiThread {
                        binding.progressTextView.text = "${binding.progressBar.progress} %"
                    }
                    delay(DURATION/110)
                }
            }
            val job = async{
                delay(DURATION)
            }

            job.await() // job2 를 다 수행할 때까지 기다림
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }


        Log.d("testt", "Duration : ${DURATION}")
    }
    private fun randomSec(): Long{
        return (1000..3000).random().toLong()
    }
}