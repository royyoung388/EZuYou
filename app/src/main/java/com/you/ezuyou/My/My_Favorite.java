package com.you.ezuyou.My;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/3/23.
 */

public class My_Favorite extends AppCompatActivity{

    private Toolbar my_favorite_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_favorite);

        my_favorite_toolbar = (Toolbar) findViewById(R.id.my_favorite_toolbar);
        my_favorite_toolbar.setNavigationIcon(R.drawable.goback_dark);
        setSupportActionBar(my_favorite_toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
