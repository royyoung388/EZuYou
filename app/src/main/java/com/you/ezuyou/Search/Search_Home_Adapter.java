package com.you.ezuyou.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.you.ezuyou.Home.Home_Item;
import com.you.ezuyou.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class Search_Home_Adapter extends BaseAdapter implements Filterable{

    private MyFilter myFilter;
    private List<Home_Item> Data;
    private Context context;

    private ArrayList<Home_Item> mOriginalValues;

    private final Object mLock = new Object();

    //接口变量
    private JumpHomeActivity jumpHomeActivity;

    //用于回调，跳转activity的接口
    public interface JumpHomeActivity {
        void jumpHome(String tag);
    }

    //暴露给外面的设置接口方法
    public void setJumpActivity(JumpHomeActivity jumpHomeActivity) {
        this.jumpHomeActivity = jumpHomeActivity;
    }

    public Search_Home_Adapter(Context context, List<Home_Item> Data) {
        this.Data = Data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem(int position) {
        return Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.search_item_name);
            holder.detil = (TextView) convertView.findViewById(R.id.search_item_detil);
            holder.image = (ImageView) convertView.findViewById(R.id.search_item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(Data.get(position).getName());
        holder.detil.setText(Data.get(position).getIntroduce());
        holder.image.setImageBitmap(Data.get(position).getImage());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               jumpHomeActivity.jumpHome(Data.get(position).getTag());
            }
        });

        return convertView;
    }

    /*@Override
    public void onClick(View v) {
        System.out.println(Data.get());
    }*/

    public class ViewHolder {
        TextView name;
        TextView detil;
        ImageView image;
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            // 持有过滤操作完成之后的数据。该数据包括过滤操作之后的数据的值以及数量。 count:数量 values包含过滤操作之后的数据的值
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    // 将list的用户 集合转换给这个原始数据的ArrayList
                    mOriginalValues = new ArrayList<>(Data);
                }
            }
            if (prefix == null || prefix.length() == 0) {
                synchronized (mLock) {
                    ArrayList<Home_Item> list = new ArrayList<>(mOriginalValues);
                    results.values = list;
                    results.count = list.size();
                }
            } else {
                // 做正式的筛选
                //转化为小写
                String prefixString = prefix.toString().toLowerCase();

                // 声明一个临时的集合对象 将原始数据赋给这个临时变量
                ArrayList<Home_Item> values;
                synchronized (mLock) {//同步复制一个原始备份数据
                    values = mOriginalValues;
                }

                final int count = values.size();

                // 新的集合对象
                final ArrayList<Home_Item> newValues = new ArrayList<>(count);

                for (int i = 0; i < count; i++) {
                    // 如果姓名的前缀相符或者电话相符就添加到新的集合
                    //从List<>中拿到Home_Item对象
                    final Home_Item value = values.get(i);

                    final String valueText = value.getName().toString().toLowerCase();//对象的name属性作为过滤的参数

                    //以搜索內容开头或者包含搜索内容
                    if (valueText.startsWith(prefixString) || valueText.indexOf(prefixString.toString()) != -1) {
                        newValues.add(value);
                    } else {
                        //处理首字符是空格
                        final String[] words = valueText.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {//一旦找到匹配的就break，跳出for循环
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }
                // 然后将这个新的集合数据赋给FilterResults对象
                //此时的results就是过滤后的List<User>数组
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        //刷选结果
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            // 重新将与适配器相关联的List重赋值一下
            //此时，Adapter数据源就是过滤后的Results
            Data = (List<Home_Item>) results.values;

            if (results.count > 0) {
                //这个相当于从mDatas中删除了一些数据，只是数据的变化，故使用notifyDataSetChanged()
                notifyDataSetChanged();
            } else {
                /**
                 * 数据容器变化 ----> notifyDataSetInValidated
                 * 容器中的数据变化  ---->  notifyDataSetChanged
                 */
                notifyDataSetInvalidated();
            }
        }
    }
}

