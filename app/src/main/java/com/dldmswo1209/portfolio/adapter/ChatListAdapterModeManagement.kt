package com.dldmswo1209.portfolio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dldmswo1209.portfolio.Model.Chat
import com.dldmswo1209.portfolio.Model.MY_CHAT
import com.dldmswo1209.portfolio.databinding.MyChatItemBinding
import com.dldmswo1209.portfolio.databinding.OtherChatItemBinding
// 채팅 리스트 어답터(관리자 모드)
class ChatListAdapterModeManagement(val itemClick : (Chat, Int) -> (Unit) ): ListAdapter<Chat, RecyclerView.ViewHolder>(diffUtil) {

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
            binding.deleteButton.isVisible = true
            binding.editButton.isVisible = true
            binding.deleteButton.setOnClickListener {
                itemClick(chat, DELETE_CHAT)
            }
            binding.editButton.setOnClickListener {
                itemClick(chat, EDIT_CHAT)
            }
        }
    }
    inner class OtherChatViewHolder(val binding: OtherChatItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chat: Chat){
            binding.chatTextView.text = chat.content
            binding.deleteButton.isVisible = true
            binding.editButton.isVisible = true
            binding.deleteButton.setOnClickListener {
                itemClick(chat, DELETE_CHAT)
            }
            binding.editButton.setOnClickListener {
                itemClick(chat, EDIT_CHAT)
            }
            binding.profileImageView.visibility = View.GONE
            binding.nameTextView.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1 -> {
                OtherChatViewHolder(OtherChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else ->{
                MyChatViewHolder(MyChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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

        const val DELETE_CHAT = 0
        const val EDIT_CHAT = 1
    }

}