package com.dldmswo1209.portfolio.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dldmswo1209.portfolio.Model.TimeLine
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.databinding.TimelineItemBinding

class TimeLineAdapter(val itemClicked: (TimeLine)->(Unit)) : ListAdapter<TimeLine, TimeLineAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(val binding: TimelineItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: TimeLine){
            if(item.year != ""){ // 해당 년도의 대표 타임라인(첫 타임라인)
                binding.yearTextView.text = "${item.year}년"
                binding.yearTextView.visibility = View.VISIBLE
            }else{
                binding.yearTextView.text = ""
                binding.yearTextView.visibility = View.INVISIBLE
            }

            binding.subjectTextView.text =item.subject
            binding.contentTextView.text = item.content
            binding.dateTextView.text = item.date
            binding.screenView.visibility = View.INVISIBLE
            binding.checkImage.visibility = View.INVISIBLE
            if(item.isSelected){ // 선택된 상태
                binding.screenView.visibility = View.VISIBLE // 가림막 보이기
                binding.checkImage.visibility = View.VISIBLE // 체크 이미지 보이기
            }else{
                binding.screenView.visibility = View.INVISIBLE // 가림막 가리기
                binding.checkImage.visibility = View.INVISIBLE // 체크 이미지 가리기
            }

            when(item.subject){
                "Activity"->{
                    binding.timelineCircle.setImageResource(R.drawable.circle_red)
                    binding.subjectTextView.setBackgroundResource(R.drawable.input_rectangle_red)
                }
                "Award"->{
                    binding.timelineCircle.setImageResource(R.drawable.circle_yellow)
                    binding.subjectTextView.setBackgroundResource(R.drawable.input_rectangle_yellow)
                }
                "Project"->{
                    binding.timelineCircle.setImageResource(R.drawable.circle_green)
                    binding.subjectTextView.setBackgroundResource(R.drawable.input_rectangle_green)
                }
                "Etc"->{
                    binding.timelineCircle.setImageResource(R.drawable.circle_gray)
                    binding.subjectTextView.setBackgroundResource(R.drawable.input_rectangle_gray)
                }
                else->{
                    binding.timelineCircle.setImageResource(R.drawable.circle_blue)
                    binding.subjectTextView.setBackgroundResource(R.drawable.input_rectangle_blue)

                }

            }

            //타임라인 클릭 이벤트
            binding.root.setOnClickListener {
                item.isSelected = !item.isSelected
                if(item.isSelected){ // 선택된 상태
                    binding.screenView.visibility = View.VISIBLE
                    binding.checkImage.visibility = View.VISIBLE
                }else{
                    binding.screenView.visibility = View.INVISIBLE
                    binding.checkImage.visibility = View.INVISIBLE
                }
                itemClicked(item)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TimelineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    companion object{
        private val diffUtil = object: DiffUtil.ItemCallback<TimeLine>(){
            override fun areItemsTheSame(oldItem: TimeLine, newItem: TimeLine): Boolean {
                return oldItem.key == newItem.key
            }
            override fun areContentsTheSame(oldItem: TimeLine, newItem: TimeLine): Boolean {
                return oldItem == newItem
            }
        }
    }
}

