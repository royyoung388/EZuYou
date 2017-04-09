package com.you.ezuyou.InternetUtls.LoginUtils;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import com.you.ezuyou.Login.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/9.
 */

public class Start_Sign extends Thread{

    private String username, userpwd;
    EditText name;

    public Start_Sign(String username, String userpwd, EditText name) {
        this.username = username;
        this.userpwd = userpwd;
        this.name = name;
    }

    @Override
    public void run() {
        Socket socket = null;

        try {
            socket = new Socket(com.you.ezuyou.Login.Login.IP, 30004);
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
}
