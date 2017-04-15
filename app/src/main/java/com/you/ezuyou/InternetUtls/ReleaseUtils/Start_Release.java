package com.you.ezuyou.InternetUtls.ReleaseUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;

import com.you.ezuyou.InternetUtls.LoginUtils.Start_Login;
import com.you.ezuyou.Login.Login;
import com.you.ezuyou.keyword.KeyWord;
import com.you.ezuyou.utils.CompressBitmap;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/4/9.
 */

public class Start_Release extends Thread {

    private LinkedList<String> dataList;
    private String name, sell, rent, detil;
    private SharedPreferences sp;

    public Start_Release(LinkedList<String> dataList, String name, String sell, String rent, String detil, Context context) {
        this.dataList = dataList;
        this.name = name;
        this.sell = sell;
        this.rent = rent;
        this.detil = detil;
        sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);
    }

    @Override
    public void run() {

        //开启线程对图片进行压缩,然后按照原路径保存
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < dataList.size() - 1; i++) {
                    CompressBitmap.getimage(dataList.get(i));
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Socket socket = null;

        try {
            socket = new Socket(Login.IP, KeyWord.PORT_RELEASE);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            System.out.println(sp.getString("id", null));
            out.writeUTF(sp.getString("id", null));
            //用户的用户名
            //out.writeUTF(sp.getString("name", null));
            out.writeUTF(Start_Login.username);
            out.writeUTF(name);
            out.writeUTF(sell);
            out.writeUTF(rent);
            out.writeUTF(detil);

            int count = dataList.size() - 1;
            out.writeInt(count);

            System.out.println(dataList.get(0));

            for (int i = 0; i < count; i++) {
                FileInputStream fis = new FileInputStream(dataList.get(i));
                int size = fis.available();
                byte[] data = new byte[size];
                fis.read(data);
                out.writeInt(size);
                out.write(data);
                out.flush();
                fis.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
