package com.you.ezuyou.InternetUtls.HomeUtils;

import android.os.Handler;

import com.you.ezuyou.Home.Item;
import com.you.ezuyou.Login.Login;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/9.
 */

public class GetHome_Item extends Thread{

    private Handler handler;

    public GetHome_Item(Handler handler) {
        this.handler = handler;
    }

    public void run() {
        Socket socket = null;
        String home_item = "";

        try {
            socket = new Socket(Login.IP, 30001);
            DataInputStream dataInput = new DataInputStream(socket.getInputStream());

            home_item += dataInput.readUTF();

            int count = Item.getSize(home_item);
            Thread getimage = new GetImage(handler, home_item, count);
            getimage.start();

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
}
