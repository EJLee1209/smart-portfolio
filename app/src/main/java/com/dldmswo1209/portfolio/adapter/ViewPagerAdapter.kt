package com.dldmswo1209.portfolio.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dldmswo1209.portfolio.Fragment.CardFragment
import com.dldmswo1209.portfolio.Fragment.ChatFragment
import com.dldmswo1209.portfolio.Fragment.HomeFragment
import com.dldmswo1209.portfolio.Fragment.TimeLineFragment

// 뷰페이져 어답터
class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    // fragment 의 수는 4개(홈, 타임라인, 채팅, 카드)
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> TimeLineFragment()
            2 -> ChatFragment()
            else -> CardFragment()
        }
    }
}