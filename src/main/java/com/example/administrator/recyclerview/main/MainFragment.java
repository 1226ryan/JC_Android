package com.example.administrator.recyclerview.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.recyclerview.R;

public class MainFragment extends Fragment {
    private int pageNumber;

    public static MainFragment newInstance(int pageNumber) {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("PAGE_NUMBER", pageNumber);
        mainFragment.setArguments(bundle);

        return mainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt("PAGE_NUMBER");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        if(pageNumber == 0) rootView = inflater.inflate(R.layout.fragment_main_first, container, false);
        else if(pageNumber == 1) rootView = inflater.inflate(R.layout.fragment_main_second, container, false);
        else if(pageNumber == 2) rootView = inflater.inflate(R.layout.fragment_main_third, container, false);
        else rootView = inflater.inflate(R.layout.fragment_main_forth, container, false);

        return rootView;
    }
}
