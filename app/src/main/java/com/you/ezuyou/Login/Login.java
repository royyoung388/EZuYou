package com.you.ezuyou.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.you.ezuyou.InternetUtls.ChatUtils.Chat_From_Other;
import com.you.ezuyou.InternetUtls.LoginUtils.Start_Login;
import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/4/4.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText name, pwd;
    private CheckBox remember;
    private Button submit;
    private TextView sign, changeip;

    private String userName, userPass;

    private SharedPreferences sp = null;

    public static String IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /*//使用toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.Login_Toolbar);
        toolbar.setTitle("");
        //toolbar.inflateMenu(R.menu.menu_main);//设置右上角的填充菜单
        setSupportActionBar(toolbar);*/

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
            Thread start_Login = new Start_Login(userName, userPass, pwd);
            start_Login.start();

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
        //添加下划线
        sign.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        changeip = (TextView) findViewById(R.id.Login_changeip);

        submit.setOnClickListener(this);
        sign.setOnClickListener(this);
        changeip.setOnClickListener(this);

        sp = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        if (sp.getString("name", null) != null) {
            name.setText(sp.getString("name", null));
            pwd.setText(sp.getString("pwd", null));
        }
        if (sp.getString("IP", null) != null) Login.IP = sp.getString("IP", null);
        else Login.IP = "172.29.179.1";
    }

    //记住密码
    private void rememer() {
        //sp = getSharedPreferences("login",MODE_PRIVATE);
        if (remember.isChecked()) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", userName);
            editor.putString("pwd", userPass);
            editor.apply();
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", null);
            editor.putString("pwd", null);
            editor.apply();
            //重置输入
            pwd.setText(null);
        }
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Login_submit:
                check();
                break;
            case R.id.Login_sign:
                startActivity(new Intent(Login.this, Login_sign.class));
                break;
            case R.id.Login_changeip:
                dialog_changeIP();
                break;
        }
    }

    //弹出编辑对话框
    private void dialog_changeIP() {
         /*@setView 装入一个EditView*/
        final EditText editText = new EditText(Login.this);
        editText.setHint("当前IP为" + Login.IP);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(Login.this);
        inputDialog.setTitle("请输入服务器IP地址").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (!editText.getText().toString().equals("")) {
                            Login.IP = editText.getText().toString();

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("IP", Login.IP);
                            editor.apply();
                        }
                        Toast.makeText(Login.this, "修改后IP为" + Login.IP, Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}
