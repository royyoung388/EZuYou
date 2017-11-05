package com.you.ezuyou.Strategy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.you.ezuyou.InternetUtls.LoginUtils.Start_Login;
import com.you.ezuyou.InternetUtls.StrategyUtils.Start_Strategy_Release;
import com.you.ezuyou.R;
import com.you.ezuyou.utils.ImageUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Created by Administrator on 2017/4/16.
 */

public class Strategy_Release extends AppCompatActivity implements View.OnClickListener {

    private EditText title, budget;
    private CheckBox checkBox;
    private TextView add;
    private TextView release, editor, money;
    private ImageView back;

    private SharedPreferences sp;

    private String str_id, str_title, str_editor = Start_Login.username, str_money, str_text;

    /**
     * 需要上传的图片路径  控制默认图片在最后面需要用LinkedList
     */
    private LinkedList<String> dataList = new LinkedList<String>();

    /**
     * 选择图片的返回码
     */
    public final static int SELECT_IMAGE_RESULT_CODE = 200;
    /**
     * 当前选择的图片的路径
     */
    public String mImagePath;

    //最终得到的图片路径
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.strategy_release);

        //使用toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.strategy_release_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.icon_back);

        bindView();

        sp = this.getSharedPreferences("login", MODE_PRIVATE);
    }

    private void bindView() {
        title = (EditText) findViewById(R.id.strategy_release_title);
        budget = (EditText) findViewById(R.id.strategy_release_budget);
        release = (TextView) findViewById(R.id.strategy_release_release);
        editor = (TextView) findViewById(R.id.strategy_release_editor);
        money = (TextView) findViewById(R.id.strategy_release_money);
        back = (ImageView) findViewById(R.id.strategy_release_back);
        add = (TextView) findViewById(R.id.strategy_release_add);
        checkBox = (CheckBox) findViewById(R.id.strategy_release_checkbox);

        //复选框的监听
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) str_editor = "";
            }
        });

        editor.setText(Start_Login.username);
        release.setOnClickListener(this);
        add.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void check() {
        str_title = title.getText().toString();
        str_text = budget.getText().toString();
        str_money = money.getText().toString();

        if (str_title == null || str_title.equals(""))
            Toast.makeText(Strategy_Release.this, "标题不能为空", Toast.LENGTH_SHORT).show();
        else if (str_text == null || str_text.equals(""))
            Toast.makeText(Strategy_Release.this, "内容不能为空", Toast.LENGTH_SHORT).show();
        else if (str_money == null || str_money.equals(""))
            Toast.makeText(Strategy_Release.this, "预算不能为空", Toast.LENGTH_SHORT).show();
        else if (imagePath == null || imagePath.equals(""))
            Toast.makeText(Strategy_Release.this, "未上传图片", Toast.LENGTH_SHORT).show();
        else {
            Thread strategy_release = new Start_Strategy_Release(imagePath, sp.getString("id", null), str_editor, str_title, str_money, str_text + "元/天");
            strategy_release.start();
            try {
                strategy_release.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        }
    }

    //setNavigationlcon的点击监听
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.strategy_release_release:
                check();
                break;
            case R.id.strategy_release_add:
                if (hasCarema() == false) {
                    return;
                }
                showPictureDailog();
                break;
            case R.id.strategy_release_back:
                finish();
                break;
        }
    }

    //判断相机是否存在
    private boolean hasCarema() {
        PackageManager pm = getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                && !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            Toast.makeText(this, "连接不上相机，请检查权限设置", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //与选择图片上传有关
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePath = "";
        if (requestCode == SELECT_IMAGE_RESULT_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                //有数据返回直接使用返回的图片地址
                imagePath = ImageUtils.getFilePathByFileUri(this, data.getData());
            } else {
                //无数据使用指定的图片路径
                imagePath = mImagePath;
            }
        }
    }

    /**
     * 拍照或从图库选择图片(Dialog形式)
     */
    public void showPictureDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new String[]{"拍摄照片", "选择照片", "取消"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0://拍照
                                takePhoto();
                                break;
                            case 1://相册选择图片
                                pickPhoto();
                                break;
                            case 2://取消
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            /**
             * 通过指定图片存储路径，解决部分机型onActivityResult回调 data返回为null的情况
             */
            //获取与应用相关联的路径
            String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            //根据当前时间生成图片的名称
            String timestamp = "/" + formatter.format(new Date()) + ".jpg";
            File imageFile = new File(imageFilePath, timestamp);// 通过路径创建保存文件
            mImagePath = imageFile.getAbsolutePath();
            Uri imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
            startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
        } else {
            Toast.makeText(this, "内存卡不存在!", Toast.LENGTH_LONG).show();
        }
    }


    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
    }

}
