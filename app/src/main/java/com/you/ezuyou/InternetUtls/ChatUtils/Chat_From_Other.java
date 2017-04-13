package com.you.ezuyou.InternetUtls.ChatUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.you.ezuyou.keyword.KeyWord;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/12.
 */

public class Chat_From_Other extends Thread {

    private ServerSocket serverSocket;
    private Handler handler;

    //使用单例模式确保该线程只有一个
    // 静态实例变量加上volatile
    private static volatile Chat_From_Other chat_from_other;

    // 双重检查锁
    public static Chat_From_Other getInstance(Handler handler) {
        if (chat_from_other == null) {
            synchronized (Chat_From_Other.class) {
                if (chat_from_other == null) {
                    chat_from_other = new Chat_From_Other(handler);
                    chat_from_other.start();
                }
            }
        }
        return chat_from_other;
    }

    // 私有化构造函数
    private Chat_From_Other(Handler handler) {

        this.handler = handler;

        // TODO Auto-generated constructor stub
        System.out.println("Chat_From_Other启动");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    serverSocket = new ServerSocket(KeyWord.PORT_CHAT_FROM_OTHER);

                    while (true) {
                        // 一旦有堵塞, 则表示服务器与客户端获得了连接
                        Socket client = serverSocket.accept();
                        System.out.println("新的设备，连接Chat_From_Other：" + client.getInetAddress().toString());

                        new HandlerThread(client);
                    }
                } catch (Exception e) {
                    System.out.println("服务器异常: " + e.getMessage());
                }
            }
        }).start();
    }

    // 处理这次连接
    private class HandlerThread implements Runnable {

        private Socket client;
        private int index;

        public HandlerThread(Socket client) {
            this.client = client;
            new Thread(this).start();
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub

            try {

                DataInputStream inputStream = new DataInputStream(client.getInputStream());

                String id = inputStream.readUTF();
                System.out.println("接收发送方的id:" + id);
                //接收name
                String useranme = inputStream.readUTF();
                System.out.println("接收到useranme:" + useranme);
                String message = inputStream.readUTF();
                System.out.println("接收到消息:" + message);

                //接收到新消息，通知给handle，meg = 1
                Message meg = new Message();
                meg.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("useranme", useranme);
                bundle.putString("message", message);
                meg.setData(bundle);
                handler.sendMessage(meg);

                inputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("聊天接收运行失败");
                e.printStackTrace();
            }
        }
    }
}
