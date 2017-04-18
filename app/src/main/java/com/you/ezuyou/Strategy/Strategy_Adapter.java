package com.you.ezuyou.Strategy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.you.ezuyou.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class Strategy_Adapter extends BaseAdapter {
    private List<Strategy_Item> Data;
    private Context context;

    public Strategy_Adapter(List<Strategy_Item> Data, Context context) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.strategy_item_item, parent, false);
        ImageView list_image = (ImageView) convertView.findViewById(R.id.strategy_item_image);
        TextView list_program = (TextView) convertView.findViewById(R.id.strategy_item_program);
        TextView list_money = (TextView) convertView.findViewById(R.id.strategy_item_money);
        TextView list_detil = (TextView) convertView.findViewById(R.id.strategy_item_detil);

        list_image.setImageBitmap(Data.get(position).getImage());
        list_program.setText(Data.get(position).getProgram());
        list_money.setText(Data.get(position).getMoney());
        list_detil.setText(Data.get(position).getDetil());
        return convertView;
    }
}
