package com.you.ezuyou.InternetUtls.HomeUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.you.ezuyou.Home.Item;
import com.you.ezuyou.Login.Login;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/9.
 */

public class GetImage extends Thread{

    private Handler handler;
    private String home_item;
    private int count, position;

    public GetImage(Handler handler, String home_item, int count, int position) {
        this.handler = handler;
        this.home_item = home_item;
        this.count = count;
        this.position = position;
    }

    public void run() {
        Socket socket = null;
        Bitmap[] image = new Bitmap[count];

        try {
            socket = new Socket(Login.IP, 30002);
            DataInputStream dataInput = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeInt(position);

            int i = 0;

            while (true) {
                int size = dataInput.readInt();

                byte[] data = new byte[size];
                int len = 0;
                while (len < size) {
                    len += dataInput.read(data, len, size - len);
                }
                if (size != 0) {
                    ByteArrayOutputStream outPut = new ByteArrayOutputStream();
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, outPut);
                    image[i] = bmp;
                    i++;
                    outPut.close();
                } else break;
            }

            //所有的图片
            if (position == -1) {
                Item item = new Item(image, home_item);
                Message message = new Message();
                message.what = 2;
                message.obj = item;
                handler.sendMessage(message);
            } else {
                //获取指定position
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("home_item", home_item);
                bundle.putSerializable("image", image);
                message.setData(bundle);
                message.what = 1;
                handler.sendMessage(message);
            }

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
