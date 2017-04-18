package com.you.ezuyou.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.you.ezuyou.R;
import com.you.ezuyou.utils.CircleImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/2/1.
 */

public class Chat_Item_Adapter extends RecyclerView.Adapter<Chat_Item_Adapter.ViewHolder> implements View.OnClickListener {

    private List<String> id, username, message, status;

    //使用单例模式确保实例只有一个
    // 静态实例变量加上volatile
    private static volatile Chat_Item_Adapter chatItemAdapter;

    private OnRecyclerviewClick itemclick = null;

    // 双重检查锁
    public static Chat_Item_Adapter getInstance() {
        if (chatItemAdapter == null) {
            synchronized (Chat_Item_Adapter.class) {
                if (chatItemAdapter == null) {
                    chatItemAdapter = new Chat_Item_Adapter();
                }
            }
        }
        return chatItemAdapter;
    }

    // 私有化构造函数
    private Chat_Item_Adapter() {
        id = new ArrayList<>();
        username = new ArrayList<>();
        message = new ArrayList<>();
        status = new ArrayList<>();
    }

    //添加
    public void addItem(String id, String username, String message, int status) {

        //发送方id
        int index = this.id.indexOf(id);
        if (index != -1) {
            //删除相同的
            this.id.remove(index);
            this.username.remove(index);
            this.message.remove(index);
            this.status.remove(index);
        }
        //插入到第一个
        this.id.add(0, id);
        this.username.add(0, username);
        this.message.add(0, message);
        this.status.add(0, "" + status);
        notifyDataSetChanged();
    }

    //继承点击事件
    @Override
    public void onClick(View v) {
        if (itemclick != null) {
            itemclick.OnItemClick(v, (String[]) v.getTag());
        }
    }

    //自定义一个点击的接口
    public interface OnRecyclerviewClick {
        void OnItemClick(View view, String[] strings);
    }

    //暴露给外面，设置接口变量的方法
    public void setOnRecyclerviewClick(OnRecyclerviewClick listener) {
        this.itemclick = listener;
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
        //点击监听
        view.setOnClickListener(this);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.username.setText(username.get(position));
        holder.message.setText(message.get(position));
        //使用serTag传数据
        holder.itemView.setTag(new String[]{id.get(position), username.get(position), message.get(position), status.get(position)});
        System.out.println(status.get(position));
    }

    public int getItemCount() {
        return id.size();
    }
}
