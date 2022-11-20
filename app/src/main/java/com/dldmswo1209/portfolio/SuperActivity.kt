package com.dldmswo1209.portfolio

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.adapter.MODE_SUPER
import com.dldmswo1209.portfolio.adapter.ViewPagerAdapter
import com.dldmswo1209.portfolio.databinding.ActivitySuperBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel

class SuperActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivitySuperBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    var uid = ""
    lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()

        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        uid = sharedPreferences.getString("uid","").toString()

        viewModel.getUser(uid).observe(this){
            currentUser = it
        }

        // 툴바 설정
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바의 기본 타이틀을 숨김

        // navigationDrawer 의 메뉴 선택 리스너
        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logout ->{ // 로그아웃 클릭
                    binding.drawerLayout.closeDrawers() // drawer 를 닫고
                    startActivity(Intent(this, IntroActivity::class.java)) // 초기 화면으로 이동
                    finish() // 현재 액티비티를 종료
                }
                R.id.myPage ->{ // 마이페이지 클릭
                    binding.drawerLayout.closeDrawers() // drawer 를 닫고
                    startActivity(Intent(this, MyPageActivity::class.java)) // 마이페이지 액티비티로 이동
                }
            }
            true
        }

        viewModel.getUser(uid).observe(this){ // 유저 정보를 가져옴.
            // 유저 정보가 변경되면 알아서 가져와짐
            binding.nameTextView.text = it.name
            if(it.profile?.image != ""){ // 프사가 있으면
                Glide.with(this)
                    .load(it.profile?.image)
                    .circleCrop()
                    .into(binding.mainProfileImageView)
            }else{
                Glide.with(this)
                    .load(R.drawable.profile)
                    .circleCrop()
                    .into(binding.mainProfileImageView)
            }

        }
    }

    // 툴 바 메뉴를 생성합니다.(drawer 호출 위한 아이콘)
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

    // 뷰 초기화 메서드
    private fun initView(){
        // 뷰페이저 어답터 연결
        binding.mainViewPager.adapter = ViewPagerAdapter(this, MODE_SUPER)
        binding.pageIndicatorView.count = 2
        binding.pageIndicatorView.selection = 0

        // 툴바 설정
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바의 기본 타이틀을 숨김

        // 뷰페이저 페이지 변경 콜백
        binding.mainViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) { // 페이지가 선택되면
                binding.pageIndicatorView.setSelected(position) // indicator 를 현재 페이지의 위치로 변경
                super.onPageSelected(position)
            }
        })

        // 툴바에 있는 프로필 사진 클릭시 마이페이지로 이동
        binding.mainProfileImageView.setOnClickListener {
            startActivity(Intent(this, MyPageActivity::class.java))
        }

        // navigationDrawer 의 메뉴 선택 리스너
        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logout ->{ // 로그아웃 클릭
                    binding.drawerLayout.closeDrawers() // drawer 를 닫고
                    startActivity(Intent(this, IntroActivity::class.java)) // 초기 화면으로 이동
                    finish() // 현재 액티비티를 종료
                }
                R.id.myPage ->{ // 마이페이지 클릭
                    binding.drawerLayout.closeDrawers() // drawer 를 닫고
                    startActivity(Intent(this, MyPageActivity::class.java)) // 마이페이지 액티비티로 이동
                }
            }
            true
        }
    }
}