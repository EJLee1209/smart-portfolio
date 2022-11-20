package com.dldmswo1209.portfolio.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.databinding.UserItemBinding

class UserListAdapter(val itemClicked : (User)->(Unit)): ListAdapter<User, UserListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.nameTextView.text = user.name
            binding.introTextView.text = user.profile?.introduce
            if(binding.introTextView.text.toString() == "")
                binding.introTextView.visibility = View.GONE

            Glide.with(binding.root)
                .load(user.profile?.image)
                .circleCrop()
                .into(binding.profileImageView)

            if(user.profile?.image == "" || user.profile?.image == null){
                Glide.with(binding.root)
                    .load(R.drawable.profile)
                    .circleCrop()
                    .into(binding.profileImageView)
            }

            binding.root.setOnClickListener {
                itemClicked(user) // 아이템 클릭은 콜백으로 처리
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
        val diffUtil = object: DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

        }
    }
}