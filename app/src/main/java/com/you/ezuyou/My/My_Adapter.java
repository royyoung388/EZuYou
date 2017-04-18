package com.you.ezuyou.My;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.you.ezuyou.Home.Home_Item;
import com.you.ezuyou.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public class My_Adapter extends BaseAdapter {

    private List<Home_Item> Data;
    private Context context;

    public My_Adapter(List<Home_Item> Data, Context context) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.my_item, parent, false);
        ImageView list_image = (ImageView) convertView.findViewById(R.id.my_list_image);
        ImageView item_rent_icon = (ImageView) convertView.findViewById(R.id.my_list_icon_rent);
        ImageView item_sell_icon = (ImageView) convertView.findViewById(R.id.my_list_icon_sell);
        TextView list_name = (TextView) convertView.findViewById(R.id.my_list_name);
        TextView list_rent = (TextView) convertView.findViewById(R.id.my_list_rent);
        TextView list_sell = (TextView) convertView.findViewById(R.id.my_list_sell);
        TextView list_introduce = (TextView) convertView.findViewById(R.id.my_list_introduce);

        list_image.setImageBitmap(Data.get(position).getImage());
        list_name.setText(Data.get(position).getName());
        list_rent.setText(Data.get(position).getRent() + "元/天");
        list_sell.setText(Data.get(position).getSell() + "元");
        list_introduce.setText(Data.get(position).getIntroduce());

        //不出租
        if (Data.get(position).getRent().equals("0")) {
            list_rent.setTextColor(ContextCompat.getColor(list_name.getContext(), R.color.gray));
            item_rent_icon.setImageResource(R.drawable.home_item_rent_no);
        }
        if (Data.get(position).getSell().equals("0")) {
            list_sell.setTextColor(ContextCompat.getColor(list_name.getContext(), R.color.gray));
            item_sell_icon.setImageResource(R.drawable.home_item_sell_no);
        }
        return convertView;
    }
}
