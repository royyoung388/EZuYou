package com.you.ezuyou.Strategy;

import android.app.Fragment;
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
import android.widget.ListView;

import com.you.ezuyou.InternetUtls.StrategyUtils.GetStrategy_Item;
import com.you.ezuyou.R;


/**
 * Created by Administrator on 2017/2/28.
 */

public class Strategy extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View view;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //item
                case 2:
                    Strategy_Adapter adapter = new Strategy_Adapter(((Strategy_Item)msg.obj).Data, view.getContext());
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

        //ListView的使用
        listView = (ListView) view.findViewById(R.id.strategy_list);
        Thread getstrategy = new GetStrategy_Item(handler, -1);
        getstrategy.start();
        try {
            getstrategy.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //listview点击事件
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Home_Item_Detil.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });*/
        return view;
    }

    //刷新
    public void Flush() {
        Thread getStrategy = new GetStrategy_Item(handler, -1);
        getStrategy.start();
        try {
            getStrategy.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        Flush();
        // 停止刷新
        swipeRefreshLayout.setRefreshing(false);
    }
}