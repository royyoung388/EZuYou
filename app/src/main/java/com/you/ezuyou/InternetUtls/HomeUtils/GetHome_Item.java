package com.you.ezuyou.InternetUtls.HomeUtils;

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
    int position;

    public GetHome_Item(Handler handler, int position) {
        this.handler = handler;
        this.position = position;
    }

    public void run() {
        Socket socket = null;
        String home_item = "";

        try {
            socket = new Socket(Login.IP, KeyWord.PORT_HOME_ITEM);
            DataInputStream dataInput = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeInt(position);

            int count = dataInput.readInt();

            home_item += dataInput.readUTF();

            Thread getimage = new GetImage(handler, home_item, count, position);
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
