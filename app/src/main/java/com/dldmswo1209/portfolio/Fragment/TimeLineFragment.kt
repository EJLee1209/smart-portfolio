package com.dldmswo1209.portfolio.Fragment

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.dldmswo1209.portfolio.AddDialog
import com.dldmswo1209.portfolio.MainActivity
import com.dldmswo1209.portfolio.Model.TimeLine
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.adapter.TimeLineAdapter
import com.dldmswo1209.portfolio.databinding.FragmentTimeLineBinding
import com.dldmswo1209.portfolio.viewModel.MainViewModel
import kotlinx.coroutines.selects.select

class TimeLineFragment : Fragment() {

    private lateinit var binding: FragmentTimeLineBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val selectedTimeLines = mutableListOf<TimeLine>()
    private val timeLineAdapter = TimeLineAdapter{ timeLine ->
        // 타임라인 아이템 클릭 이벤트
        if(timeLine.isSelected){
            selectedTimeLines.add(timeLine)
        }else{
            selectedTimeLines.remove(timeLine)
        }
    }
    private var uid = ""
    private var isSuperShow = false
    private var isFabOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimeLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uid = (activity as MainActivity).uid
        isSuperShow = (activity as MainActivity).isSuperShow

        if(isSuperShow) // 채용 담당자가 보고 있음
            binding.mainFab.visibility = View.GONE // 포트폴리오 추가 버튼 숨기기

        binding.timeLineRecyclerView.adapter = timeLineAdapter

        viewModel.getTimeLine(uid).observe(viewLifecycleOwner){
            timeLineAdapter.submitList(it)
        }

        // 추가 버튼
        binding.addFab.setOnClickListener{
            val dlg = AddDialog((activity as MainActivity))
            dlg.show{ timeLine->
                viewModel.createTimeLine(uid, timeLine)
            }
        }

        // 삭제 버튼
        binding.deleteFab.setOnClickListener{
            // 여러 타임라인을 선택해서 한 번에 삭제 가능하도록 구현
            Log.d("testt", "delete button click : ${selectedTimeLines}")
            AlertDialog.Builder(requireContext())
                .setTitle("타임라인 ${selectedTimeLines.size}개 삭제")
                .setMessage("정말로 삭제하시겠습니까?")
                .setPositiveButton("확인") { dialogInterface, i ->
                    // 선택된 타임라인 모두 삭제
                    selectedTimeLines.forEach {  timeline->
                        viewModel.deleteTimeLine(uid, timeline.key)
                    }
                    selectedTimeLines.clear()
                }
                .setNegativeButton("취소") { dialogInterface, i -> }
                .show()
        }

        // 플로팅 버튼 클릭시 애니메이션 동작
        binding.mainFab.setOnClickListener{
            toggleFab()
        }



    }


    private fun toggleFab(){
        // 플로팅 액션 버튼 닫기
        if(isFabOpen){
            ObjectAnimator.ofFloat(binding.deleteFab, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.addFab, "translationY", 0f).apply { start() }
            binding.mainFab.setImageResource(R.drawable.ic_baseline_add_24)


        }else{ // 플로팅 액션 버튼 열기
            ObjectAnimator.ofFloat(binding.deleteFab, "translationY", -200f).apply { start() }
            ObjectAnimator.ofFloat(binding.addFab, "translationY", -350f).apply { start() }
            binding.mainFab.setImageResource(R.drawable.ic_baseline_add_24)
        }

        isFabOpen = !isFabOpen
    }


}