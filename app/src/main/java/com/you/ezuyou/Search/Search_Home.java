package com.you.ezuyou.Search;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.you.ezuyou.Home.Home_Item;
import com.you.ezuyou.Home.Home_Item_Detil;
import com.you.ezuyou.Login.Login;
import com.you.ezuyou.R;
import com.you.ezuyou.keyword.KeyWord;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Created by Administrator on 2017/2/28.
 */

public class Search_Home extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;
    private Search_Home_Adapter search_Home_Adapter;
    private Home_Item home_item;

    private final String WANT = "home";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        initView();

        Thread searchhome = new Thread_Search_Home_Item();
        searchhome.start();
        try {
            searchhome.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("加载适配器");
        //加载适配器
        search_Home_Adapter = new Search_Home_Adapter(Search_Home.this, home_item.Data);
        listView.setAdapter(search_Home_Adapter);
        //设置adapter的接口方法
        search_Home_Adapter.setJumpActivity(new Search_Home_Adapter.JumpHomeActivity() {
            @Override
            public void jumpHome(String tag) {
                Intent intent = new Intent(Search_Home.this, Home_Item_Detil.class);
                intent.putExtra("tag", tag);
                startActivity(intent);
            }
        });

        /*//设置监听事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Search_Home.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });*/

        //设置过滤功能
        listView.setTextFilterEnabled(true);

        //设置搜索按键
        searchView.setSubmitButtonEnabled(true);

        //设置搜索事件监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //输入时触发
            public boolean onQueryTextChange(String s) {
                search_Home_Adapter.getFilter().filter(s);
                return true;
            }

            @Override
            //点击搜索时触发
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(Search_Home.this, "您搜索了" + s, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void initView() {
        searchView = (SearchView) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.list);
    }

    private class Thread_Search_Home_Item extends Thread{


        public Thread_Search_Home_Item() {
            System.out.println("线程item初始化");
        }

        @Override
        public void run() {

            Socket socket = null;
            String home_item = "";
            System.out.println("线程item启动");

            try {
                socket = new Socket(Login.IP, KeyWord.PORT_SEARCH_ITEM);

                DataInputStream dataInput = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                //想要获取home信息
                dataOutputStream.writeUTF(WANT);

                //图片个数
                int count = dataInput.readInt();

                //item信息
                home_item += dataInput.readUTF();

                Thread getimage = new Thread_Search_Home_Imgae(home_item, count);
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

    private class Thread_Search_Home_Imgae extends Thread{

        private int count;
        private Bitmap[] image;
        private String homeitem;

        public Thread_Search_Home_Imgae(String homeitem, int count) {
            System.out.println("线程image初始化");
            this.count = count;
            this.homeitem = homeitem;
            image = new Bitmap[count];
        }

        @Override
        public void run() {
            Socket socket = null;
            System.out.println("线程image启动");

            try {
                socket = new Socket(Login.IP, KeyWord.PORT_SEARCH_IMAGE);

                DataInputStream dataInput = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                dataOutputStream.writeUTF(WANT);

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
                        bmp.compress(Bitmap.CompressFormat.JPEG, 80, outPut);
                        image[i] = bmp;
                        i++;
                        outPut.close();
                    } else break;
                }

                home_item = new Home_Item(image, homeitem);

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
}
