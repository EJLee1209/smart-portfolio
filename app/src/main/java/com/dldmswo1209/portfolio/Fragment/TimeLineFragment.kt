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
import android.widget.ArrayAdapter
import android.widget.Toast
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
import java.util.*

class TimeLineFragment : Fragment() {

    private lateinit var binding: FragmentTimeLineBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val selectedTimeLines = mutableListOf<TimeLine>()
    private lateinit var timeLineAdapter : TimeLineAdapter
    private var uid = ""
    private var isSuperShow = false
    private var isFabOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimeLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uid = (activity as MainActivity).uid
        isSuperShow = (activity as MainActivity).isSuperShow

        if(isSuperShow) {// 채용 담당자가 보고 있음
            binding.mainFab.visibility = View.GONE // 포트폴리오 추가 버튼 숨기기
            binding.addFab.visibility = View.GONE
            binding.updateFab.visibility = View.GONE
            binding.deleteFab.visibility = View.GONE
        }
        timeLineAdapter = TimeLineAdapter{ timeLine ->
            // 타임라인 아이템 클릭 이벤트
            if(timeLine.isSelected){
                selectedTimeLines.add(timeLine)
            }else{
                selectedTimeLines.remove(timeLine)
            }
        }
        binding.timeLineRecyclerView.adapter = timeLineAdapter

        viewModel.getTimeLine(uid).observe(viewLifecycleOwner){ timeLineList->
            // 날짜 순으로 정렬
            timeLineList
                .sortWith(compareBy<TimeLine> {it.date.split("-")[0].toInt()}
                    .thenBy { it.date.split("-")[1].toInt() }
                    .thenBy { it.date.split("-")[2].toInt() }
            )
            // 타임라인의 모든 날짜를 파악해서 해당 년도의 가장 첫번째 타임라인의 인덱스를 저장
            val idxMap = mutableMapOf<String, Int>()
            timeLineList.forEachIndexed { index, timeLine ->
                val year = timeLine.date.split("-")[0]
                if(idxMap[year] == null)
                    idxMap[year] = index
                timeLine.year = "" // 초기화
            }
            idxMap.forEach { (year, idx) ->
                timeLineList[idx].year = year
            }

            timeLineAdapter.submitList(timeLineList)
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
            if(selectedTimeLines.size == 0){ //선택된 타임라인이 0개임
                Toast.makeText(requireContext(),"삭제할 타임라인을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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

        // 수정 버튼
        binding.updateFab.setOnClickListener {
            if(selectedTimeLines.size == 0){
                Toast.makeText(requireContext(),"수정할 타임라인을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(selectedTimeLines.size > 1){
                Toast.makeText(requireContext(),"수정은 한번에 한개만 가능합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val updateItem = selectedTimeLines.first()
            val dlg = AddDialog((activity as MainActivity), updateItem)
            dlg.show{ timeLine->
                viewModel.updateTimeLine(uid, timeLine)
                selectedTimeLines.clear()
            }


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
            ObjectAnimator.ofFloat(binding.updateFab, "translationY", 0f).apply { start() }
            binding.mainFab.setImageResource(R.drawable.ic_baseline_add_24)


        }else{ // 플로팅 액션 버튼 열기
            ObjectAnimator.ofFloat(binding.deleteFab, "translationY", -200f).apply { start() }
            ObjectAnimator.ofFloat(binding.updateFab, "translationY", -350f).apply { start() }
            ObjectAnimator.ofFloat(binding.addFab, "translationY", -500f).apply { start() }
            binding.mainFab.setImageResource(R.drawable.close)
        }

        isFabOpen = !isFabOpen
    }


}