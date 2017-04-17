package com.you.ezuyou.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
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
import com.you.ezuyou.utils.CompressBitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/4/10.
 */

public class Home_Item_Detil extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView1;
    private TextView name, detile, person, rent, sell, school;
    private Button bt_chat, bt_sell, bt_rent;
    private LinearLayout linearLayout;
    private int tag;
    private Bitmap bitmap;

    private Item item;

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
        tag = intent.getIntExtra("tag", -1);

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
        imageView1 = (ImageView) findViewById(R.id.home_item_detil_image1);
        name = (TextView) findViewById(R.id.home_item_detil_name);
        person = (TextView) findViewById(R.id.home_item_detil_person);
        detile = (TextView) findViewById(R.id.home_item_detil_detil);
        rent = (TextView) findViewById(R.id.home_item_detil_rent);
        sell = (TextView) findViewById(R.id.home_item_detil_sell);
        school = (TextView) findViewById(R.id.home_item_detil_school);
        bt_chat = (Button) findViewById(R.id.home_item_detil_bt_chat);
        bt_sell = (Button) findViewById(R.id.home_item_detil_bt_sell);
        bt_rent = (Button) findViewById(R.id.home_item_detil_bt_rent);
        linearLayout = (LinearLayout) findViewById(R.id.home_item_detil_linearlayout);

        bt_chat.setOnClickListener(this);
        bt_sell.setOnClickListener(this);
        bt_rent.setOnClickListener(this);
    }

    //设置View
    private void setView(String home_item, Bitmap[] images) {

        //获取item信息并设置
        item = new Item(images, home_item);

        //设置文字
        name.setText(item.Data.get(0).getName());
        person.setText(item.Data.get(0).getPerson());
        school.setText(item.Data.get(0).getSchool());

        if (item.Data.get(0).getRent().equals("0")) {
            rent.setText("0元/天");
            rent.setTextColor(ContextCompat.getColor(this, R.color.grey));
        } else rent.setText(item.Data.get(0).getRent() + "元/天");

        if (item.Data.get(0).getSell().equals("0")) {
            sell.setText("0元");
            sell.setTextColor(ContextCompat.getColor(this, R.color.gray));
        } else sell.setText(item.Data.get(0).getSell() + "元");
        detile.setText(item.Data.get(0).getIntroduce());

        //设置按钮
        if (item.Data.get(0).getStatus().equals("0")) {
            bt_rent.setBackgroundResource(R.color.gray);
            bt_sell.setBackgroundResource(R.color.gray);
            bt_rent.setEnabled(false);
            bt_sell.setEnabled(false);
        }

        //设置image
        imageView1.setImageBitmap(images[0]);
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
                intent.putExtra("id", item.Data.get(0).getID());
                intent.putExtra("person", item.Data.get(0).getPerson());
                intent.putExtra("message", "");
                startActivity(intent);
                break;
            case R.id.home_item_detil_bt_rent:
                Intent intent1 = new Intent(Home_Item_Detil.this, Home_Pay_Sell.class);
                intent1.putExtra("tag", item.Data.get(0).getTag());
                startActivity(intent1);
                break;
            case R.id.home_item_detil_bt_sell:

                Intent intent2 = new Intent(Home_Item_Detil.this, Home_Pay_Sell.class);

                Bundle b = new Bundle();
                //开启线程对图片进行压缩,然后按照原路径保存
                bitmap = item.Data.get(0).getImage();
                /*Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        bitmap = CompressBitmap.compressImage(bitmap);
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                //将图片转化为字节传输，以免过大的图片导致死机
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bytes = baos.toByteArray();
                b.putByteArray("image", bytes);

                b.putString("tag", item.Data.get(0).getTag());
                b.putString("name", item.Data.get(0).getName());
                b.putString("sell", item.Data.get(0).getSell());
                b.putString("detil", item.Data.get(0).getIntroduce());
                b.putString("person", item.Data.get(0).getPerson());
                b.putString("school", item.Data.get(0).getSchool());
                intent2.putExtras(b);
                startActivity(intent2);
                break;
        }
    }
}
