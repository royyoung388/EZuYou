package com.you.ezuyou.Chat;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.you.ezuyou.InternetUtls.ChatUtils.Chat_From_Other;
import com.you.ezuyou.Menu.Menu;
import com.you.ezuyou.R;
import com.you.ezuyou.Release.Release;


/**
 * Created by Administrator on 2017/2/28.
 */

public class Chat_Item extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String id, useranme, message;
    private MyRAdapter myRAdapter = MyRAdapter.getInstance();


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //有新的消息，刷新界面
                //对方id，username，message
                case 1:
                    Bundle bundle = msg.getData();
                    //发送方id
                    id = bundle.getString("id");
                    useranme = bundle.getString("useranme");
                    message = bundle.getString("message");
                    myRAdapter.addItem(id, useranme, message);
                    break;
            }
        }
    };

    //其他类通过该方法启动聊天线程
    public void sart() {
        //通过单例模式获取实例，监听接收消息
        Thread chatfromother = Chat_From_Other.getInstance(handler);
    }

    public Chat_Item() {
        //通过单例模式获取实例，监听接收消息
        //Thread chatfromother = Chat_From_Other.getInstance(handler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("fragemnt4启动");
        View view = inflater.inflate(R.layout.chat_item, container, false);

        //设置RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.chat_item_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        //recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(myRAdapter);

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.chat_item_swip);
        swipeRefreshLayout.setOnRefreshListener(this);
        //小圈圈的颜色。转一圈换一种颜色，每一圈耗时1s。
        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.green, R.color.blue, R.color.orange);
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getActivity(), R.color.white));
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        return view;
    }

    //运行过程中
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onRefresh() {
        // 停止刷新
        swipeRefreshLayout.setRefreshing(false);
    }
}


