package com.you.ezuyou.InternetUtls.StrategyUtils;

import android.os.Handler;

import com.you.ezuyou.InternetUtls.HomeUtils.GetImage;
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
    private int position;

    public GetStrategy_Item(Handler handler, int position) {
        this.handler = handler;
        this.position = position;
    }

    public void run() {
        Socket socket = null;
        String strategy_item = "";

        try {
            socket = new Socket(Login.IP, KeyWord.PORT_STRATEGY);

            DataInputStream dataInput = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeInt(position);

            int count = dataInput.readInt();

            strategy_item += dataInput.readUTF();

            Thread getimage = new GetImage_Strategy(handler, strategy_item, count, position);
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
