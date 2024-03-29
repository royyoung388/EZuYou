package com.you.ezuyou.My;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.you.ezuyou.InternetUtls.StrategyUtils.GetStrategy_Item;
import com.you.ezuyou.R;
import com.you.ezuyou.Strategy.Strategy_Adapter;
import com.you.ezuyou.Strategy.Strategy_Detil;
import com.you.ezuyou.Strategy.Strategy_Item;

/**
 * Created by Administrator on 2017/3/23.
 */

public class My_Strategy extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Strategy_Item strategy_item;

    private SharedPreferences sp;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //指定id的Strategy信息
                case 2:
                    strategy_item = (Strategy_Item) msg.obj;
                    Strategy_Adapter adapter = new Strategy_Adapter(strategy_item.Data, My_Strategy.this);
                    listView.setAdapter(adapter);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_strategy);

        sp = this.getSharedPreferences("login", MODE_PRIVATE);

        bindVIew();

        flush();
    }

    private void bindVIew() {
        Toolbar my_strategy_toolbar = (Toolbar) findViewById(R.id.my_strategy_toolbar);
        my_strategy_toolbar.setTitle("");
        my_strategy_toolbar.setNavigationIcon(R.drawable.icon_back);
        setSupportActionBar(my_strategy_toolbar);

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_strategy_swip);
        swipeRefreshLayout.setOnRefreshListener(this);
        //小圈圈的颜色。转一圈换一种颜色，每一圈耗时1s。
        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.green, R.color.blue, R.color.orange);
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.white));
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        listView = (ListView) findViewById(R.id.my_strategy_list);

        //listview点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(My_Strategy.this, Strategy_Detil.class);
                intent.putExtra("tag", Integer.parseInt(strategy_item.Data.get(position).getTag()));
                startActivity(intent);
            }
        });
    }

    //刷新
    public void flush() {
        Thread getStrategy = new GetStrategy_Item(handler, sp.getString("id", null), -2);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
