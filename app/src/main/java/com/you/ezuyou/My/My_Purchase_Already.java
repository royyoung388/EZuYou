package com.you.ezuyou.My;

import android.content.Intent;
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

import com.you.ezuyou.Home.Home_Item_Detil;
import com.you.ezuyou.Home.Home_Item;
import com.you.ezuyou.InternetUtls.HomeUtils.GetHome_Item;
import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/4/16.
 */

public class My_Purchase_Already extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private Home_Item homeItem;
    private ListView listView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //item
                case 2:
                    homeItem = (Home_Item) msg.obj;
                    My_Adapter adapter = new My_Adapter(homeItem.Data, My_Purchase_Already.this);
                    listView.setAdapter(adapter);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_purchase_already);

        Toolbar my_history_toolbar = (Toolbar) findViewById(R.id.my_purchase_already_toolbar);
        my_history_toolbar.setTitle("");
        my_history_toolbar.setNavigationIcon(R.drawable.icon_back);
        setSupportActionBar(my_history_toolbar);

        bindView();

        //刷新
        flush();
    }

    private void bindView() {

        //使用listview
        listView = (ListView) findViewById(R.id.my_purcahse_already_list);

        //listview点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(My_Purchase_Already.this, Home_Item_Detil.class);
                intent.putExtra("tag", Integer.parseInt(homeItem.Data.get(position).getTag()));
                startActivity(intent);
            }
        });

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_purchase_already_swip);
        swipeRefreshLayout.setOnRefreshListener(this);
        //小圈圈的颜色。转一圈换一种颜色，每一圈耗时1s。
        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.green, R.color.blue, R.color.orange);
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.white));
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //刷新
    public void flush() {
        // 获取指定id的status为0的item
        Thread getHome_Item = new GetHome_Item(handler, -4, this);
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
