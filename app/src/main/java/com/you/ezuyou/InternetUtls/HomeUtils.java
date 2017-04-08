package com.you.ezuyou.InternetUtls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.you.ezuyou.Home.Item;
import com.you.ezuyou.Home.Item_Adapter;
import com.you.ezuyou.Login.Login;
import com.you.ezuyou.Menu.Menu;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/3/30.
 */

public class HomeUtils {

    //获取广告图片
    public static synchronized void GetAdvertisement(final Handler handler) {

        new Thread(new Runnable() {

            @Override
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
        }).start();
    }

    //获取image
    public static synchronized void getImage(final Handler handler, final String home_item, final int count) {
        new Thread(new Runnable() {
            @Override
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
        }).start();

        /*int[] image = new int[]{2130837593, 2130837593, 2130837593, 2130837593, R.drawable.advertisement};

        Item item = new Item(image, home_item);

        Item_Adapter adapter = new Item_Adapter(item.Data, view.getContext());
        listView.setAdapter(adapter);*/
    }

    //获取Home_Item
    public static synchronized void getHome_Item(final Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Socket socket = null;
                String home_item = "";

                try {
                    socket = new Socket(Login.IP, 30001);
                    DataInputStream dataInput = new DataInputStream(socket.getInputStream());

                    home_item += dataInput.readUTF();

                    int count = Item.getSize(home_item);
                    getImage(handler, home_item, count);

//                    Item item = new Item(image, home_item);
//                    Item_Adapter adapter = new Item_Adapter(item.Data, view.getContext());
//                    listView.setAdapter(adapter);

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

    /*public static Drawable byteToDrawable(byte[] img) {
        Bitmap bitmap;
        if (img != null) {


            bitmap = BitmapFactory.decodeByteArray(img,0, img.length);
            Drawable drawable = new BitmapDrawable(bitmap);

            return drawable;
        }
        return null;

    }*/
}