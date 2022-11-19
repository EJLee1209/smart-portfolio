package com.dldmswo1209.portfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dldmswo1209.portfolio.Model.Chat
import com.dldmswo1209.portfolio.Model.MY_CHAT
import com.dldmswo1209.portfolio.databinding.MyChatItemBinding
import com.dldmswo1209.portfolio.databinding.OtherChatItemBinding

// 채팅 리스트 어답터(기본 모드)
class ChatListAdapterModeOrigin: ListAdapter<Chat, RecyclerView.ViewHolder>(diffUtil) {

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
        fun bind(chat: Chat){
            binding.chatTextView.text = chat.content
        }
    }
    inner class OtherChatViewHolder(val binding: OtherChatItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chat: Chat){
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
        private val diffUtil = object: DiffUtil.ItemCallback<Chat>(){
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem == newItem
            }

        }
    }

}