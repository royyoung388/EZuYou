package com.you.ezuyou.My;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/3/23.
 */

public class My_Strategy extends AppCompatActivity{

    private Toolbar my_strategy_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_strategy);

        my_strategy_toolbar = (Toolbar) findViewById(R.id.my_strategy_toolbar);
        my_strategy_toolbar.setNavigationIcon(R.drawable.icon_back);
        setSupportActionBar(my_strategy_toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
