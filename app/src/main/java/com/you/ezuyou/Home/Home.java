package com.you.ezuyou.Home;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.you.ezuyou.InternetUtils.HomeUtils.HomeUtils;
import com.you.ezuyou.R;
import com.you.ezuyou.Rearch.Search;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/2/28.
 */

public class Home extends Fragment {

    private int[] image = new int[]{R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image};

    public Home() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        //使用toolbar
        Toolbar home_toolbar = (Toolbar) view.findViewById(R.id.home_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(home_toolbar);

        //点击搜索栏事件
        LinearLayout search = (LinearLayout) view.findViewById(R.id.home_searh);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Search.class));
            }
        });

        //获取广告图片
        ImageView advertisement = (ImageView) view.findViewById(R.id.home_image);
        HomeUtils.GetAdvertisement(advertisement);


        //使用listview
        ListView listView = (ListView) view.findViewById(R.id.home_listview);
        HomeUtils.getHome_Item(view, listView, image);

        return view;
    }


}
