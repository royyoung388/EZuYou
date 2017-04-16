package com.you.ezuyou.InternetUtls.ChatUtils;


import com.you.ezuyou.Login.Login;
import com.you.ezuyou.keyword.KeyWord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/12.
 */

public class Start_Chat extends Thread {

    String act, id, userid, username, message;

    //连接
    public Start_Chat(String act, String id) {
        this.act = act;
        this.id = id;
    }

    //聊天
    public Start_Chat(String act, String id, String userid, String username, String message) {
        this.act = act;
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.message = message;
    }

    @Override
    public void run() {

        Socket socket = null;

        try {
            socket = new Socket(Login.IP, KeyWord.PORT_CHAT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF(act);
            out.writeUTF(id);

            //连接
            if (act.equals("连接")) System.out.println(in.readUTF());
            else if (act.equals("聊天")) {
                out.writeUTF(userid);
                out.writeUTF(username);
                out.writeUTF(message);
                if (in.readUTF().equals("聊天成功")) {
                    System.out.println("聊天成功");
                }
            }

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
