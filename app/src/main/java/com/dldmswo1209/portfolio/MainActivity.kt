package com.dldmswo1209.portfolio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.dldmswo1209.portfolio.adapter.ViewPagerAdapter
import com.dldmswo1209.portfolio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mainViewPager.adapter = ViewPagerAdapter(this)
        binding.pageIndicatorView.count = 4
        binding.pageIndicatorView.selection = 0

        binding.mainViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.pageIndicatorView.setSelected(position)
                super.onPageSelected(position)
            }
        })

    }

    // 툴 바 버튼 클릭시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return super.onOptionsItemSelected(item)
    }
}