package com.you.ezuyou.Menu;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.you.ezuyou.Fragment.MyFragment2;
import com.you.ezuyou.Fragment.MyFragment4;
import com.you.ezuyou.Home.Home;
import com.you.ezuyou.My.My;
import com.you.ezuyou.Release.Release;
import com.you.ezuyou.R;
import com.you.ezuyou.utils.ImageUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Menu extends BaseActivity implements View.OnClickListener {

    //UI Objects
    private TextView menu1, menu2, menu3, menu4, menu5;
    private Button menu3_bt;

    //Fragment Object
    private Home fg1;
    private MyFragment2 fg2;
    private Release fg3;
    private MyFragment4 fg4;
    private My fg5;

    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        fManager = getFragmentManager();
        bindViews();
        menu1.performClick();   //模拟一次点击，既进去后选择第一项
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        menu1 = (TextView) findViewById(R.id.menu1);
        menu2 = (TextView) findViewById(R.id.menu2);
//        menu3 = (TextView) findViewById(R.id.menu3);
        menu3_bt = (Button) findViewById(R.id.menu3_bt);
        menu4 = (TextView) findViewById(R.id.menu4);
        menu5 = (TextView) findViewById(R.id.menu5);

        menu1.setOnClickListener(this);
        menu2.setOnClickListener(this);
//        menu3.setOnClickListener(this);
        menu3_bt.setOnClickListener(this);
        menu4.setOnClickListener(this);
        menu5.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        menu1.setSelected(false);
        menu2.setSelected(false);
//        menu3.setSelected(false);
        menu3_bt.setSelected(false);
        menu4.setSelected(false);
        menu5.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
        if(fg5 != null)fragmentTransaction.hide(fg5);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.menu1:
                setSelected();
                menu1.setSelected(true);
                if(fg1 == null){
                    fg1 = new Home();
                    fTransaction.add(R.id.frame,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.menu2:
                setSelected();
                menu2.setSelected(true);
                if(fg2 == null){
                    fg2 = new MyFragment2();
                    fTransaction.add(R.id.frame,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;
            case R.id.menu3_bt:
                setSelected();
                //menu3.setSelected(true);
                //menu3_bt.setSelected(true);
                if(fg3 == null){
                    fg3 = new Release();
                    fTransaction.add(R.id.frame,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
            case R.id.menu4:
                setSelected();
                menu4.setSelected(true);
                if(fg4 == null){
                    fg4 = new MyFragment4();
                    fTransaction.add(R.id.frame,fg4);
                }else{
                    fTransaction.show(fg4);
                }
                break;
            case R.id.menu5:
                setSelected();
                menu5.setSelected(true);
                if(fg5 == null){
                    fg5 = new My();
                    fTransaction.add(R.id.frame,fg5);
                }else{
                    fTransaction.show(fg5);
                }
                break;
        }
        fTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String imagePath = "";
        if(requestCode == SELECT_IMAGE_RESULT_CODE && resultCode== RESULT_OK){
            if(data != null && data.getData() != null){//有数据返回直接使用返回的图片地址
                imagePath = ImageUtils.getFilePathByFileUri(this, data.getData());
            }else{//无数据使用指定的图片路径
                imagePath = mImagePath;
            }
            mOnFragmentResult.onResult(imagePath);
        }
    }
}