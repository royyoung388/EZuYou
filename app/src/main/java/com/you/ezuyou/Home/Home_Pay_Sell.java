package com.you.ezuyou.Home;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class Home_Pay_Sell extends AppCompatActivity implements View.OnClickListener{

    private TextView sure, name, sell, detil, pay, person, school;
    private ImageView imageView;
    private String str_tag, str_name, str_sell, str_detil, str_person, str_school;
    private Bitmap image;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_item_pay_sell);

        Bundle bundle = getIntent().getExtras();

        //获取图片
        byte [] bis = bundle.getByteArray("image");
        image = BitmapFactory.decodeByteArray(bis, 0, bis.length);

        str_tag = bundle.getString("str_tag");
        str_name = bundle.getString("naem");
        str_sell = bundle.getString("str_sell");
        str_detil = bundle.getString("str_detil");
        str_person = bundle.getString("str_person");
        str_school = bundle.getString("str_school");
        
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
        sell = (TextView) findViewById(R.id.home_item_pay_sell);
        detil = (TextView) findViewById(R.id.home_item_pay_detil);
        pay = (TextView) findViewById(R.id.home_item_pay_pay);
        person = (TextView) findViewById(R.id.home_item_pay_person);
        school = (TextView)findViewById(R.id.home_item_pay_school);
        imageView = (ImageView) findViewById(R.id.home_item_pay_image);

        sure.setOnClickListener(this);

        name.setText(str_name);
        sell.setText(str_sell);
        detil.setText(str_detil);
        pay.setText(str_sell);
        person.setText(str_person);
        school.setText(str_school);
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

        Thread pay = new Hoem_Pay_Change_Status(sp.getString("id", null), str_tag);
        pay.start();
        try {
            pay.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(Home_Pay_Sell.this, "支付成功", Toast.LENGTH_SHORT).show();
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
