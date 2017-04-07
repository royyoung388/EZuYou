package com.you.ezuyou.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.you.ezuyou.InternetUtls.LoginUtils;
import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/4/4.
 */

public class Login_sign extends AppCompatActivity implements View.OnClickListener{

    private EditText name, pwd, repwd;
    private Button sign;

    private String userName, userPwd, userrePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Login_sign_Toolbar);
        toolbar.setTitle("");
        //toolbar.inflateMenu(R.menu.menu_main);//设置右上角的填充菜单
        setSupportActionBar(toolbar);

        bindView();
    }

    //检查并注册
    private void check() {
        // 获取用户名
        userName = name.getText().toString();
        // 获取用户密码
        userPwd = pwd.getText().toString();
        userrePwd = repwd.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (!userPwd.equals(userrePwd)){
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "正在注册", Toast.LENGTH_SHORT).show();

            //注册
            LoginUtils.start_sign(userName, userPwd, name);
        }
    }

    public void bindView() {
        name = (EditText) findViewById(R.id.Login_sign_name);
        pwd = (EditText) findViewById(R.id.Login_sign_password);
        repwd = (EditText) findViewById(R.id.Login_sign_repassword);
        sign = (Button) findViewById(R.id.Login_sign_submit);

        sign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Login_sign_submit:
                check();
                break;
        }
    }
}
