package com.dldmswo1209.portfolio.swipeHelper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

// 카드 포트폴리오 드래그 기능을 구현하기 위해 ItemTouchHelper.Callback() 상속받은 클래스
// 이 부분에 대한 내용은 아직 공부가 필요합니다.
class ItemDragHelperCallback(private val itemMoveListener: OnItemMoveListener): ItemTouchHelper.Callback() {
    interface OnItemMoveListener{
        fun onItemMoved(fromPosition: Int, toPosition: Int)
    }
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {
        return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        itemMoveListener.onItemMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    override fun isLongPressDragEnabled(): Boolean = true
}