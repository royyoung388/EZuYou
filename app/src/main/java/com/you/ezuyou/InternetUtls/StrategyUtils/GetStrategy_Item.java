package com.you.ezuyou.InternetUtls.StrategyUtils;

import android.os.Handler;

import com.you.ezuyou.Login.Login;
import com.you.ezuyou.keyword.KeyWord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/14.
 */

public class GetStrategy_Item extends Thread{

    private Handler handler;
    private int tag;
    private String id;

    public GetStrategy_Item(Handler handler, String id, int tag) {
        this.handler = handler;
        this.id = id;
        this.tag = tag;
    }

    public void run() {
        Socket socket = null;
        String strategy_item = "";

        try {
            socket = new Socket(Login.IP, KeyWord.PORT_STRATEGY);

            DataInputStream dataInput = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF(id);
            dataOutputStream.writeInt(tag);

            int count = dataInput.readInt();

            strategy_item += dataInput.readUTF();

            Thread getimage = new GetStrategy_Image(handler, strategy_item, id, count, tag);
            getimage.start();
            getimage.join();

            dataInput.close();
            dataOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
