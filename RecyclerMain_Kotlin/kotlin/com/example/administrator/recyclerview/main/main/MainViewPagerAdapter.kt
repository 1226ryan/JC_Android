package com.example.administrator.recyclerview.main.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class MainViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return MainFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return 4
    }
}
