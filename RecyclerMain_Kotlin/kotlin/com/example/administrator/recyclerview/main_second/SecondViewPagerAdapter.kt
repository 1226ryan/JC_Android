package com.example.administrator.recyclerview.main_second

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.example.administrator.recyclerview.recycler1.recycler1.RecyclerFragment1
import com.example.administrator.recyclerview.recycler2.RecyclerFragment2

class SecondViewPagerAdapter(fm: FragmentManager, private val tabItemCount: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return getTabFragment(position)
    }

    override fun getCount(): Int {
        return tabItemCount
    }

    private fun getTabFragment(position: Int): Fragment {
        return when(position) {
            0 -> RecyclerFragment1.newInstance()
            1 -> RecyclerFragment2.newInstance()
            else -> RecyclerFragment1.newInstance()
        }
    }
}
