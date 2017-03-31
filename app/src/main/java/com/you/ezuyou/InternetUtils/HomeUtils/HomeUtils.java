package com.you.ezuyou.InternetUtils.HomeUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.you.ezuyou.Home.Item;
import com.you.ezuyou.Home.Item_Adapter;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/3/30.
 */

public class HomeUtils {

    //获取广告图片
    public static void GetAdvertisement(final ImageView imageView) {

        new Thread(new Runnable() {

            @Override
            public void run() {

                Bitmap bmp = null;
                Socket socket = null;

                try {
                    socket = new Socket("172.29.179.1", 30000);
                    DataInputStream dataInput = new DataInputStream(socket.getInputStream());
                    int size = dataInput.readInt();
                    byte[] data = new byte[size];
                    int len = 0;
                    while (len < size) {
                        len += dataInput.read(data, len, size - len);
                    }
                    ByteArrayOutputStream outPut = new ByteArrayOutputStream();
                    bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outPut);

                    imageView.setImageBitmap(bmp);

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
        }).start();
    }

    //获取Home_Item
    public static void getHome_Item(final View view, final ListView listView, final int[] image) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Socket socket = null;
                String home_item = "";

                try {
                    socket = new Socket("172.29.179.1", 30001);
                    DataInputStream dataInput = new DataInputStream(socket.getInputStream());

                    home_item += dataInput.readUTF();

                    Item item = new Item(image, home_item);

                    Item_Adapter adapter = new Item_Adapter(item.Data, view.getContext());
                    listView.setAdapter(adapter);

                    dataInput.close();
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
        }).start();

    }
}