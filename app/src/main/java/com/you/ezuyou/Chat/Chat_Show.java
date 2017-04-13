package com.you.ezuyou.Chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.you.ezuyou.InternetUtls.ChatUtils.Start_Chat;
import com.you.ezuyou.InternetUtls.LoginUtils.Start_Login;
import com.you.ezuyou.Login.Login;
import com.you.ezuyou.Menu.Menu;
import com.you.ezuyou.R;
import com.you.ezuyou.keyword.KeyWord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


/**
 * Created by Administrator on 2017/4/12.
 */

public class Chat_Show extends AppCompatActivity implements View.OnClickListener {

    private String person, id, userid, message;
    private TextView title;
    private Button send;
    private EditText edit;
    private LinearLayout linearLayout;
    private ScrollView scrollView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //用户输入后，清空输入框
                case 1:
                    edit.setText(null);
                    break;
                //接收到的
                case 2:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_show);

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        userid = sp.getString("id", null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        BindView();

        Intent intent = getIntent();
        person = intent.getStringExtra("person");
        id = intent.getStringExtra("id");

        title.setText(person);
    }

    //处理聊天过程
    private void Chat_Connection() {
        message = edit.getText().toString();
        //生成对话框
        AddText(message);
        //清除输入框
        edit.setText("");

        //开启聊天线程
        Thread chat = new Start_Chat("聊天", id, userid, Start_Login.username, message);
        chat.start();
        try {
            chat.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    //绑定控件
    private void BindView() {
        linearLayout = (LinearLayout) findViewById(R.id.chat_show);
        edit = (EditText) findViewById(R.id.chat_input);
        send = (Button) findViewById(R.id.chat_send);
        title = (TextView) findViewById(R.id.chat_toolbar_name);
        scrollView = (ScrollView) findViewById(R.id.chat_show_scroll);

        send.setOnClickListener(this);
    }

    //添加textview,即对话框
    private void AddText(String text) {
        TextView textView = new TextView(linearLayout.getContext());
        textView.setText(text);
        textView.setBackgroundColor(Color.parseColor("#b9b9b9"));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(dpTopx(8), dpTopx(8), dpTopx(8), dpTopx(8));
        textView.setLayoutParams(lp);
        linearLayout.addView(textView);
        handler.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    //将dp单位换算为px
    private int dpTopx(int dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.getResources().getDisplayMetrics());
        return px;
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_send:
                //开始处理发送
                Chat_Connection();
        }
    }
}
