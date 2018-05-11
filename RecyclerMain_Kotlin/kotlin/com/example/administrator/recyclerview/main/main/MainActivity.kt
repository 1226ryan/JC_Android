package com.example.administrator.recyclerview.main.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.administrator.recyclerview.R
import com.example.administrator.recyclerview.main_second.SecondActivity

import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViewPager()
    }

    private fun setViewPager() {
        val mainViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        mainActivityViewPager.adapter = mainViewPagerAdapter
        mainActivityTabLayout.setupWithViewPager(mainActivityViewPager)

        mainActivityButton.setOnClickListener {
            startActivity<SecondActivity>()
        }
    }
}
