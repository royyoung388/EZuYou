package com.you.ezuyou.Strategy;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.you.ezuyou.InternetUtls.StrategyUtils.GetStrategy_Item;
import com.you.ezuyou.R;
import com.you.ezuyou.Search.Search_Strategy;


/**
 * Created by Administrator on 2017/2/28.
 */

public class Strategy extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View view;
    private ImageView release;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SharedPreferences sp;

    private Strategy_Item strategy_item;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //所有Strategy信息
                case 2:
                    strategy_item = (Strategy_Item) msg.obj;
                    Strategy_Adapter adapter = new Strategy_Adapter(strategy_item.Data, view.getContext());
                    listView.setAdapter(adapter);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public Strategy() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("fragment2启动");
        view = inflater.inflate(R.layout.strategy, container, false);

        //使用toolbar
        Toolbar home_toolbar = (Toolbar) view.findViewById(R.id.strategy_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(home_toolbar);

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.strategy_swip);
        swipeRefreshLayout.setOnRefreshListener(this);
        //小圈圈的颜色。转一圈换一种颜色，每一圈耗时1s。
        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.green, R.color.blue, R.color.orange);
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getActivity(), R.color.white));
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        //发布
        release = (ImageView) view.findViewById(R.id.strategy_release);
        //点击搜索栏事件
        LinearLayout search = (LinearLayout) view.findViewById(R.id.strategy_searh);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Search_Strategy.class));
            }
        });

        //ListView的使用
        listView = (ListView) view.findViewById(R.id.strategy_list);

        flush();

        //listview点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Strategy_Detil.class);
                intent.putExtra("tag", strategy_item.Data.get(position).getTag());
                startActivity(intent);
            }
        });

        //发布
        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Strategy_Release.class));
            }
        });

        return view;
    }

    //刷新
    public void flush() {
        sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        Thread getStrategy = new GetStrategy_Item(handler, sp.getString("id", null), -1);
        getStrategy.start();
        try {
            getStrategy.join();
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