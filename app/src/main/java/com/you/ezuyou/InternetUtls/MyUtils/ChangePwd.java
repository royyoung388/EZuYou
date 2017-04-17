package com.you.ezuyou.InternetUtls.MyUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.you.ezuyou.Login.Login;
import com.you.ezuyou.keyword.KeyWord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/16.
 */

public class ChangePwd extends Thread{

    private Handler handler;
    private String id, pwd_old, pwd_new;

    public ChangePwd(Handler handler, String id, String pwd_old, String pwd_new) {
        this.handler = handler;
        this.id = id;
        this.pwd_old = pwd_old;
        this.pwd_new = pwd_new;
    }

    @Override
    public void run() {

        Socket socket = null;

        try {
            socket = new Socket(Login.IP, KeyWord.PORT_MY_CHANGE_PWD);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            System.out.println("id:" + id + "  old:" + pwd_old + "  new:" + pwd_new);

            out.writeUTF(id);
            out.writeUTF(pwd_old);
            out.writeUTF(pwd_new);

            String status = in.readUTF();

            Message message = new Message();
            message.what = 1;
            message.obj = status;
            handler.sendMessage(message);

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
