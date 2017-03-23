package com.you.ezuyou.Home;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.you.ezuyou.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/2/28.
 */

public class Home extends Fragment {

    private List<Item> Data;
    private int[] image = new int[] {R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image};
    private String[] name = new String[] {"标准二人帐篷1", "标准二人帐篷2", "标准二人帐篷3", "标准二人帐篷4", "标准二人帐篷5"};
    private String[] rent = new String[] {"121", "122", "123", "124", "125"};
    private String[] sell = new String[] {"61", "62", "63", "64", "65"};
    private String[] introduce = new String[] {"这是一个双人帐篷1", "这是一个双人帐篷2", "这是一个双人帐篷3",
            "这是一个双人帐篷4", "这是一个双人帐篷5"};

    public Home() {
        Data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Item item = new Item(image[i], name[i], rent[i], sell[i], introduce[i]);
            Data.add(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        //view.setBackgroundColor(getResources().getColor(R.color.gray));
        ListView listView = (ListView) view.findViewById(R.id.home_listview);

        Item_Adapter adapter = new Item_Adapter(Data, view.getContext());
        listView.setAdapter(adapter);
        return view;
    }
}
