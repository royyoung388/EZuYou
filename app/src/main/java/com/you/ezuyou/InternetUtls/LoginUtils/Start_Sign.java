package com.you.ezuyou.InternetUtls.LoginUtils;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import com.you.ezuyou.Login.*;
import com.you.ezuyou.keyword.KeyWord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/9.
 */

public class Start_Sign extends Thread{

    private String userid, username, userpwd, userschool, userschool_class, usernumber, usersex;
    EditText name;

    public Start_Sign(String username, String userpwd, String userschool, String userschool_class, String usernumber, String usersex, EditText name) {
        this.username = username;
        this.userpwd = userpwd;
        this.userschool = userschool;
        this.userschool_class = userschool_class;
        this.usernumber = usernumber;
        this.usersex = usersex;
        this.name = name;
    }

    @Override
    public void run() {
        Socket socket = null;

        try {
            socket = new Socket(com.you.ezuyou.Login.Login.IP, KeyWord.PORT_LOGIN_SIGN);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF(username);
            out.writeUTF(userpwd);
            out.writeUTF(userschool);
            out.writeUTF(userschool_class);
            out.writeUTF(usernumber);
            out.writeUTF(usersex);

            if (in.readUTF().equals("用户已存在")) {
                ((Activity)name.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((Activity)name.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(name.getContext(), "用户已存在", Toast.LENGTH_SHORT).show();
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
}
