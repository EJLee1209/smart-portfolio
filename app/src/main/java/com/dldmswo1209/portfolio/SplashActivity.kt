package com.dldmswo1209.portfolio

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dldmswo1209.portfolio.databinding.ActivitySplashBinding
import java.lang.String
import kotlin.Throwable
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread {
            for (i in 0..100) {
                // runOnUiThread() - Background 작업 중 UI 조작위해
                runOnUiThread {
                    binding.progressBar.incrementProgressBy(1)
                }
                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            startActivity(Intent(this, MainActivity::class.java))
        }.start()

    }
}