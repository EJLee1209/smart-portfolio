package com.dldmswo1209.portfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dldmswo1209.portfolio.databinding.MyChatItemBinding
import com.dldmswo1209.portfolio.databinding.OtherChatItemBinding
import com.dldmswo1209.portfolio.entity.ChatEntity
import com.dldmswo1209.portfolio.entity.MY_CHAT

// 채팅 리스트 어답터(기본 모드)
class ChatListAdapterModeOrigin: ListAdapter<ChatEntity, RecyclerView.ViewHolder>(diffUtil) {

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(currentList[position].type == MY_CHAT){
            1
        }else{
            0
        }
    }

    inner class MyChatViewHolder(val binding: MyChatItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chat: ChatEntity){
            binding.chatTextView.text = chat.content
        }
    }
    inner class OtherChatViewHolder(val binding: OtherChatItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chat: ChatEntity){
            binding.chatTextView.text = chat.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1 -> {
                MyChatViewHolder(MyChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else ->{
                OtherChatViewHolder(OtherChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MyChatViewHolder){
            (holder as MyChatViewHolder).bind(currentList[position])
        }else{
            (holder as OtherChatViewHolder).bind(currentList[position])
        }
    }
    companion object{
        private val diffUtil = object: DiffUtil.ItemCallback<ChatEntity>(){
            override fun areItemsTheSame(oldItem: ChatEntity, newItem: ChatEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ChatEntity, newItem: ChatEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

}