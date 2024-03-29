package com.you.ezuyou.Chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.you.ezuyou.InternetUtls.ChatUtils.Chat_From_Other;
import com.you.ezuyou.InternetUtls.ChatUtils.Start_Chat;
import com.you.ezuyou.InternetUtls.LoginUtils.Start_Login;
import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/4/12.
 */

public class Chat_Show extends AppCompatActivity implements View.OnClickListener {

    private String person, id, userid, message;
    private TextView title;
    private Button send;
    private EditText edit;
    private LinearLayout linearLayout = null;
    private ScrollView scrollView;

    private final int MY_MESSAGE = 1;
    private final int OTHER_MESSAGE = 0;

    private Chat_Item_Adapter chat_item_adapter;

    //private FlushChatShow flushChatShow;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                /*//用户输入后，清空输入框
                case 2:
                    edit.setText(null);
                    break;*/
                //接收到的
                case 1:
                    String message = (String) msg.obj;
                    if (linearLayout != null) AddText(message, OTHER_MESSAGE);
                    break;
            }
        }
    };

    /*//设置接口，用于接收消息时更新消息
    public interface FlushChatShow {
        void flushChatShow(String message);
    }
    //暴露给外面的设置接口方法
    public void setFlushChatShow(FlushChatShow flushChatShow) {
        this.flushChatShow = flushChatShow;
    }*/

    public Chat_Show() {
        Chat_From_Other.getInstance(handler).setHandler(handler);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_show);

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        //用户id
        userid = sp.getString("id", null);

        //使用Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        BindView();

        Chat_From_Other.getInstance(handler).setAddMessage(new Chat_From_Other.AddMessage() {
            @Override
            public void addMeg(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AddText(message, OTHER_MESSAGE);
                    }
                });
            }
        });

        Intent intent = getIntent();

        //对方姓名
        person = intent.getStringExtra("person");
        //对方id
        id = intent.getStringExtra("id");
        //从cha_item启动时，会传入一个历史消息
        String message = intent.getStringExtra("message");
        int status = 0;
        if (intent.getStringExtra("status") != null && !intent.getStringExtra("status").equals("")) {
            status = Integer.parseInt(intent.getStringExtra("status"));
        }

        if (!message.equals("") && intent.getStringExtra("message") != null)
            AddText(intent.getStringExtra("message"), status);

        title.setText(person);
    }

    //setNavigationlcon的点击监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //点击发送，处理聊天过程
    private void Chat_Connection() {
        message = edit.getText().toString();
        if (!(message.equals("") && message != null)) {
            chat_item_adapter.getInstance().addItem(id, person, message, MY_MESSAGE);
            //生成对话框
            //自己的，在右边
            AddText(message, 1);
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
        } else {
            Toast.makeText(this, "发送消息不能为空", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 监听Back键按下事件,方法1:
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("按下了back键   onBackPressed()");
    }


    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }*/

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
    //0：左边,对方
    //1：右边，自己
    private void AddText(String text, int position) {
        if (!text.equals("") && text != null) {
            TextView textView = new TextView(linearLayout.getContext());
            textView.setText(text);
            textView.setTextColor(ContextCompat.getColor(this, R.color.white));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            //textView.setPadding(dpTopx(8), dpTopx(8), dpTopx(8), dpTopx(8));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(dpTopx(20), dpTopx(20), dpTopx(20), dpTopx(20));
            if (position == OTHER_MESSAGE) {
                textView.setBackgroundResource(R.drawable.chat_bg_other);
                textView.setGravity(Gravity.LEFT);
                lp.gravity = Gravity.LEFT;
            } else if (position == MY_MESSAGE) {
                textView.setBackgroundResource(R.drawable.chat_bg_self);
                textView.setGravity(Gravity.RIGHT);
                lp.gravity = Gravity.RIGHT;
            }

            textView.setLayoutParams(lp);
            linearLayout.addView(textView);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
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
