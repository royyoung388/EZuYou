package com.you.ezuyou.InternetUtls.MyUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.you.ezuyou.InternetUtls.ChatUtils.Chat_From_Other;
import com.you.ezuyou.InternetUtls.ChatUtils.Start_Chat;
import com.you.ezuyou.InternetUtls.LoginUtils.Start_Login;
import com.you.ezuyou.Menu.Menu;
import com.you.ezuyou.keyword.KeyWord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/15.
 */

public class Start_My extends Thread{

    private SharedPreferences sp;
    private Context context;
    private Handler handler;

    /*//使用单例模式确保该线程只有一个
    // 静态实例变量加上volatile
    private static volatile Start_My start_my;

    // 双重检查锁
    public static Start_My getInstance(Handler handler, Context context) {
        if (start_my == null) {
            synchronized (Chat_From_Other.class) {
                if (start_my == null) {
                    start_my = new Start_My(handler, context);
                    start_my.start();
                }
            }
        }
        return start_my;
    }*/

    public Start_My(Handler handler, Context context) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void run() {

        Socket socket = null;

        try {
            socket = new Socket(com.you.ezuyou.Login.Login.IP, KeyWord.PORT_MY);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);

            out.writeUTF(sp.getString("id", null));

            String my = in.readUTF();

            Message message = new Message();
            message.what = 1;
            message.obj = my;
            handler.sendMessage(message);

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
