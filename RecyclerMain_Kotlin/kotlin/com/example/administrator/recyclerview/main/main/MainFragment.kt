package com.example.administrator.recyclerview.main.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.administrator.recyclerview.R

class MainFragment : Fragment() {
    private var pageNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = arguments!!.getInt("PAGE_NUMBER")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return when(pageNumber) {
            0 -> inflater.inflate(R.layout.fragment_main_first, container, false)
            1 -> inflater.inflate(R.layout.fragment_main_second, container, false)
            2 -> inflater.inflate(R.layout.fragment_main_third, container, false)
            else -> inflater.inflate(R.layout.fragment_main_forth, container, false)
        }
    }

    companion object {
        fun newInstance(pageNumber: Int): MainFragment {
            val mainFragment = MainFragment()
            val bundle = Bundle()
            bundle.putInt("PAGE_NUMBER", pageNumber)
            mainFragment.arguments = bundle

            return mainFragment
        }
    }
}
