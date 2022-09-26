package com.dldmswo1209.portfolio.adapter


// 카드 포트폴리오 리사이클러뷰 어답터
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dldmswo1209.portfolio.databinding.CardItemBinding
import com.dldmswo1209.portfolio.entity.CardEntity
import com.dldmswo1209.portfolio.swipeHelper.ItemDragHelperCallback
import java.util.*
import kotlin.collections.ArrayList

class CardListAdapter(val itemClick: (CardEntity,Int)->(Unit)): ListAdapter<CardEntity, CardListAdapter.ViewHolder>(diffUtil), ItemDragHelperCallback.OnItemMoveListener{

    private lateinit var dragListener: OnStartDragListener

    inner class ViewHolder(val binding: CardItemBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ClickableViewAccessibility")
        fun bind(card: CardEntity){
            binding.titleTextView.text = card.title
            binding.cardDetailTextView.text = card.content
            Glide.with(binding.root)
                .load(card.image?.toUri())
                .into(binding.cardImageView)

            binding.root.setOnClickListener {
                itemClick(card,GO_HOMEPAGE)
            }
            binding.deleteCardTextView.setOnClickListener {
                itemClick(card,DELETE_CARD)
            }
            binding.editCardTextView.setOnClickListener {
                itemClick(card,EDIT_CARD)
            }
            binding.dragImageView.setOnTouchListener { view, event ->
                if(event.action == MotionEvent.ACTION_DOWN){
                    dragListener.onStartDrag(this)
                }
                return@setOnTouchListener false
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
        const val GO_HOMEPAGE = 0
        const val EDIT_CARD = 1
        const val DELETE_CARD = 2
    }

    interface OnStartDragListener{
        fun onStartDrag(viewHolder: ViewHolder)
    }

    fun startDrag(listener: OnStartDragListener){
        this.dragListener = listener
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        val item = currentList[fromPosition]
        val newList = ArrayList<CardEntity>()
        newList.addAll(currentList)
        newList.removeAt(fromPosition)
        newList.add(toPosition, item)
        submitList(newList)
    }
}