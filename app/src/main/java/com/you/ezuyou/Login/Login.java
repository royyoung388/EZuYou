package com.you.ezuyou.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.you.ezuyou.InternetUtls.LoginUtils.LoginUtils;
import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/4/4.
 */

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText name, pwd;
    private CheckBox remember;
    private Button submit;
    private TextView sign;

    private String userName, userPass;

    private SharedPreferences sp = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Login_Toolbar);
        toolbar.setTitle("");
        //toolbar.inflateMenu(R.menu.menu_main);//设置右上角的填充菜单
        setSupportActionBar(toolbar);

        bindView();
    }

    //检查并登录
    private void check() {
        // 获取用户名
        userName = name.getText().toString();
        // 获取用户密码
        userPass = pwd.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            //重置输入和验证码
            pwd.setText(null);
        } else if (TextUtils.isEmpty(userPass)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            //重置输入和验证码
            pwd.setText(null);
        } else {
            Toast.makeText(this, "正在连接", Toast.LENGTH_SHORT).show();
            //登录
            LoginUtils.start_Login(userName, userPass, pwd);
            //实现记住密码
            rememer();
        }
    }

    //绑定控件
    public void bindView() {
        name = (EditText) findViewById(R.id.Login_name);
        pwd = (EditText) findViewById(R.id.Login_password);
        remember = (CheckBox) findViewById(R.id.Login_checkbox);
        submit = (Button) findViewById(R.id.Login_submit);
        sign = (TextView) findViewById(R.id.Login_sign);

        submit.setOnClickListener(this);
        sign.setOnClickListener(this);

        sp = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if (sp != null) {
            name.setText(sp.getString("name",null));
            pwd.setText(sp.getString("pwd",null));
        }
    }

    //记住密码
    private void rememer() {
        sp = getSharedPreferences("login",MODE_PRIVATE);
        if (remember.isChecked()) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name",userName);
            editor.putString("pwd",userPass);
            editor.apply();
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name",null);
            editor.putString("pwd",null);
            editor.apply();
            //重置输入
            pwd.setText(null);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Login_submit:
                check();
                break;
            case R.id.Login_sign:
                startActivity(new Intent(Login.this, Login_sign.class));
                break;
        }
    }
}
