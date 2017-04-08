package com.you.ezuyou.InternetUtls;

import com.you.ezuyou.Login.Login;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/4/8.
 */

public class ReleaseUtils {

    //上传图片
    public static synchronized void start_Release(final LinkedList<String> dataList, final String name, final String sell, final String rent, final String detil) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Socket socket = null;

                try {
                    socket = new Socket(Login.IP, 30005);

                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    out.writeUTF(name);
                    out.writeUTF(sell);
                    out.writeUTF(rent);
                    out.writeUTF(detil);
                    int count = dataList.size() - 1;
                    out.writeInt(count);

                    System.out.println(dataList.get(0));

                    for (int i = 0; i < count; i++) {
                        FileInputStream fis = new FileInputStream(dataList.get(i));
                        int size = fis.available();
                        byte[] data = new byte[size];
                        fis.read(data);
                        out.writeInt(size);
                        out.write(data);
                        out.flush();
                        fis.close();
                    }

                }catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
