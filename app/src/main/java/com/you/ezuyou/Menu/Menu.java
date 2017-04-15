package com.you.ezuyou.Menu;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.you.ezuyou.Chat.Chat_Item;
import com.you.ezuyou.Strategy.Strategy;
import com.you.ezuyou.Home.Home;
import com.you.ezuyou.My.My;
import com.you.ezuyou.Release.Release;
import com.you.ezuyou.R;
import com.you.ezuyou.utils.ImageUtils;


public class Menu extends BaseActivity implements View.OnClickListener, Release.Flush_Home {

    //UI Objects
    private TextView menu1, menu2, menu3, menu4, menu5;
    private Button menu3_bt;

    //Fragment Object
    private Home fg1;
    private Strategy fg2;
    private Chat_Item fg4;
    private Release fg3;
    private My fg5;

    public String username;

    //private FragmentTransaction fTransaction;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        fManager = getFragmentManager();

        //用于启动聊天线程
        new Chat_Item().sartChat();

        bindViews();
        //模拟一次点击，既进去后选择第一项
        menu1.performClick();

        //加载聊天界面，以启动线程
        /*System.out.println("加载聊天界面，以启动线程");
        fTransaction = fManager.beginTransaction();
        fg1 = new Home();
        fTransaction.add(R.id.frame,fg1);
        hideAllFragment(fTransaction);*/

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
    private void setSelected() {
        menu1.setSelected(false);
        menu2.setSelected(false);
//        menu3.setSelected(false);
        menu3_bt.setSelected(false);
        menu4.setSelected(false);
        menu5.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) fragmentTransaction.hide(fg1);
        if (fg2 != null) fragmentTransaction.hide(fg2);
        if (fg3 != null) fragmentTransaction.hide(fg3);
        if (fg4 != null) fragmentTransaction.hide(fg4);
        if (fg5 != null) fragmentTransaction.hide(fg5);
    }

    /******
     * 监听返回键点击事件，并创建一个退出对话框，
     *防止自己写的应用程序不小心点击退出键而直接退出。
     ****/
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出吗");
            // 添加选择按钮并注册监听
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            // 显示对话框
            isExit.show();

        }

        return false;

    }*/
    /**
     * 监听对话框里面的button点击事件
     */
    /*DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };*/

    //双击退出
    private long mPressedTime = 0;

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            this.finish();
            System.exit(0);
        }
    }

    //点击事件
    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);

        switch (v.getId()) {
            case R.id.menu1:
                setSelected();
                menu1.setSelected(true);
                if (fg1 == null) {
                    fg1 = new Home();
                    fTransaction.add(R.id.frame, fg1);
                } else {
                    fTransaction.show(fg1);
                }
                break;
            case R.id.menu2:
                setSelected();
                menu2.setSelected(true);
                if (fg2 == null) {
                    fg2 = new Strategy();
                    fTransaction.add(R.id.frame, fg2);
                } else {
                    fTransaction.show(fg2);
                }
                break;
            case R.id.menu3_bt:
                setSelected();
                //menu3.setSelected(true);
                //menu3_bt.setSelected(true);
                if (fg3 == null) {
                    fg3 = new Release();
                    fTransaction.add(R.id.frame, fg3);
                } else {
                    fTransaction.show(fg3);
                }
                break;
            case R.id.menu4:
                setSelected();
                menu4.setSelected(true);
                if (fg4 == null) {
                    fg4 = new Chat_Item();
                    fTransaction.add(R.id.frame, fg4);
                } else {
                    fTransaction.show(fg4);
                }
                break;
            case R.id.menu5:
                setSelected();
                menu5.setSelected(true);
                if (fg5 == null) {
                    fg5 = new My();
                    fTransaction.add(R.id.frame, fg5);
                } else {
                    fTransaction.show(fg5);
                }
                break;
        }
        fTransaction.commit();
    }

    //与选择图片上传有关
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String imagePath = "";
        if (requestCode == SELECT_IMAGE_RESULT_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                //有数据返回直接使用返回的图片地址
                imagePath = ImageUtils.getFilePathByFileUri(this, data.getData());
            } else {
                //无数据使用指定的图片路径
                imagePath = mImagePath;
            }
            mOnFragmentResult.onResult(imagePath);
        }
    }

    //刷新home的接口实现
    @Override
    public void Flush_Item() {
        fg1.Flush();
    }
}