package com.dldmswo1209.portfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.Model.Chat
import com.dldmswo1209.portfolio.Model.MY_CHAT
import com.dldmswo1209.portfolio.Model.RealChat
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.databinding.MyChatItemBinding
import com.dldmswo1209.portfolio.databinding.OtherChatItemBinding

class RealChatListAdapter(val currentUser: User, val receiver: User): ListAdapter<RealChat, RecyclerView.ViewHolder>(diffUtil) {


    override fun getItemViewType(position: Int): Int {
        // currentList 의 sender 에는 채팅을 보낸 사람
        // receiver 에는 받는 사람
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
            binding.dateTextView.text = chat.date_time

            Glide.with(binding.root)
                .load(receiver.profile?.image)
                .circleCrop()
                .into(binding.profileImageView)

            if(chat.sender.profile?.image == "" || chat.sender.profile?.image == null){
                Glide.with(binding.root)
                    .load(R.drawable.profile)
                    .circleCrop()
                    .into(binding.profileImageView)
            }
            binding.nameTextView.text = receiver.name
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