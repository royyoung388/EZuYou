package com.you.ezuyou.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.you.ezuyou.R;


/**
 * Created by Administrator on 2017/2/28.
 */

public class MyFragment2 extends Fragment {

    public MyFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("fragment2启动");
        View view = inflater.inflate(R.layout.fragment2, container, false);
        TextView txt_content = (TextView) view.findViewById(R.id.text4);
        txt_content.setText("第四个Fragment");
        return view;
    }
}