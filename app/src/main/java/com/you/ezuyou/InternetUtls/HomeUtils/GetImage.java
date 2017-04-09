package com.you.ezuyou.InternetUtls.HomeUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.you.ezuyou.Home.Item;
import com.you.ezuyou.Login.Login;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/9.
 */

public class GetImage extends Thread{

    private Handler handler;
    private String home_item;
    private int count;

    public GetImage(Handler handler, String home_item, int count) {
        this.handler = handler;
        this.home_item = home_item;
        this.count = count;
    }

    public void run() {
        Socket socket = null;
        Bitmap[] image = new Bitmap[count];

        try {
            socket = new Socket(Login.IP, 30002);
            DataInputStream dataInput = new DataInputStream(socket.getInputStream());
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


            Item item = new Item(image, home_item);

            //Item_Adapter adapter = new Item_Adapter(item.Data, view.getContext());
            //listView.setAdapter(adapter);

            Message message = new Message();
            message.what = 2;
            message.obj = item;
            handler.sendMessage(message);

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
