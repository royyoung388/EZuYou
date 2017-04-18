package com.you.ezuyou.InternetUtls.StrategyUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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

public class GetStrategy_Image extends Thread{

    private Handler handler;
    private String strategy_item, id;
    private int count, tag;

    public GetStrategy_Image(Handler handler, String strategy_item, String id, int count, int tag) {
        this.handler = handler;
        this.strategy_item = strategy_item;
        this.id = id;
        this.count = count;
        this.tag = tag;
    }

    public void run() {
        Socket socket = null;
        Bitmap[] image = new Bitmap[count];

        try {
            socket = new Socket(Login.IP, KeyWord.PORT_STRATEGY_IMAGE);

            DataInputStream dataInput = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF(id);
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

            //-1:所有的图片
            //-2:指定id
            if (tag < 0) {
                Strategy_Item item = new Strategy_Item(image, strategy_item);
                Message message = new Message();
                message.what = 2;
                message.obj = item;
                handler.sendMessage(message);
            } else {
                //获取指定tag
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("strategy_item", strategy_item);
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
