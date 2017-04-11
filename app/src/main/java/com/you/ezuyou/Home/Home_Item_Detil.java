package com.you.ezuyou.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.you.ezuyou.InternetUtls.HomeUtils.GetHome_Item;
import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/4/10.
 */

public class Home_Item_Detil extends AppCompatActivity{

    private ImageView imageView1;
    private TextView name, detile;
    private LinearLayout linearLayout;
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
        detile = (TextView) findViewById(R.id.home_item_detil_detil);
        linearLayout = (LinearLayout) findViewById(R.id.home_item_detil_linearlayout);
    }

    //设置View
    private void setView(String home_item, Bitmap[] images) {
        //获取item信息并设置
        Item item = new Item(images, home_item);
        name.setText(item.Data.get(0).getName());
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
}
