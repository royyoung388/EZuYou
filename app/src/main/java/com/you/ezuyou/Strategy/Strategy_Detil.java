package com.you.ezuyou.Strategy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.you.ezuyou.Home.Item;
import com.you.ezuyou.InternetUtls.StrategyUtils.GetStrategy_Item;
import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/4/16.
 */

public class Strategy_Detil extends AppCompatActivity {

    private TextView title, editor, text, money;
    private ImageView image;
    private int tag;
    private Strategy_Item strategy_Item;

    private SharedPreferences sp;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    String item = bundle.getString("strategy_item");
                    Bitmap[] images = (Bitmap[]) bundle.getSerializable("image");
                    setView(item, images);
                    break;
                case 2:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.strategy_detil);

        //使用toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.strategy_detil_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        bindView();

        Intent intent = getIntent();
        tag = intent.getIntExtra("tag", -1);

        sp = this.getSharedPreferences("login", MODE_PRIVATE);
        Thread getStrategy = new GetStrategy_Item(handler, sp.getString("id", null), tag);
        getStrategy.start();
        try {
            getStrategy.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //绑定控件
    private void bindView() {
        title = (TextView) findViewById(R.id.strategy_detil_title);
        editor = (TextView) findViewById(R.id.strategy_detil_editor);
        text = (TextView) findViewById(R.id.strategy_detil_text);
        image = (ImageView) findViewById(R.id.strategy_detil_image);
        money = (TextView) findViewById(R.id.strategy_detil_money);
    }

    //设置View
    private void setView(String item, Bitmap[] images) {
        //获取item信息并设置
        strategy_Item = new Strategy_Item(images, item);

        //设置文字
        title.setText(strategy_Item.Data.get(0).getProgram());
        text.setText(strategy_Item.Data.get(0).getDetil());
        money.setText(strategy_Item.Data.get(0).getMoney());

        if (strategy_Item.Data.get(0).getPerson() == null || strategy_Item.Data.get(0).getPerson().equals("")) {
            editor.setText("作者: 匿名");
        } else editor.setText("作者: " + strategy_Item.Data.get(0).getPerson());

        //设置image
        image.setImageBitmap(images[0]);

        /*for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageBitmap(images[i]);
            linearLayout.addView(imageView);
        }*/
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
}
