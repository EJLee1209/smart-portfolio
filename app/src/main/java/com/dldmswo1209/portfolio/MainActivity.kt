package com.dldmswo1209.portfolio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.adapter.ViewPagerAdapter
import com.dldmswo1209.portfolio.databinding.ActivityMainBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.dldmswo1209.portfolio.viewModel.UserInfoViewModel

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel
    private lateinit var userInfoViewModel : UserInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        userInfoViewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]

        userInfoViewModel.getAllUser()

        userInfoViewModel.allUser.observe(this, Observer{
            val user = it.first()
            binding.nameTextView.text = user.name
            Glide.with(this)
                .load(user.profileImage?.toUri())
                .circleCrop()
                .into(binding.mainProfileImageView)
        })

        userInfoViewModel.userInfo.observe(this, Observer {
            binding.nameTextView.text = it.name
            Glide.with(this)
                .load(it.profileImage?.toUri())
                .circleCrop()
                .into(binding.mainProfileImageView)
        })

    }
    private fun initView(){
        binding.mainViewPager.adapter = ViewPagerAdapter(this)
        binding.pageIndicatorView.count = 3
        binding.pageIndicatorView.selection = 0

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.mainViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.pageIndicatorView.setSelected(position)
                super.onPageSelected(position)
            }
        })

        binding.mainProfileImageView.setOnClickListener {
            startActivity(Intent(this, MyPageActivity::class.java))
        }

        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logout ->{
                    binding.drawerLayout.closeDrawers()
                    startActivity(Intent(this, IntroActivity::class.java))
                    finish()
                }
                R.id.myPage ->{
                    binding.drawerLayout.closeDrawers()
                    startActivity(Intent(this, MyPageActivity::class.java))
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    // 툴 바 버튼 클릭시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_icon->{
                Log.d("testt", "menu clicked!")
                binding.drawerLayout.openDrawer(GravityCompat.END)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}