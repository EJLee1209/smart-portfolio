package com.dldmswo1209.portfolio

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dldmswo1209.portfolio.databinding.ActivitySplashBinding
import kotlinx.coroutines.*
import java.lang.Math.random
import java.lang.String
import java.time.Duration
import kotlin.Throwable
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val DURATION = randomSec()

        CoroutineScope(Dispatchers.IO).launch {
            async {
                for (i in 0..100) {
                    binding.progressBar.incrementProgressBy(1)
                    runOnUiThread {
                        binding.progressTextView.text = "${binding.progressBar.progress} %"
                    }
                    delay(DURATION/150)
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