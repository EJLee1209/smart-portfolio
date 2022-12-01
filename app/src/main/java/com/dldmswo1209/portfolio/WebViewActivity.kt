package com.dldmswo1209.portfolio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.dldmswo1209.portfolio.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityWebViewBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 웹 url 를 인텐트로 전달받음
        val url = intent.getStringExtra("url").toString()

        // 웹뷰 세팅
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }

        // 웹뷰 실행
        url.let {
            binding.webView.loadUrl(url)
        }

    }

    override fun onBackPressed() { // 뒤로가기 키를 누른 경우
        if(binding.webView.canGoBack()) binding.webView.goBack() // 웹뷰 상에서 뒤로 갈 수 있는 페이지가 존재하면 한페이지 뒤로 이동
        else finish() // 없으면 웹뷰 종료
    }
}