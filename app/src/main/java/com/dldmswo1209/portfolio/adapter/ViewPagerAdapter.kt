package com.dldmswo1209.portfolio.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dldmswo1209.portfolio.Fragment.CardFragment
import com.dldmswo1209.portfolio.Fragment.ChatFragment
import com.dldmswo1209.portfolio.Fragment.HomeFragment
import com.dldmswo1209.portfolio.Fragment.TimeLineFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
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