package com.you.ezuyou.InternetUtls.HomeUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.you.ezuyou.Home.Item;
import com.you.ezuyou.Login.Login;
import com.you.ezuyou.keyword.KeyWord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/9.
 */

public class GetHome_Item extends Thread {

    private Handler handler;
    private int tag;
    private Context context;

    private SharedPreferences sp;

    public GetHome_Item(Handler handler, int tag, Context context) {
        this.handler = handler;
        this.tag = tag;
        this.context = context;
    }

    public void run() {
        Socket socket = null;
        String home_item = "";

        try {
            socket = new Socket(Login.IP, KeyWord.PORT_HOME_ITEM);

            DataInputStream dataInput = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);

            dataOutputStream.writeUTF(sp.getString("id", null));
            dataOutputStream.writeInt(tag);

            int count = dataInput.readInt();

            home_item += dataInput.readUTF();

            Thread getimage = new GetImage(handler, home_item, count, tag, context);
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
