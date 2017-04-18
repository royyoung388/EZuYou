package com.you.ezuyou.Home;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.you.ezuyou.InternetUtls.HomeUtils.GetAdvertisement;
import com.you.ezuyou.InternetUtls.HomeUtils.GetHome_Item;
import com.you.ezuyou.R;
import com.you.ezuyou.Search.Search_Home;


/**
 * Created by Administrator on 2017/2/28.
 */

public class Home extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View view;
    private ImageView advertisement;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Home_Item homeItem;

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
                    homeItem = (Home_Item) msg.obj;
                    Item_Adapter adapter = new Item_Adapter(homeItem.Data, view.getContext());
                    listView.setAdapter(adapter);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("fragement1启动");
        view = inflater.inflate(R.layout.home, container, false);

        //使用toolbar
        Toolbar home_toolbar = (Toolbar) view.findViewById(R.id.home_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(home_toolbar);

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        //小圈圈的颜色。转一圈换一种颜色，每一圈耗时1s。
        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.green, R.color.blue, R.color.orange);
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getActivity(), R.color.white));
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        //点击搜索栏事件
        LinearLayout search = (LinearLayout) view.findViewById(R.id.home_searh);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Search_Home.class));
            }
        });

        //广告图片
        advertisement = (ImageView) view.findViewById(R.id.home_image);

        //使用listview
        listView = (ListView) view.findViewById(R.id.home_listview);
        //HomeUtils.getHome_Item(handler);

        flush();

        //listview点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Home_Item_Detil.class);
                intent.putExtra("tag", Integer.parseInt(homeItem.Data.get(position).getTag()));
                startActivity(intent);
            }
        });
        return view;
    }

    //刷新
    public void flush() {
        Thread getAdvertisement = new GetAdvertisement(handler);
        getAdvertisement.start();
        try {
            getAdvertisement.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread getHome_Item = new GetHome_Item(handler, -1, getActivity());
        getHome_Item.start();
        try {
            getHome_Item.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        flush();
        // 停止刷新
        swipeRefreshLayout.setRefreshing(false);
    }
}
