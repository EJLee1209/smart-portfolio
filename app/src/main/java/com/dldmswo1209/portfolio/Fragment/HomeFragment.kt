package com.dldmswo1209.portfolio.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.databinding.FragmentHomeBinding
import com.dldmswo1209.portfolio.viewModel.UserInfoViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val userInfoViewModel : UserInfoViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userInfoViewModel.getAllUser()

        userInfoViewModel.allUser.observe(viewLifecycleOwner, Observer {
            it.first().let { userEntity->
                binding.introTextView.text = userEntity.intro
                binding.nameTextView.text = "Name : ${userEntity.name}"
                binding.phoneTextView.text = "Phone : ${userEntity.phone}"
                binding.emailTextView.text = "Email : ${userEntity.email}"
                binding.addressTextView.text = "Address : ${userEntity.address}"
            }

        })

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


}