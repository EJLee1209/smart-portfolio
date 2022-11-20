package com.dldmswo1209.portfolio.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dldmswo1209.portfolio.Fragment.*

// 뷰페이져 어답터
class ViewPagerAdapter(fragmentActivity: FragmentActivity, val mode: Int) : FragmentStateAdapter(fragmentActivity) {
    // fragment 의 수는 4개(홈, 타임라인, 채팅, 카드)
    override fun getItemCount(): Int{
        return when(mode){
            MODE_NORMAL -> 4
            else -> 2
        }
    }

    override fun createFragment(position: Int): Fragment {
        if(mode == MODE_NORMAL) { // 일반 사용자 모드
            return when (position) {
                0 -> HomeFragment()
                1 -> TimeLineFragment()
                2 -> ChatFragment()
                else -> CardFragment()
            }
        }
        else{ // 채용 담당자 모드
            return when(position){
                0 -> AllUserFragment()
                else -> ChatListFragment()
            }
        }
    }
}



const val MODE_SUPER = 1
const val MODE_NORMAL = 0