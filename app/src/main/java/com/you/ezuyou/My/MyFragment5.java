package com.you.ezuyou.My;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.you.ezuyou.R;


/**
 * Created by Administrator on 2017/3/9.
 */

public class MyFragment5 extends Fragment implements View.OnClickListener{

    private TextView my_list1, my_list2, my_list3, my_list4, my_list5, my_list6;
    private ImageView my_image;

    public MyFragment5() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment5, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    //初始化控件,并设置监听器
    private void initView() {
        //初始化控件
        my_image = (ImageView) getView().findViewById(R.id.my_image);
        my_list1 = (TextView) getView().findViewById(R.id.my_name);
        my_list2 = (TextView) getView().findViewById(R.id.my_gerxx);
        my_list3 = (TextView) getView().findViewById(R.id.my_shoucj);
        my_list4 = (TextView) getView().findViewById(R.id.my_lisxx);
        my_list5 = (TextView) getView().findViewById(R.id.my_wodgl);
        my_list6 = (TextView) getView().findViewById(R.id.my_zhengz);

        //设置监听器
        my_image.setOnClickListener(this);
        my_list1.setOnClickListener(this);
        my_list2.setOnClickListener(this);
        my_list3.setOnClickListener(this);
        my_list4.setOnClickListener(this);
        my_list5.setOnClickListener(this);
        my_list6.setOnClickListener(this);
    }

    //处理监听动作
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_image:
                break;
            case R.id.my_name:
                break;
            case R.id.my_gerxx:
                break;
            case R.id.my_shoucj:
                break;
            case R.id.my_lisxx:
                break;
            case R.id.my_wodgl:
                break;
            case R.id.my_zhengz:
                break;
        }
    }
}
