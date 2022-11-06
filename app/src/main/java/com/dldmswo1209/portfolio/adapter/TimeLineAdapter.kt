package com.dldmswo1209.portfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dldmswo1209.portfolio.databinding.TimelineItemBinding
import com.dldmswo1209.portfolio.entity.TimeLineEntity

class TimeLineAdapter : ListAdapter<TimeLineEntity, TimeLineAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(val binding: TimelineItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: TimeLineEntity){
            binding.contentTextView.text = item.content
            binding.dateTextView.text = item.date
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TimelineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    companion object{
        private val diffUtil = object: DiffUtil.ItemCallback<TimeLineEntity>(){
            override fun areItemsTheSame(oldItem: TimeLineEntity, newItem: TimeLineEntity): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: TimeLineEntity, newItem: TimeLineEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}

