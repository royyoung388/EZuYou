package com.you.ezuyou.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.you.ezuyou.R;


/**
 * Created by Administrator on 2017/2/28.
 */

public class MyFragment3 extends Fragment {

    public MyFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        TextView txt_content = (TextView) view.findViewById(R.id.text3);
        txt_content.setText("第三个Fragment");
        return view;
    }
}
