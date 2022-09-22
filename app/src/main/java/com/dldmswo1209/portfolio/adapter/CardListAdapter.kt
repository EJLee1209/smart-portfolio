package com.dldmswo1209.portfolio.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.databinding.CardItemBinding
import com.dldmswo1209.portfolio.entity.CardEntity

class CardListAdapter(val itemClick: (CardEntity)->(Unit)): ListAdapter<CardEntity, CardListAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(val binding: CardItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(card: CardEntity){
            binding.titleTextView.text = card.title
            binding.cardDetailTextView.text = card.content
            Glide.with(binding.root)
                .load(card.image?.toUri())
                .into(binding.cardImageView)

            binding.root.setOnClickListener {
                itemClick(card)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    companion object{
        private val diffUtil = object: DiffUtil.ItemCallback<CardEntity>(){
            override fun areItemsTheSame(oldItem: CardEntity, newItem: CardEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CardEntity, newItem: CardEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}