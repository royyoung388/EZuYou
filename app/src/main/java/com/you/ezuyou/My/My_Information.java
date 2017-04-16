package com.you.ezuyou.My;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.you.ezuyou.Login.Login;
import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/3/23.
 */

public class My_Information extends AppCompatActivity implements View.OnClickListener{

    private TextView name, school, school_class, number;
    private Button changepwd, logout;
    private My_Utils_my my_utils_my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information);

        Toolbar my_information_toolbar = (Toolbar) findViewById(R.id.my_information_toolbar);
        my_information_toolbar.setTitle("");
        my_information_toolbar.setNavigationIcon(R.drawable.icon_back);
        setSupportActionBar(my_information_toolbar);

        bindView();

        String my = getIntent().getStringExtra("my");
        my_utils_my = new My_Utils_my(my);

        setView();
    }

    //绑定控件
    public void bindView() {
        name = (TextView) findViewById(R.id.my_information_name);
        school = (TextView) findViewById(R.id.my_information_school);
        school_class = (TextView) findViewById(R.id.my_information_school_class);
        number = (TextView) findViewById(R.id.my_information_number);
        changepwd = (Button) findViewById(R.id.my_information_bt_changepwd);
        logout = (Button) findViewById(R.id.my_information_bt_logout);

        changepwd.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    //设置view
    public void setView() {
        name.setText(my_utils_my.getUsername());
        school.setText(my_utils_my.getUserschool());
        school_class.setText(my_utils_my.getUserschool_class());
        number.setText(my_utils_my.getUsernumber());
    }

    //返回键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_information_bt_changepwd:
                break;
            case R.id.my_information_bt_logout:
                Intent intent=new Intent(My_Information.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
