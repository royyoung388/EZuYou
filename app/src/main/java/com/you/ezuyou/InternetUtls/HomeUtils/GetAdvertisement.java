package com.you.ezuyou.InternetUtls.HomeUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.you.ezuyou.Login.Login;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/9.
 */

public class GetAdvertisement extends Thread{

    private Handler handler;

    public GetAdvertisement(Handler handler) {
        this.handler = handler;
    }

    public void run() {
        Bitmap bmp = null;
        Socket socket = null;

        try {
            socket = new Socket(Login.IP, 30000);
            DataInputStream dataInput = new DataInputStream(socket.getInputStream());
            int size = dataInput.readInt();
            byte[] data = new byte[size];
            int len = 0;
            while (len < size) {
                len += dataInput.read(data, len, size - len);
            }
            System.out.println(len);

            ByteArrayOutputStream outPut = new ByteArrayOutputStream();
            bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outPut);

            //imageView.setImageBitmap(bmp);

            //广告图片
            Message message = new Message();
            message.what = 1;
            message.obj = bmp;
            handler.sendMessage(message);

            outPut.close();
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
