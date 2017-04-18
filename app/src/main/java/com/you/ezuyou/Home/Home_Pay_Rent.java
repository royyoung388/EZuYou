package com.you.ezuyou.Home;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.you.ezuyou.InternetUtls.HomeUtils.Hoem_Pay_Change_Status;
import com.you.ezuyou.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/17.
 */

public class Home_Pay_Rent extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView, back;
    private TextView sure, name, rent, detil, start, end, day, pay, person, school;
    private Bitmap image;
    private String str_tag, str_name, str_rent, str_detil, str_person, str_school;

    private Calendar c;
    private Date data_start, data_end;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_item_pay_rent);

        sp = this.getSharedPreferences("login", MODE_PRIVATE);

        Bundle bundle = getIntent().getExtras();

        //获取图片
        byte[] bis = bundle.getByteArray("image");
        image = BitmapFactory.decodeByteArray(bis, 0, bis.length);

        str_tag = bundle.getString("tag");
        str_name = bundle.getString("name");
        str_rent = bundle.getString("rent");
        str_detil = bundle.getString("detil");
        str_person = bundle.getString("person");
        str_school = bundle.getString("school");
        //str_sex = bundle.getString("sex");

        bindView();

        setView();
    }

    //设置控件
    private void setView() {
        //初始化日历和时间
        c = Calendar.getInstance();
        start.setText((c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日");
        end.setText((c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日");
        //初始化时间
        try {
            data_start = dateFormat.parse(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
            data_end = dateFormat.parse(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        imageView.setImageBitmap(image);
        name.setText(str_name);
        rent.setText(str_rent + "元/天");
        pay.setText(str_rent + "元");
        detil.setText(str_detil);
        person.setText(str_person);
        school.setText(str_school);
    }

    //支付
    private void pay() {
        sp = getSharedPreferences("login", MODE_PRIVATE);

        Thread pay = new Hoem_Pay_Change_Status(sp.getString("id", null), str_tag);
        pay.start();
        try {
            pay.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(Home_Pay_Rent.this, "支付成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    ///点击监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_item_pay_rent_back:
                finish();
                break;
            case R.id.home_item_pay_rent_sure:
                if (daysOfTwo() < 0)
                    Toast.makeText(Home_Pay_Rent.this, "时间设置有误", Toast.LENGTH_SHORT).show();
                else pay();
                break;
            case R.id.home_item_pay_rent_time_start:
                createDataDialog_Start();
                break;
            case R.id.home_item_pay_rent_time_end:
                createDataDialog_End();
                break;
        }
    }

    /**
     * 创建日期及时间选择对话框
     * <p>
     * ***************************
     * 注意month从0开始算！！！！*
     *****************************/

    private void createDataDialog_Start() {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(Home_Pay_Rent.this,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                        //是不是今年
                        System.out.println(c.get(Calendar.YEAR));
                        if (year == c.get(Calendar.YEAR))
                            start.setText((month + 1) + "月" + dayOfMonth + "日");
                        else start.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");

                        Toast.makeText(Home_Pay_Rent.this, "开始日期：" + year + "年"
                                        + (month + 1) + "月" + dayOfMonth + "日",
                                Toast.LENGTH_SHORT).show();
                        //设置时间
                        try {
                            data_start = dateFormat.parse(year + "-" + (month + 1) + "-" + dayOfMonth);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //判断时间差, 并且设置天数和钱
                        if (daysOfTwo() >= 0) {
                            day.setText((daysOfTwo() == 0) ? "1天" : ("" + daysOfTwo() + "天"));
                            pay.setText((daysOfTwo() == 0) ? ("" + Integer.parseInt(str_rent) + "元") : ("" + (Integer.parseInt(str_rent) * dayOfMonth) + "元"));
                        } else {
                            pay.setText("0元");
                            day.setText("0天");
                            Toast.makeText(Home_Pay_Rent.this, "时间设置有误", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                //设置初始日期
                , c.get(Calendar.YEAR)
                , c.get(Calendar.MONTH)
                , c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void createDataDialog_End() {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(Home_Pay_Rent.this,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                        //是不是今年
                        if (year == c.get(Calendar.YEAR))
                            end.setText((month + 1) + "月" + dayOfMonth + "日");
                        else end.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");

                        Toast.makeText(Home_Pay_Rent.this, "结束日期：" + year + "年"
                                        + (month + 1) + "月" + dayOfMonth + "日",
                                Toast.LENGTH_SHORT).show();
                        //设置时间
                        try {
                            data_end = dateFormat.parse(year + "-" + (month + 1) + "-" + dayOfMonth);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //判断时间差, 并且设置天数和钱
                        if (daysOfTwo() >= 0) {
                            day.setText((daysOfTwo() == 0) ? "1天" : ("" + daysOfTwo() + "天"));
                            pay.setText((daysOfTwo() == 0) ? ("" + Integer.parseInt(str_rent) + "元") : ("" + (Integer.parseInt(str_rent) * dayOfMonth) + "元"));
                        } else {
                            pay.setText("0元");
                            day.setText("0天");
                            Toast.makeText(Home_Pay_Rent.this, "时间设置有误", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                //设置初始日期
                , c.get(Calendar.YEAR)
                , c.get(Calendar.MONTH)
                , c.get(Calendar.DAY_OF_MONTH)).show();
    }

    //绑定控件
    private void bindView() {
        imageView = (ImageView) findViewById(R.id.hoem_item_pay_rent_image);
        back = (ImageView) findViewById(R.id.home_item_pay_rent_back);
        sure = (TextView) findViewById(R.id.home_item_pay_rent_sure);
        name = (TextView) findViewById(R.id.home_item_pay_rent_name);
        rent = (TextView) findViewById(R.id.home_item_pay_rent_rent);
        detil = (TextView) findViewById(R.id.home_item_pay_rent_detil);
        start = (TextView) findViewById(R.id.home_item_pay_rent_time_start);
        end = (TextView) findViewById(R.id.home_item_pay_rent_time_end);
        day = (TextView) findViewById(R.id.home_item_pay_rent_time_day);
        pay = (TextView) findViewById(R.id.home_item_pay_rent_pay);
        person = (TextView) findViewById(R.id.home_item_pay_rent_person);
        school = (TextView) findViewById(R.id.home_item_pay_rent_school);

        back.setOnClickListener(this);
        sure.setOnClickListener(this);
        start.setOnClickListener(this);
        end.setOnClickListener(this);
    }


    //计算相差多少天
    private int daysOfTwo() {

        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(data_start);
        int day_start = aCalendar.get(Calendar.DAY_OF_YEAR);
        int year_start = aCalendar.get(Calendar.YEAR);

        aCalendar.setTime(data_end);
        int day_end = aCalendar.get(Calendar.DAY_OF_YEAR);
        int year_end = aCalendar.get(Calendar.YEAR);

        if (year_start != year_end) //不同年
        {
            int timeDistance = 0;
            for (int i = year_start; i < year_end; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) //闰年
                {
                    timeDistance += 366;
                } else //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day_end - day_start);
        } else //同年
        {
            System.out.println("判断day2 - day1 : " + (day_end - day_start));
            return day_end - day_start;
        }
    }
}
