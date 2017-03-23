package com.you.ezuyou.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.you.ezuyou.Menu.Menu;
import com.you.ezuyou.R;


/**
 * Created by Administrator on 2017/2/27.
 */

public class Welcome extends AppCompatActivity{

    private TextView counter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        counter =  (TextView) findViewById(R.id.counter);
        /*CountDownTimer有四个方法：onTick，onFinsh、cancel和start。
        其中前面两个是抽象方法，所以要重写一下。
        CountDownTimer构造器的两个参数分别是第一个参数表示总时间，第二个参数表示间隔时间。
        意思就是每隔xxx会回调一次方法onTick，然后xxx之后会回调onFinish方法。*/
        CountDownTimer timer = new CountDownTimer(4000,1000) {
            //运行时
            public void onTick(long millisUntilFinished) {
                counter.setText("倒计时: " + millisUntilFinished / 1000);
            }
            //结束时
            public void onFinish() {
                startActivity(new Intent(Welcome.this, Menu.class));
                finish();
            }
        }.start();;
    }
}
