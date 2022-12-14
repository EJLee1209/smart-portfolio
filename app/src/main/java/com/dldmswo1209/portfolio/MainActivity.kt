package com.dldmswo1209.portfolio

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.Model.ChatRoom
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.adapter.MODE_NORMAL
import com.dldmswo1209.portfolio.adapter.ViewPagerAdapter
import com.dldmswo1209.portfolio.databinding.ActivityMainBinding
import com.dldmswo1209.portfolio.fcm.MyFirebaseMessagingService
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    var uid  = ""
    var isSuperShow = false // 채용 담당자가 보는 중인가?
    var chatRooms = mutableListOf<ChatRoom>()
    var fcmToken = ""

    lateinit var currentUser : User
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferences2: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getPreferences()
        initView()
        observer()
        getFCMToken()
    }
    // 뷰 초기화 메서드
    private fun initView(){
        // 뷰페이저 어답터 연결
        binding.mainViewPager.adapter = ViewPagerAdapter(this, MODE_NORMAL)
        binding.pageIndicatorView.count = 4
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
                    val editor = sharedPreferences.edit()
                    editor.putString("uid","").apply() // 저장되어있던 uid 삭제
                    val editor2 = sharedPreferences2.edit()
                    editor2.putString("showUid", "").apply()

                    val auth = Firebase.auth
                    auth.signOut()

                    viewModel.removeToken(uid)

                    startActivity(Intent(this, IntroActivity::class.java)) // 초기 화면으로 이동
                    finish() // 현재 액티비티를 종료
                }
                R.id.myPage ->{ // 마이페이지 클릭
                    binding.drawerLayout.closeDrawers() // drawer 를 닫고
                    startActivity(Intent(this, MyPageActivity::class.java)) // 마이페이지 액티비티로 이동
                }
                R.id.message ->{ // 메세지 클릭
                    binding.drawerLayout.closeDrawers()
                    val intent = Intent(this, ChatListActivityNormal::class.java)
                    intent.putExtra("currentUser",currentUser)
                    startActivity(intent) // 마이페이지 액티비티로 이동
                }
            }
            true
        }
    }

    private fun getFCMToken(){
        // FCM 토큰 가져오기
        if(isSuperShow) return // 채용 담당자가 보는 중이라면 토큰을 등록하면 안됨.

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(!it.isSuccessful){
                Log.d("testt", "Fetching FCM registration token failed", it.exception)
                return@addOnCompleteListener
            }

            fcmToken = it.result

            Log.d("testt", "getFCMToken: ${fcmToken}")
            viewModel.registerToken(uid, fcmToken) // 토큰 등록
        }
    }

    private fun getPreferences(){
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        sharedPreferences2 = getSharedPreferences("superMode", Context.MODE_PRIVATE)
        uid = sharedPreferences.getString("uid","").toString()

        isSuperShow = intent.getBooleanExtra("isSuperShow", false)
        if(isSuperShow){ // 채용 담당자가 보는 중
            uid = sharedPreferences2.getString("showUid","").toString()
        }
    }

    private fun observer(){
        viewModel.getUser(uid).observe(this){ // 유저 정보를 가져옴.
            currentUser = it // 현재 유저 저장

            // 채팅방 리스트 가져오기
            viewModel.getChatRooms(it).observe(this){ rooms->
                chatRooms = rooms
                rooms.forEach { room->
                    viewModel.registerTokenChatRooms(uid, room.key, fcmToken, isSuperShow)
                }
            }

            // 유저 정보가 변경되면 알아서 가져와짐
            binding.nameTextView.text = it.name
            if(it.profile != null){ // 아예 프로필 설정을 안한 경우 (회원가입 후 초기 상태)
                if(it.profile?.image == "" || it.profile?.image == null){
                    // 프로필은 있지만, 사진이 없는 경우
                    Glide.with(this)
                        .load(R.drawable.profile)
                        .circleCrop()
                        .into(binding.mainProfileImageView)
                }else{ // 프로필 있고, 사진이 있음
                    Glide.with(this)
                        .load(it.profile?.image)
                        .circleCrop()
                        .into(binding.mainProfileImageView)
                }

            }else{ // 프로필이 아예 없음
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
        if(isSuperShow){
            Toast.makeText(this, "열람을 종료하시려면 뒤로가기 키를 눌러주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        when(item.itemId){
            R.id.menu_icon->{
                binding.drawerLayout.openDrawer(GravityCompat.END)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}