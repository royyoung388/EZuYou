package com.you.ezuyou.InternetUtls.MyUtils;

import android.content.Context;
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
 * Created by Administrator on 2017/4/19.
 */

public class Upload_Image extends Thread {

    private String imagePath;
    private Context context;
    private SharedPreferences sp;

    public Upload_Image(Context context, String imagePath) {
        this.context = context;
        this.imagePath = imagePath;
        System.out.println("线程中的imagepath:" + imagePath);
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
            socket = new Socket(Login.IP, KeyWord.PORT_MY_UPLOOAD_IMAGE);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);

            System.out.println(sp.getString("id", null));
            out.writeUTF(sp.getString("id", null));

            FileInputStream fis = new FileInputStream(imagePath);
            int size = fis.available();
            byte[] data = new byte[size];
            fis.read(data);
            out.writeInt(size);
            out.write(data);
            out.flush();
            fis.close();

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
