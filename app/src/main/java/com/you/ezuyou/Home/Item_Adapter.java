package com.you.ezuyou.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.you.ezuyou.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public class Item_Adapter extends android.widget.BaseAdapter{

    private List<Item> Data;
    private Context context;

    public Item_Adapter(List<Item> Data, Context context) {
        this.Data = Data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.home_list_item, parent, false);
        ImageView list_image = (ImageView) convertView.findViewById(R.id.list_image);
        TextView list_name = (TextView) convertView.findViewById(R.id.list_name);
        TextView list_rent = (TextView) convertView.findViewById(R.id.list_rent);
        TextView list_sell = (TextView) convertView.findViewById(R.id.list_sell);
        TextView list_introduce = (TextView) convertView.findViewById(R.id.list_introduce);

        list_image.setImageResource(Data.get(position).getImage());
        list_name.setText(Data.get(position).getName());
        list_rent.setText(Data.get(position).getRent());
        list_sell.setText(Data.get(position).getSell());
        list_introduce.setText(Data.get(position).getIntroduce());
        return convertView;
    }
}
