package com.you.ezuyou.Login;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.you.ezuyou.InternetUtls.LoginUtils.Start_Sign;
import com.you.ezuyou.R;

/**
 * Created by Administrator on 2017/4/4.
 */

public class Login_sign extends AppCompatActivity implements View.OnClickListener {

    private EditText name, pwd, repwd, school, school_class, number;
    private TextView back;
    private RadioGroup group;
    private RadioButton man, women;
    private Button sign;

    private String userName, userPwd, userrePwd, userschool, userschool_class, usernumber, usersex = "男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.Login_sign_Toolbar);
        toolbar.setTitle("");
        //toolbar.inflateMenu(R.menu.menu_main);//设置右上角的填充菜单
        setSupportActionBar(toolbar);*/

        bindView();
    }

    //检查并注册
    private void check() {
        //获取输入
        // 获取用户名
        userName = name.getText().toString();
        // 获取用户密码
        userPwd = pwd.getText().toString();
        userrePwd = repwd.getText().toString();
        userschool = school.getText().toString();
        userschool_class = school_class.getText().toString();
        usernumber = number.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (!userPwd.equals(userrePwd)) {
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userschool)) {
            Toast.makeText(this, "学校不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userschool_class)) {
            Toast.makeText(this, "学院不能为空", Toast.LENGTH_SHORT).show();
            //新设计中去掉学号
//        } else if (TextUtils.isEmpty(usernumber)) {
//            Toast.makeText(this, "学号不能为空", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "正在注册", Toast.LENGTH_SHORT).show();

            //注册
            //LoginUtils.start_sign(userName, userPwd, name);
            Thread start_sign = new Start_Sign(userName, userPwd, userschool, userschool_class, "usernumber", usersex, name);
            start_sign.start();
        }
    }

    public void bindView() {
        name = (EditText) findViewById(R.id.Login_sign_name);
        pwd = (EditText) findViewById(R.id.Login_sign_password);
        repwd = (EditText) findViewById(R.id.Login_sign_repassword);
        school = (EditText) findViewById(R.id.Login_sign_school);
        school_class = (EditText) findViewById(R.id.Login_sign_class);
        number = (EditText) findViewById(R.id.Login_sign_number);
        group = (RadioGroup) findViewById(R.id.Login_sign_radiogroup);
        man = (RadioButton) findViewById(R.id.Login_sign_radio_man);
        women = (RadioButton) findViewById(R.id.Login_sign_radio_woman);
        sign = (Button) findViewById(R.id.Login_sign_submit);
        back = (TextView) findViewById(R.id.login_sign_back);
        //添加下划线
        back.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        sign.setOnClickListener(this);
        //新的设计中去掉了
        //back.setOnClickListener(this);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == man.getId()) usersex = "男";
                else usersex = "女";
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_sign_back:
                System.out.println("点击了返回键");
                finish();
                break;
            case R.id.Login_sign_submit:
                check();
                break;
        }
    }
}
