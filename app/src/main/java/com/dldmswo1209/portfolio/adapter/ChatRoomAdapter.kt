package com.dldmswo1209.portfolio.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.Model.ChatRoom
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.databinding.UserItemBinding

class ChatRoomAdapter(val currentUser: User, val itemClicked : (ChatRoom)->(Unit)): ListAdapter<ChatRoom, ChatRoomAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(room: ChatRoom){
            if(currentUser.uid == room.sender.uid){
                Log.d("testt", "bind: current user = sender")
                binding.nameTextView.text = room.receiver.name
                Glide.with(binding.root)
                    .load(room.receiver.profile?.image)
                    .circleCrop()
                    .into(binding.profileImageView)
                binding.introTextView.text = room.lastMessage // user_item 재활용이라 이름이 안맞음
                binding.timeTextView.text = room.lastTime
            }else{
                Log.d("testt", "bind: current user = receiver")
                binding.nameTextView.text = room.sender.name
                Glide.with(binding.root)
                    .load(room.sender.profile?.image)
                    .circleCrop()
                    .into(binding.profileImageView)
                binding.introTextView.text = room.lastMessage // user_item 재활용이라 이름이 안맞음
                binding.timeTextView.text = room.lastTime
            }

            binding.root.setOnClickListener {
                itemClicked(room)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<ChatRoom>(){
            override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
                return oldItem == newItem
            }

        }
    }
}