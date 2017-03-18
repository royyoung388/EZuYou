package com.you.ezuyou.Home;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.you.ezuyou.R;


/**
 * Created by Administrator on 2017/2/28.
 */

public class MyFragment1 extends Fragment {

    public MyFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.whitesmoke));
        return view;
    }
}
