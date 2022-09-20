package com.dldmswo1209.portfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dldmswo1209.portfolio.databinding.CardItemBinding
import com.dldmswo1209.portfolio.model.Card

class CardListAdapter : ListAdapter<Card, CardListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(card: Card){
            binding.titleTextView.text = card.title
            binding.cardDetailTextView.text = card.content

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : ViewHolder = ViewHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        private val diffUtil = object: DiffUtil.ItemCallback<Card>(){
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem == newItem
            }

        }
    }


}