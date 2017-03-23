package com.you.ezuyou.My;

import android.app.Fragment;
import android.content.Intent;
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

public class My extends Fragment implements View.OnClickListener{

    private ImageView my_image;
    private TextView my_name, my_information, my_favorite, my_history, my_strategy, my_purchase;

    public My() {
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
        my_name = (TextView) getView().findViewById(R.id.my_name);
        my_information = (TextView) getView().findViewById(R.id.my_information);
        my_favorite = (TextView) getView().findViewById(R.id.my_favorite);
        my_history = (TextView) getView().findViewById(R.id.my_history);
        my_strategy = (TextView) getView().findViewById(R.id.my_strategy);
        my_purchase = (TextView) getView().findViewById(R.id.my_purchase);

        //设置监听器
        my_image.setOnClickListener(this);
        my_name.setOnClickListener(this);
        my_information.setOnClickListener(this);
        my_favorite.setOnClickListener(this);
        my_history.setOnClickListener(this);
        my_strategy.setOnClickListener(this);
        my_purchase.setOnClickListener(this);
    }

    //处理监听动作
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_image:
                break;
            case R.id.my_name:
                break;
            case R.id.my_information:
                startActivity(new Intent(getActivity(), My_Information.class));
                break;
            case R.id.my_favorite:
                startActivity(new Intent(getActivity(), My_Favorite.class));
                break;
            case R.id.my_history:
                startActivity(new Intent(getActivity(), My_History.class));
                break;
            case R.id.my_strategy:
                startActivity(new Intent(getActivity(), My_Strategy.class));
                break;
            case R.id.my_purchase:
                startActivity(new Intent(getActivity(), My_Purchase.class));
                break;
        }
    }
}
