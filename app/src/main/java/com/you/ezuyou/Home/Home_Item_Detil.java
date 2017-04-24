package com.you.ezuyou.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.you.ezuyou.Chat.Chat_Show;
import com.you.ezuyou.InternetUtls.HomeUtils.GetHome_Item;
import com.you.ezuyou.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/4/10.
 */

public class Home_Item_Detil extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView, sell_icon, rent_icon;
    private TextView name, detile, person, rent, sell, school;
    private TextView bt_chat, bt_sell, bt_rent;
    private LinearLayout linearLayout;
    private int tag;

    private Home_Item homeItem;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //指定的item
                case 1:
                    Bundle bundle = msg.getData();
                    String home_item = bundle.getString("home_item");
                    Bitmap[] images = (Bitmap[]) bundle.getSerializable("image");
                    setView(home_item, images);
                    break;
                case 2:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //当从支付界面跳回来时，刷新当前界面
    @Override
    protected void onRestart() {
        super.onRestart();
        Thread getHome_item = new GetHome_Item(handler, tag, this);
        getHome_item.start();
        try {
            getHome_item.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_item_detil);

        //使用toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.home_item_detil_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        bindView();

        Intent intent = getIntent();
        tag = Integer.parseInt(intent.getStringExtra("tag"));

        Thread getHome_item = new GetHome_Item(handler, tag, this);
        getHome_item.start();
        try {
            getHome_item.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //绑定控件
    private void bindView() {
        imageView = (ImageView) findViewById(R.id.home_item_detil_image1);
        rent_icon = (ImageView) findViewById(R.id.home_item_detil_rent_icon);
        sell_icon = (ImageView) findViewById(R.id.home_item_detil_sell_icon);
        name = (TextView) findViewById(R.id.home_item_detil_name);
        person = (TextView) findViewById(R.id.home_item_detil_person);
        detile = (TextView) findViewById(R.id.home_item_detil_detil);
        rent = (TextView) findViewById(R.id.home_item_detil_rent);
        sell = (TextView) findViewById(R.id.home_item_detil_sell);
        school = (TextView) findViewById(R.id.home_item_detil_school);
        bt_chat = (TextView) findViewById(R.id.home_item_detil_bt_chat);
        bt_sell = (TextView) findViewById(R.id.home_item_detil_bt_sell);
        bt_rent = (TextView) findViewById(R.id.home_item_detil_bt_rent);
        linearLayout = (LinearLayout) findViewById(R.id.home_item_detil_linearlayout);

        Drawable drawable_chat = ContextCompat.getDrawable(this, R.drawable.home_item_detil_chat);
        Drawable drawable_sell = ContextCompat.getDrawable(this, R.drawable.home_item_detil_sell);
        Drawable drawable_rent = ContextCompat.getDrawable(this, R.drawable.home_item_detil_rent);

        drawable_chat.setBounds(0, 0, 90, 90);//左上右下，对应的差值是长宽
        drawable_sell.setBounds(0, 0, 90, 90);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        drawable_rent.setBounds(0, 0, 90, 90);//第一0是距左边距离，第二0是距上边距离，40分别是长宽

        bt_chat.setCompoundDrawables(null, drawable_chat, null, null);//左上右下
        bt_sell.setCompoundDrawables(null, drawable_sell, null, null);//左上右下
        bt_rent.setCompoundDrawables(null, drawable_rent, null, null);//左上右下

        bt_chat.setOnClickListener(this);
        bt_sell.setOnClickListener(this);
        bt_rent.setOnClickListener(this);
    }

    //设置View
    private void setView(String home_item, Bitmap[] images) {

        //获取item信息并设置
        homeItem = new Home_Item(images, home_item);

        //设置文字
        name.setText(homeItem.Data.get(0).getName());
        person.setText(homeItem.Data.get(0).getPerson());
        school.setText(homeItem.Data.get(0).getSchool());

        //对0的处理
        if (homeItem.Data.get(0).getRent().equals("0")) {
            bt_rent.setBackgroundResource(R.color.detil);
            bt_rent.setEnabled(false);
            rent_icon.setImageResource(R.drawable.home_item_rent_no);
            rent.setText("0元/天");
            rent.setTextColor(ContextCompat.getColor(this, R.color.grey));
        } else rent.setText(homeItem.Data.get(0).getRent() + "元/天");

        if (homeItem.Data.get(0).getSell().equals("0")) {
            bt_sell.setBackgroundResource(R.color.detil);
            bt_sell.setEnabled(false);
            sell_icon.setImageResource(R.drawable.home_item_sell_no);
            sell.setText("0元");
            sell.setTextColor(ContextCompat.getColor(this, R.color.gray));
        } else sell.setText(homeItem.Data.get(0).getSell() + "元");
        detile.setText(homeItem.Data.get(0).getIntroduce());

        //设置按钮
        //对status的处理
        if (homeItem.Data.get(0).getStatus().equals("0")) {
            bt_rent.setBackgroundColor(ContextCompat.getColor(this, R.color.grey));
            bt_sell.setBackgroundColor(ContextCompat.getColor(this, R.color.grey));
            /*bt_rent.setBackgroundResource(R.drawable.home_item_detil_grey);
            bt_sell.setBackgroundResource(R.drawable.home_item_detil_grey);*/
            bt_rent.setEnabled(false);
            bt_sell.setEnabled(false);
        }

        //设置image
        imageView.setImageBitmap(images[0]);
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageBitmap(images[i]);
            linearLayout.addView(imageView);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //联系按钮
            case R.id.home_item_detil_bt_chat:
                Intent intent = new Intent(Home_Item_Detil.this, Chat_Show.class);
                intent.putExtra("id", homeItem.Data.get(0).getID());
                intent.putExtra("person", homeItem.Data.get(0).getPerson());
                intent.putExtra("message", "");
                intent.putExtra("status", "");
                startActivity(intent);
                break;

            case R.id.home_item_detil_bt_rent:
                Intent intent1 = new Intent(Home_Item_Detil.this, Home_Pay_Rent.class);

                Bundle bundle1 = new Bundle();
                //开启线程对图片进行压缩,然后按照原路径保存
                Bitmap bitmap1 = homeItem.Data.get(0).getImage();

                //将图片转化为字节传输，以免过大的图片导致死机
                ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
                byte[] bytes1 = baos1.toByteArray();
                bundle1.putByteArray("image", bytes1);

                bundle1.putString("tag", homeItem.Data.get(0).getTag());
                bundle1.putString("name", homeItem.Data.get(0).getName());
                bundle1.putString("rent", homeItem.Data.get(0).getRent());
                bundle1.putString("detil", homeItem.Data.get(0).getIntroduce());
                bundle1.putString("person", homeItem.Data.get(0).getPerson());
                bundle1.putString("school", homeItem.Data.get(0).getSchool());
                //b.putString("sex", item.Data.get(0).getsex());
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;

            case R.id.home_item_detil_bt_sell:

                Intent intent2 = new Intent(Home_Item_Detil.this, Home_Pay_Sell.class);

                Bundle bundle2 = new Bundle();
                //开启线程对图片进行压缩,然后按照原路径保存
                Bitmap bitmap2 = homeItem.Data.get(0).getImage();

                //将图片转化为字节传输，以免过大的图片导致死机
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos2);
                byte[] bytes2 = baos2.toByteArray();
                bundle2.putByteArray("image", bytes2);

                bundle2.putString("tag", homeItem.Data.get(0).getTag());
                bundle2.putString("name", homeItem.Data.get(0).getName());
                bundle2.putString("sell", homeItem.Data.get(0).getSell());
                bundle2.putString("detil", homeItem.Data.get(0).getIntroduce());
                bundle2.putString("person", homeItem.Data.get(0).getPerson());
                bundle2.putString("school", homeItem.Data.get(0).getSchool());
                //b.putString("sex", item.Data.get(0).getsex());
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
        }
    }
}
