package com.you.ezuyou.InternetUtls.StrategyUtils;


import android.content.SharedPreferences;

import com.you.ezuyou.InternetUtls.LoginUtils.Start_Login;
import com.you.ezuyou.Login.Login;
import com.you.ezuyou.keyword.KeyWord;
import com.you.ezuyou.utils.CompressBitmap;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/16.
 */

public class Start_Strategy_Release extends Thread{

    private String imagePath, id, title, editor, money, text;

    public Start_Strategy_Release(String imagePath, String id, String editor, String title, String money, String text) {
        this.imagePath = imagePath;
        this.id = id;
        this.editor = editor;
        this.title = title;
        this.money = money;
        this.text = text;
    }

    @Override
    public void run() {

        //开启线程对图片进行压缩,然后按照原路径保存
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                    CompressBitmap.getimage(imagePath);
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
            socket = new Socket(Login.IP, KeyWord.PORT_STRATEGY_RELEASE);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF(id);
            out.writeUTF(editor);
            out.writeUTF(title);
            out.writeUTF(money);
            out.writeUTF(text);

            int count = 1;
            out.writeInt(count);

            System.out.println("图片路径为:" + imagePath);

            for (int i = 0; i < count; i++) {
                FileInputStream fis = new FileInputStream(imagePath);
                int size = fis.available();
                byte[] data = new byte[size];
                fis.read(data);
                out.writeInt(size);
                out.write(data);
                out.flush();
                fis.close();
            }

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
