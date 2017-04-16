package com.you.ezuyou.InternetUtls.HomeUtils;

import com.you.ezuyou.Login.Login;
import com.you.ezuyou.keyword.KeyWord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/15.
 */

public class Hoem_Pay_Change_Status extends Thread{

    private String id, tag;

    public Hoem_Pay_Change_Status(String id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public void run() {

        Socket socket = null;

        try {
            socket = new Socket(Login.IP, KeyWord.PORT_PAY);

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF(id);
            int a = Integer.parseInt(tag);
            dataOutputStream.writeInt(Integer.valueOf(tag));

            dataOutputStream.close();
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
