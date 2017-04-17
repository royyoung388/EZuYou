package com.you.ezuyou.My;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.you.ezuyou.InternetUtls.MyUtils.ChangePwd;
import com.you.ezuyou.Login.Login;
import com.you.ezuyou.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/3/23.
 */

public class My_Information extends AppCompatActivity implements View.OnClickListener {

    private TextView name, school, school_class, number;
    private Button changepwd, logout;
    private My_Utils_my my_utils_my;

    private SharedPreferences sp;

    private String status = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    status = (String) msg.obj;
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information);

        Toolbar my_information_toolbar = (Toolbar) findViewById(R.id.my_information_toolbar);
        my_information_toolbar.setTitle("");
        my_information_toolbar.setNavigationIcon(R.drawable.icon_back);
        setSupportActionBar(my_information_toolbar);

        bindView();

        sp = this.getSharedPreferences("login", MODE_PRIVATE);

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
                dialog_ChangePwd();
                break;
            case R.id.my_information_bt_logout:
                Intent intent = new Intent(My_Information.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    //改密码
    private void dialog_ChangePwd() {

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.my_information_dialog, null);
        final EditText pwd_old = (EditText) view.findViewById(R.id.dialog_old);
        final EditText pwd_new = (EditText) view.findViewById(R.id.dialog_new);
        final EditText pwd_renew = (EditText) view.findViewById(R.id.dialog_renew);

        AlertDialog.Builder builder = new AlertDialog.Builder(My_Information.this);
        builder.setTitle("更改密码");
        builder.setView(view);

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, false);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (pwd_old.getText().toString() == null || pwd_old.getText().toString().equals("") &&
                                pwd_new.getText().toString() == null || pwd_new.getText().toString().equals("") &&
                                pwd_renew.getText().toString() == null || pwd_renew.getText().toString().equals(""))
                            Toast.makeText(My_Information.this, "输入有误, 不能为空", Toast.LENGTH_SHORT).show();
                        else if (!pwd_new.getText().toString().equals(pwd_renew.getText().toString())) {
                            Toast.makeText(My_Information.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                            pwd_old.setText("");
                            pwd_renew.setText("");
                        } else {
                            String id = sp.getString("id", null);
                            Thread changePwd = new ChangePwd(handler, id, pwd_old.getText().toString(), pwd_new.getText().toString());
                            changePwd.start();
                            try {
                                changePwd.join(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (status.equals("wrong")) {
                                Toast.makeText(My_Information.this, "输入的旧密码错误", Toast.LENGTH_SHORT).show();
                                pwd_old.setText("");
                                pwd_renew.setText("");
                            } else if (status.equals("right")) {
                                Toast.makeText(My_Information.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                                pwd_old.setText("");
                                pwd_new.setText("");
                                pwd_renew.setText("");
                                try {
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
        builder.create().show();
    }
}
