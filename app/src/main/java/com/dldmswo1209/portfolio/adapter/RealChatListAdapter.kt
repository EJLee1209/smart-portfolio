package com.dldmswo1209.portfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dldmswo1209.portfolio.Model.Chat
import com.dldmswo1209.portfolio.Model.MY_CHAT
import com.dldmswo1209.portfolio.Model.RealChat
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.databinding.MyChatItemBinding
import com.dldmswo1209.portfolio.databinding.OtherChatItemBinding

class RealChatListAdapter(val currentUser: User): ListAdapter<RealChat, RecyclerView.ViewHolder>(diffUtil) {

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(currentList[position].sender.uid == currentUser.uid){ // 내 채팅
            1
        }else{ // 상대방 채팅
            0
        }
    }

    inner class MyChatViewHolder(val binding: MyChatItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chat: RealChat){
            binding.chatTextView.text = chat.message
        }
    }
    inner class OtherChatViewHolder(val binding: OtherChatItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chat: RealChat){
            binding.chatTextView.text = chat.message
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
        private val diffUtil = object: DiffUtil.ItemCallback<RealChat>(){
            override fun areItemsTheSame(oldItem: RealChat, newItem: RealChat): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: RealChat, newItem: RealChat): Boolean {
                return oldItem == newItem
            }

        }
    }

}