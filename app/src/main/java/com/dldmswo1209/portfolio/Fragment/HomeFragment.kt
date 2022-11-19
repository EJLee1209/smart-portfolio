package com.dldmswo1209.portfolio.Fragment

import android.app.ActionBar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.MainActivity
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.databinding.FragmentHomeBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var uid = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // 웹뷰 세팅
        binding.homeWebView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uid = (activity as MainActivity).uid

        viewModel.getUser(uid).observe(viewLifecycleOwner){
            // 웹뷰 실행
            binding.homeWebView.loadUrl(it.profile?.resumeUrl.toString())
            if(it.profile?.email == "dldmswo1209@gmail.com") { // 개발자의 계정인 경우
                binding.homeBottomSheet.visibility = View.VISIBLE // bottomSheetDialog 를 보여줌

                // margin 동적 할당
                val layoutParams = CoordinatorLayout.LayoutParams(
                    CoordinatorLayout.LayoutParams.MATCH_PARENT,
                    CoordinatorLayout.LayoutParams.MATCH_PARENT
                )

                layoutParams.bottomMargin = changeDP(120)
                binding.webViewLayout.layoutParams = layoutParams
            }
            else{ // bottomSheetDialog 숨기기
                binding.homeBottomSheet.visibility = View.GONE
            }
        }

        Glide.with(binding.root) // 이미지를 circleCrop 하기 위해서 Glide 사용
            .load(R.drawable.health_me)
            .circleCrop()
            .into(binding.healthImageView)

        val behavior = BottomSheetBehavior.from(binding.homeBottomSheet)
        //BottomSheetCallback 을 구현해서 BottomSheetDialog 의 State 가 변경될 때 어떤 작업을 처리할지 알려준다.
        behavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){
                    BottomSheetBehavior.STATE_EXPANDED ->{ // 펼쳐진 상태
                        binding.upAnim.visibility = View.INVISIBLE // 애니메이션을 숨김
                        binding.upAnim.pauseAnimation() // 애니메이션 멈춤
                    }
                    BottomSheetBehavior.STATE_COLLAPSED ->{ // 닫힌 상태
                        binding.upAnim.visibility = View.VISIBLE // 애니메이션 보임
                        binding.upAnim.playAnimation() // 애니메이션 재생
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

        })
    }

    private fun changeDP(value : Int) : Int{
        var displayMetrics = resources.displayMetrics
        var dp = Math.round(value * displayMetrics.density)
        return dp
    }

}