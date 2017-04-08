package com.you.ezuyou.Release;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.you.ezuyou.InternetUtls.ReleaseUtils;
import com.you.ezuyou.Menu.Menu;
import com.you.ezuyou.R;

import java.util.LinkedList;


/**
 * Created by Administrator on 2017/2/28.
 */

public class Release extends Fragment implements View.OnClickListener {

    /**
     * 需要上传的图片路径  控制默认图片在最后面需要用LinkedList
     */
    private LinkedList<String> dataList = new LinkedList<String>();

    /**
     * 图片上传GridView
     */
    private GridView uploadGridView;

    /**
     * 图片上传Adapter
     */
    private UploadImageAdapter adapter;

    //布局
    private EditText edit_name, edit_sell, edit_rent, edit_detil;
    private TextView publish;

    private String str_name, str_sell, str_rent, str_detil;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.release, container, false);

        //这些和上传图片有关
        uploadGridView = (GridView) view.findViewById(R.id.Release_grid_upload_pictures);
        dataList.addLast(null);// 初始化第一个添加按钮数据
        adapter = new UploadImageAdapter(getActivity(), dataList);
        uploadGridView.setAdapter(adapter);
        uploadGridView.setOnItemClickListener(mItemClick);
        uploadGridView.setOnItemLongClickListener(mItemLongClick);
        //设置监听
        ((Menu) getActivity()).setOnFragmentResult(mOnFragmentResult);

        //UI
        //输入框
        edit_name = (EditText) view.findViewById(R.id.Release_input_name);
        edit_sell = (EditText) view.findViewById(R.id.Release_input_sell);
        edit_rent = (EditText) view.findViewById(R.id.Release_input_rent);
        edit_detil = (EditText) view.findViewById(R.id.Release_input_detail);
        //发布
        publish = (TextView) view.findViewById(R.id.Release_publish);
        publish.setOnClickListener(this);

        //使用Toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.Release_toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Release_publish:

                str_detil = edit_detil.getText().toString();
                str_name = edit_name.getText().toString();

                if ((str_sell = edit_sell.getText().toString()) == null) str_sell = "0";
                if ((str_rent = edit_rent.getText().toString()) == null) str_rent = "0";

                if (edit_name == null)
                    Toast.makeText(getActivity(), "物品名称不能为空", Toast.LENGTH_SHORT).show();
                else if (str_detil == null)
                    Toast.makeText(getActivity(), "详细信息不能为空", Toast.LENGTH_SHORT).show();
                else if (str_sell.equals("0") && str_rent.equals("0"))
                    Toast.makeText(getActivity(), "详细信息不能为空", Toast.LENGTH_SHORT).show();
                else ReleaseUtils.start_Release(dataList, str_name, str_sell, str_rent, str_detil);
                break;
        }
    }

    /**
     * 上传图片GridView Item单击监听
     */
    private AdapterView.OnItemClickListener mItemClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if (parent.getItemAtPosition(position) == null) { // 添加图片
                ((Menu) getActivity()).showPictureDailog();//Dialog形式
                //((BaseActivity)getActivity()).showPicturePopupWindow();//PopupWindow形式
            }
        }
    };

    /**
     * 上传图片GridView Item长按监听
     */
    private AdapterView.OnItemLongClickListener mItemLongClick = new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            if (parent.getItemAtPosition(position) != null) { // 长按删除
                dataList.remove(parent.getItemAtPosition(position));
                adapter.update(dataList); // 刷新图片
            }
            return true;
        }
    };

    //单击之后的回传的图片路劲
    private Menu.OnFragmentResult mOnFragmentResult = new Menu.OnFragmentResult() {

        @Override
        public void onResult(String mImagePath) {
            dataList.addFirst(mImagePath);
            adapter.update(dataList); // 刷新图片
            //System.out.println(mImagePath);
        }
    };
}
