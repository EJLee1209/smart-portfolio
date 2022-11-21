package com.dldmswo1209.portfolio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dldmswo1209.portfolio.Model.TimeLine
import com.dldmswo1209.portfolio.databinding.TimelineItemBinding

class TimeLineAdapter(val itemClicked: (TimeLine)->(Unit)) : ListAdapter<TimeLine, TimeLineAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(val binding: TimelineItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: TimeLine){
            binding.contentTextView.text = item.content
            binding.dateTextView.text = item.date
            binding.screenView.visibility = View.INVISIBLE
            binding.checkImage.visibility = View.INVISIBLE
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

