package com.you.ezuyou.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.you.ezuyou.InternetUtls.HomeUtils.Hoem_Pay_Change_Status;
import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/4/15.
 */

public class Home_Pay extends AppCompatActivity implements View.OnClickListener{

    private TextView sure, name, money;
    private ImageView image;
    private String tag;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_item_pay);

        //获取标识符
        tag = getIntent().getStringExtra("tag");

        //使用toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.home_item_pay_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        bindView();
    }

    //绑定控件
    private void bindView() {
        sure = (TextView) findViewById(R.id.home_item_pay_sure);
        name = (TextView) findViewById(R.id.home_item_pay_name);
        money = (TextView) findViewById(R.id.home_item_pay_money);

        sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_item_pay_sure:
                pay();
                break;
        }
    }

    //支付过程
    public void pay() {

        sp = getSharedPreferences("login", MODE_PRIVATE);

        Thread pay = new Hoem_Pay_Change_Status(sp.getString("id", null), tag);
        pay.start();
        try {
            pay.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(Home_Pay.this, "支付成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    //setNavigationlcon的点击监听（左上角第一个）。
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
