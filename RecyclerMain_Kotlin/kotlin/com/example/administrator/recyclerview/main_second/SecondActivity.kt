package com.example.administrator.recyclerview.main_second

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import com.example.administrator.recyclerview.R

import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        if (SecondActivityTabLayout.tabCount != 0)
            SecondActivityTabLayout.removeAllTabs()

        setTabLayout()
        setViewPager()
    }

    private fun setTabLayout() {
        SecondActivityTabLayout.addTab(SecondActivityTabLayout.newTab().setText(R.string.first))
        SecondActivityTabLayout.addTab(SecondActivityTabLayout.newTab().setText(R.string.second))
        SecondActivityTabLayout.tabGravity = TabLayout.GRAVITY_FILL
        SecondActivityTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                SecondActivityViewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun setViewPager() {
        SecondActivityViewPager.adapter = SecondViewPagerAdapter(supportFragmentManager, SecondActivityTabLayout.tabCount)
        SecondActivityViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(SecondActivityTabLayout))
    }
}
