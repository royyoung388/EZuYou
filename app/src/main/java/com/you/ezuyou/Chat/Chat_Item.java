package com.you.ezuyou.Chat;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.you.ezuyou.R;


/**
 * Created by Administrator on 2017/2/28.
 */

public class Chat_Item extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    //private String id, useranme, message;
    private Chat_Item_Adapter chatItemAdapter = Chat_Item_Adapter.getInstance();

    private final int MY_MESSAGE = 1;
    private final int OTHER_MESSAGE = 0;

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
                    String id = bundle.getString("id");
                    String useranme = bundle.getString("useranme");
                    String message = bundle.getString("message");
                    chatItemAdapter.addItem(id, useranme, message, OTHER_MESSAGE);
                    break;
            }
        }
    };

    //其他类通过该方法启动聊天线程
    public void sartChat() {
        //通过单例模式获取实例，监听接收消息
        Chat_From_Other.getInstance(handler);
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
        recyclerView.setAdapter(chatItemAdapter);

        //item点击事件，启动聊天窗口，在这里实现
        chatItemAdapter.setOnRecyclerviewClick(new Chat_Item_Adapter.OnRecyclerviewClick() {
            @Override
            public void OnItemClick(View view, String[] strings) {
                Intent intent = new Intent(getActivity(), Chat_Show.class);
                //对方id
                intent.putExtra("id", strings[0]);
                //对方姓名
                intent.putExtra("person", strings[1]);
                intent.putExtra("message", strings[2]);
                intent.putExtra("status", strings[3]);
                startActivity(intent);
            }
        });

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


