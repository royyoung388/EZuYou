package com.you.ezuyou.InternetUtls.LoginUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

import com.you.ezuyou.Login.Login;
import com.you.ezuyou.Menu.Menu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/4.
 */

public class LoginUtils {

    //处理登录
    public static void start_Login(final String username, final String userpwd, final EditText pwd) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Socket socket = null;

                try {
                    socket = new Socket("172.29.179.1", 30003);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    DataInputStream in = new DataInputStream(socket.getInputStream());

                    out.writeUTF(username);
                    out.writeUTF(userpwd);

                    String string = in.readUTF();
                    if (string.equals("error")) {
                        ((Activity)pwd.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(pwd.getContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                pwd.setText("");
                            }
                        });
                    } else if (string.equals("right")) {
                        pwd.getContext().startActivity(new Intent(pwd.getContext(), Menu.class));
                        ((Activity)pwd.getContext()).finish();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //处理注册信息
    public  static void start_sign(final String username, final String userpwd, final EditText name) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;

                try {
                    socket = new Socket("172.29.179.1", 30004);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    DataInputStream in = new DataInputStream(socket.getInputStream());

                    out.writeUTF(username);
                    out.writeUTF(userpwd);

                    if (in.readUTF().equals("error")) {
                        ((Activity)name.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((Activity)name.getContext()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(name.getContext(), "用户名已存在", Toast.LENGTH_SHORT).show();
                                        name.setText("");
                                    }
                                });
                            }
                        });
                    } else {
                        ((Activity)name.getContext()).finish();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
