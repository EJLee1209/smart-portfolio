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

        val url = intent.getStringExtra("url").toString()

        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }

        url?.let {
            binding.webView.loadUrl(url)
        }

    }

    override fun onBackPressed() {
        if(binding.webView.canGoBack()) binding.webView.goBack()
        else finish()
    }
}