package com.you.ezuyou.InternetUtls.LoginUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.you.ezuyou.Chat.Chat_Item;
import com.you.ezuyou.InternetUtls.ChatUtils.Chat_From_Other;
import com.you.ezuyou.InternetUtls.ChatUtils.Start_Chat;
import com.you.ezuyou.Menu.Menu;
import com.you.ezuyou.keyword.KeyWord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/9.
 */

public class Start_Login extends Thread{

    private String userpwd;
    public static String username;
    private EditText pwd;
    private SharedPreferences sp;

    public Start_Login(String username, String userpwd, EditText pwd) {
        this.username = username;
        this.userpwd = userpwd;
        this.pwd = pwd;
        sp = pwd.getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
    }

    @Override
    public void run() {

        Socket socket = null;

        try {
            socket = new Socket(com.you.ezuyou.Login.Login.IP, KeyWord.PORT_LOGIN);
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
                SharedPreferences.Editor editor = sp.edit();
                String id = in.readUTF();
                editor.putString("id", id);
                editor.apply();
                System.out.println("写入id：" + id);

                //连接
                Thread connection_chat = new Start_Chat("连接", id);
                connection_chat.start();

                //通过单例模式获取实例，监听接收消息
                //Thread chatfromother = Chat_From_Other.getInstance(new Chat_Item().getHandler());


                pwd.getContext().startActivity(new Intent(pwd.getContext(), Menu.class));
                ((Activity) pwd.getContext()).finish();
            }

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
