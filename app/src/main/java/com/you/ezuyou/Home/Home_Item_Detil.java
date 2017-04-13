package com.you.ezuyou.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.you.ezuyou.Chat.Chat_Show;
import com.you.ezuyou.InternetUtls.HomeUtils.GetHome_Item;
import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/4/10.
 */

public class Home_Item_Detil extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView1;
    private TextView name, detile, person, rent, sell;
    private Button bt_chat, bt_sell, bt_rent;
    private LinearLayout linearLayout;

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
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_item_detil);

        //使用toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.home_item_detil_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        bindView();

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);

        Thread getHome_item = new GetHome_Item(handler, position);
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
        name.setText(item.Data.get(0).getName());
        person.setText(item.Data.get(0).getPerson());
        if (item.Data.get(0).getRent().equals("0")) {
            rent.setText("0元/天");
            rent.setTextColor(ContextCompat.getColor(this, R.color.grey));
        } else rent.setText(item.Data.get(0).getRent() + "元/天");

        if (item.Data.get(0).getSell().equals("0")) {
            sell.setText("0元");
            sell.setTextColor(ContextCompat.getColor(this, R.color.gray));
        } else sell.setText(item.Data.get(0).getSell() + "元");

        detile.setText(item.Data.get(0).getIntroduce());

        //设置image
        imageView1.setImageBitmap(images[0]);
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageBitmap(images[i]);
            linearLayout.addView(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //联系按钮
            case R.id.home_item_detil_bt_chat:
                Intent intent = new Intent(Home_Item_Detil.this, Chat_Show.class);
                intent.putExtra("id", item.Data.get(0).getID());
                intent.putExtra("person", item.Data.get(0).getPerson());
                startActivity(intent);
                break;
            case R.id.home_item_detil_bt_rent:
                break;
            case R.id.home_item_detil_bt_sell:
                break;
        }
    }
}
