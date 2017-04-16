package com.you.ezuyou.InternetUtls.HomeUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.you.ezuyou.Home.Item;
import com.you.ezuyou.Login.Login;
import com.you.ezuyou.keyword.KeyWord;

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
    private int count, tag;
    private Context context;

    private SharedPreferences sp;

    public GetImage(Handler handler, String home_item, int count, int tag, Context context) {
        this.handler = handler;
        this.home_item = home_item;
        this.count = count;
        this.tag = tag;
        this.context = context;
    }

    public void run() {
        Socket socket = null;
        Bitmap[] image = new Bitmap[count];

        try {
            socket = new Socket(Login.IP, KeyWord.PORT_HOME_IMAGE);

            DataInputStream dataInput = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);

            dataOutputStream.writeUTF(sp.getString("id", null));
            dataOutputStream.writeInt(tag);

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

            //-1:获取所有item信息
            //-2:获取指定id的信息
            //-3:获取指定id的status为1的信息
            //-4:获取指定id的status为0的信息
            if (tag < 0) {
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

            dataInput.close();
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
