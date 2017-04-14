package com.you.ezuyou.InternetUtls.StrategyUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.you.ezuyou.Home.Item;
import com.you.ezuyou.Login.Login;
import com.you.ezuyou.Strategy.Strategy_Item;
import com.you.ezuyou.keyword.KeyWord;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/14.
 */

public class GetImage_Strategy extends Thread{

    private Handler handler;
    private String strategy_item;
    private int count, position;

    public GetImage_Strategy(Handler handler, String strategy_item, int count, int position) {
        this.handler = handler;
        this.strategy_item = strategy_item;
        this.count = count;
        this.position = position;
    }

    public void run() {
        Socket socket = null;
        Bitmap[] image = new Bitmap[count];

        try {
            socket = new Socket(Login.IP, KeyWord.PORT_STRATEGY_IMAGE);
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
                Strategy_Item item = new Strategy_Item(image, strategy_item);
                Message message = new Message();
                message.what = 2;
                message.obj = item;
                handler.sendMessage(message);
            } else {
                //获取指定position
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("strategy_item", strategy_item);
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
