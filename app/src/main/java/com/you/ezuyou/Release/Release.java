package com.you.ezuyou.Release;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.you.ezuyou.Menu.Menu;
import com.you.ezuyou.R;

import java.util.LinkedList;


/**
 * Created by Administrator on 2017/2/28.
 */

public class Release extends Fragment {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.release, container, false);
        uploadGridView = (GridView) view.findViewById(R.id.grid_upload_pictures);
        dataList.addLast(null);// 初始化第一个添加按钮数据
        adapter = new UploadImageAdapter(getActivity(), dataList);
        uploadGridView.setAdapter(adapter);
        uploadGridView.setOnItemClickListener(mItemClick);
        uploadGridView.setOnItemLongClickListener(mItemLongClick);
        //设置监听
        ((Menu)getActivity()).setOnFragmentResult(mOnFragmentResult);
        return view;
    }

    /**
     * 上传图片GridView Item单击监听
     */
    private AdapterView.OnItemClickListener mItemClick = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if(parent.getItemAtPosition(position) == null){ // 添加图片
                ((Menu)getActivity()).showPictureDailog();//Dialog形式
                //((BaseActivity)getActivity()).showPicturePopupWindow();//PopupWindow形式
            }
        }
    };

    /**
     * 上传图片GridView Item长按监听
     */
    private AdapterView.OnItemLongClickListener mItemLongClick = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            if(parent.getItemAtPosition(position) != null){ // 长按删除
                dataList.remove(parent.getItemAtPosition(position));
                adapter.update(dataList); // 刷新图片
            }
            return true;
        }
    };

    private Menu.OnFragmentResult mOnFragmentResult = new Menu.OnFragmentResult() {

        @Override
        public void onResult(String mImagePath) {
            dataList.addFirst(mImagePath);
            adapter.update(dataList); // 刷新图片
            System.out.println(mImagePath);
        }
    };
}
