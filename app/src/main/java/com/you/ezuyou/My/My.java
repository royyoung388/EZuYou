package com.you.ezuyou.My;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.you.ezuyou.InternetUtls.MyUtils.Start_My;
import com.you.ezuyou.R;


/**
 * Created by Administrator on 2017/3/9.
 */

public class My extends Fragment implements View.OnClickListener{

    private ImageView my_image;
    private TextView my_name, my_information, my_favorite, my_history, my_strategy, my_purchase_ing, getMy_purchase_already;
    private View view;
    private String my = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //获取了my信息，刷新界面
                case 1:
                    my = (String) msg.obj;
                    My_Utils_my my_utils_my = new My_Utils_my(my);
                    my_name.setText(my_utils_my.getUsername());
            }
        }
    };

    public My() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("fragement5启动");
        view = inflater.inflate(R.layout.my, container, false);

        //初始化控件
        my_image = (ImageView) view.findViewById(R.id.my_image);
        my_name = (TextView) view.findViewById(R.id.my_name);
        my_information = (TextView) view.findViewById(R.id.my_information);
        my_favorite = (TextView) view.findViewById(R.id.my_favorite);
        my_history = (TextView) view.findViewById(R.id.my_history);
        my_strategy = (TextView) view.findViewById(R.id.my_strategy);
        my_purchase_ing = (TextView) view.findViewById(R.id.my_purchase_ing);
        getMy_purchase_already = (TextView) view.findViewById(R.id.my_purchase_already);

        //设置监听器
        my_image.setOnClickListener(this);
        my_name.setOnClickListener(this);
        my_information.setOnClickListener(this);
        my_favorite.setOnClickListener(this);
        my_history.setOnClickListener(this);
        my_strategy.setOnClickListener(this);
        my_purchase_ing.setOnClickListener(this);
        getMy_purchase_already.setOnClickListener(this);

        //启动线程
        Thread my = new Start_My(handler, getActivity());
        my.start();
        try {
            my.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return view;
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
                Intent intent = new Intent(getActivity(), My_Information.class);
                intent.putExtra("my", my);
                startActivity(intent);
                break;
            case R.id.my_favorite:
                startActivity(new Intent(getActivity(), My_Favorite.class));
                break;
            case R.id.my_history:
                startActivity(new Intent(getActivity(), My_Trade.class));
                break;
            case R.id.my_strategy:
                startActivity(new Intent(getActivity(), My_Strategy.class));
                break;
            case R.id.my_purchase_ing:
                startActivity(new Intent(getActivity(), My_Purchase_Ing.class));
                break;
            case R.id.my_purchase_already:
                startActivity(new Intent(getActivity(), My_Purchase_Already.class));
                break;
        }
    }
}
