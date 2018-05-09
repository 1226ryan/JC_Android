package com.example.administrator.recyclerview.main_second;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.recyclerview.recycler1.RecyclerFragment1;
import com.example.administrator.recyclerview.recycler2.RecyclerFragment2;

public class SecondViewPagerAdapter extends FragmentStatePagerAdapter {
    private int tabItemCount;

    public SecondViewPagerAdapter(FragmentManager fm, int tabItemCount) {
        super(fm);
        this.tabItemCount = tabItemCount;
    }

    @Override
    public Fragment getItem(int position) {
        return getTabFragment(position);
    }

    @Override
    public int getCount() {
        return tabItemCount;
    }

    private Fragment getTabFragment(int position) {
        if(position == 0) {
            return RecyclerFragment1.newInstance();
        } else if(position == 1) {
            return RecyclerFragment2.newInstance();
        } else
            return RecyclerFragment1.newInstance();
    }
}
