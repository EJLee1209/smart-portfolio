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

        // viewModel 을 둘다 가져옵니다.
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        userInfoViewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]

        // 모든 유저 정보를 가져오고
        userInfoViewModel.getAllUser()

        // 모든 유저 정보에 대한 데이터를 관찰
        userInfoViewModel.allUser.observe(this, Observer{
            val user = it.first() // 첫번째 유저 정보를 가져와서 ui를 업데이트 합니다.
            binding.nameTextView.text = user.name
            Glide.with(this)
                .load(user.profileImage?.toUri())
                .circleCrop()
                .into(binding.mainProfileImageView)
        })

    }
    // 뷰 초기화 메서드
    private fun initView(){
        // 뷰페이저 어답터 연결
        binding.mainViewPager.adapter = ViewPagerAdapter(this)
        binding.pageIndicatorView.count = 3
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

    override fun onResume() {
        super.onResume()
        userInfoViewModel.getAllUser() //  마이페이지에서 정보 수정시 정보 업데이트를 해주기 위함
    }

}