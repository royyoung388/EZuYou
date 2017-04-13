package com.you.ezuyou.Chat;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.you.ezuyou.InternetUtls.ChatUtils.Chat_From_Other;
import com.you.ezuyou.R;
import com.you.ezuyou.utils.CircleImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/2/1.
 */

public class MyRAdapter extends RecyclerView.Adapter<MyRAdapter.ViewHolder> {

    private List<String> id, username, message;

    //使用单例模式确保实例只有一个
    // 静态实例变量加上volatile
    private static volatile MyRAdapter myRAdapter;

    // 双重检查锁
    public static MyRAdapter getInstance() {
        if (myRAdapter == null) {
            synchronized (MyRAdapter.class) {
                if (myRAdapter == null) {
                    myRAdapter = new MyRAdapter();
                }
            }
        }
        return myRAdapter;
    }

    ;

    // 私有化构造函数
    private MyRAdapter() {
        id = new ArrayList<>();
        username = new ArrayList<>();
        message = new ArrayList<>();
    }

    //添加
    public void addItem(String id, String username, String message) {
        //发送方id
        int index = this.id.indexOf(id);
        if (index != -1) {
            //删除相同的
            this.id.remove(index);
            this.username.remove(index);
            this.message.remove(index);            ;
        }
        //插入到第一个
        this.id.add(0, id);
        this.username.add(0, username);
        this.message.add(0, message);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView username, message;
        CircleImageView circleimag;

        public ViewHolder(View v) {
            super(v);
            username = (TextView) v.findViewById(R.id.chat_item_item_username);
            message = (TextView) v.findViewById(R.id.chat_item_item_message);
            circleimag = (CircleImageView) v.findViewById(R.id.chat_item_item_image);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.username.setText(username.get(position));
        holder.message.setText(message.get(position));
    }

    public int getItemCount() {
        return id.size();
    }
}