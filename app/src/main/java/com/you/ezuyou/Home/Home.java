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

import com.you.ezuyou.InternetUtls.HomeUtils.GetAdvertisement;
import com.you.ezuyou.InternetUtls.HomeUtils.GetHome_Item;
import com.you.ezuyou.R;
import com.you.ezuyou.Rearch.Search;


/**
 * Created by Administrator on 2017/2/28.
 */

public class Home extends Fragment {

    private View view;
    private ImageView advertisement;
    private ListView listView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //广告图片
                case 1:
                    advertisement.setImageBitmap((Bitmap)msg.obj);
                    break;
                //item
                case 2:
                    Item_Adapter adapter = new Item_Adapter(((Item)msg.obj).Data, view.getContext());
                    listView.setAdapter(adapter);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home, container, false);

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
        advertisement = (ImageView) view.findViewById(R.id.home_image);
        //HomeUtils.GetAdvertisement(handler);
        Thread getAdvertisement = new GetAdvertisement(handler);
        getAdvertisement.start();
        try {
            getAdvertisement.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //使用listview
        listView = (ListView) view.findViewById(R.id.home_listview);
        //HomeUtils.getHome_Item(handler);
        Thread getHome_Item = new GetHome_Item(handler);
        getHome_Item.start();
        try {
            getHome_Item.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return view;
    }

    //刷新
    public void Flush() {
        Thread getAdvertisement = new GetAdvertisement(handler);
        getAdvertisement.start();
        try {
            getAdvertisement.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread getHome_Item = new GetHome_Item(handler);
        getHome_Item.start();
        try {
            getHome_Item.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
