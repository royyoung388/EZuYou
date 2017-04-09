package com.you.ezuyou.InternetUtls.LoginUtils;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.you.ezuyou.Menu.Menu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/9.
 */

public class Start_Login extends Thread{

    private String username, userpwd;
    private EditText pwd;

    public Start_Login(String username, String userpwd, EditText pwd) {
        this.username = username;
        this.userpwd = userpwd;
        this.pwd = pwd;
    }

    @Override
    public void run() {

        Socket socket = null;

        try {
            socket = new Socket(com.you.ezuyou.Login.Login.IP, 30003);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF(username);
            out.writeUTF(userpwd);

            String string = in.readUTF();
            if (string.equals("error")) {
                ((Activity) pwd.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(pwd.getContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        pwd.setText("");
                    }
                });
            } else if (string.equals("right")) {
                pwd.getContext().startActivity(new Intent(pwd.getContext(), Menu.class));
                ((Activity) pwd.getContext()).finish();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
