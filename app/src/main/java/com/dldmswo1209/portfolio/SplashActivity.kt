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
    private val defaultImageUri = Uri.parse(
        "android.resource://com.dldmswo1209.portfolio/"
                + R.drawable.github)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getAllCard()
        viewModel.getAllChat()

        val defaultCard = CardEntity(
            0
            ,defaultImageUri.toString()
            ,"KBSC 소프트웨어 경진대회"
            ,"심리상담 챗봇 안드로이드 앱 개발 참여(예선 심사 중)"
            ,"https://github.com/EJLee1209/Chatbot")

        viewModel.chatList.observe(this, Observer {
        })

        viewModel.cardList.observe(this, Observer {
            if(it.isEmpty()){
                viewModel.insertCard(defaultCard)
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