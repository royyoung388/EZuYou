package com.you.ezuyou.My;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.you.ezuyou.InternetUtls.MyUtils.ChangePwd;
import com.you.ezuyou.InternetUtls.MyUtils.Upload_Image;
import com.you.ezuyou.Login.Login;
import com.you.ezuyou.R;
import com.you.ezuyou.utils.ImageUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Created by Administrator on 2017/3/23.
 */

public class My_Information extends AppCompatActivity implements View.OnClickListener {

    /**
     * 需要上传的图片路径  控制默认图片在最后面需要用LinkedList
     */
    private LinkedList<String> dataList = new LinkedList<String>();

    /**
     * 选择图片的返回码
     */
    public final static int SELECT_IMAGE_RESULT_CODE = 200;

    //要裁剪图片的返回码
    private static final int REQUEST_CODE_CLIP_PHOTO = 1;

    /**
     * 当前选择的图片的路径
     */
    public String mImagePath;

    //最终得到的图片路径
    private String imagePath;

    private TextView name, school, school_class, number;
    private Button changepwd, logout;
    private My_Utils_my my_utils_my;
    private ImageView my_image;

    private SharedPreferences sp;

    private String status = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    /*Bundle bundle = msg.getData();
                    status = bundle.getString("my");
                    Bitmap my_image = bundle.getParcelable("image");
                    image.setImageBitmap(my_image);*/
                    status = (String) msg.obj;
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information);

        Toolbar my_information_toolbar = (Toolbar) findViewById(R.id.my_information_toolbar);
        my_information_toolbar.setTitle("");
        my_information_toolbar.setNavigationIcon(R.drawable.icon_back);
        setSupportActionBar(my_information_toolbar);

        bindView();

        sp = this.getSharedPreferences("login", MODE_PRIVATE);

        Bundle bundle = getIntent().getExtras();
        String my = bundle.getString("my");

        //从字节流中获取图片
        byte[] bis = bundle.getByteArray("image");
        if (bis != null) {
            Bitmap image = BitmapFactory.decodeByteArray(bis, 0, bis.length);
            my_image.setImageBitmap(image);
        }

        /*String my = getIntent().getStringExtra("my");
        Bitmap image = getIntent().get*/
        my_utils_my = new My_Utils_my(my);

        setView();
    }

    //绑定控件
    public void bindView() {
        name = (TextView) findViewById(R.id.my_information_name);
        school = (TextView) findViewById(R.id.my_information_school);
        school_class = (TextView) findViewById(R.id.my_information_school_class);
        number = (TextView) findViewById(R.id.my_information_number);
        changepwd = (Button) findViewById(R.id.my_information_bt_changepwd);
        logout = (Button) findViewById(R.id.my_information_bt_logout);
        my_image = (ImageView) findViewById(R.id.my_information_image);

        changepwd.setOnClickListener(this);
        logout.setOnClickListener(this);
        my_image.setOnClickListener(this);
    }

    //设置view
    public void setView() {
        name.setText(my_utils_my.getUsername());
        school.setText(my_utils_my.getUserschool());
        school_class.setText(my_utils_my.getUserschool_class());
        number.setText(my_utils_my.getUsernumber());
    }

    //返回键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_information_bt_changepwd:
                dialog_ChangePwd();
                break;
            case R.id.my_information_bt_logout:
                Intent intent = new Intent(My_Information.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.my_information_image:
                if (hasCarema() == false) {
                    return;
                }
                showPictureDailog();
                break;
        }
    }

    //改密码
    private void dialog_ChangePwd() {

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.my_information_dialog, null);
        final EditText pwd_old = (EditText) view.findViewById(R.id.dialog_old);
        final EditText pwd_new = (EditText) view.findViewById(R.id.dialog_new);
        final EditText pwd_renew = (EditText) view.findViewById(R.id.dialog_renew);

        AlertDialog.Builder builder = new AlertDialog.Builder(My_Information.this);
        builder.setTitle("更改密码");
        builder.setView(view);

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, false);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (pwd_old.getText().toString() == null || pwd_old.getText().toString().equals("") &&
                                pwd_new.getText().toString() == null || pwd_new.getText().toString().equals("") &&
                                pwd_renew.getText().toString() == null || pwd_renew.getText().toString().equals(""))
                            Toast.makeText(My_Information.this, "输入有误, 不能为空", Toast.LENGTH_SHORT).show();
                        else if (!pwd_new.getText().toString().equals(pwd_renew.getText().toString())) {
                            Toast.makeText(My_Information.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                            pwd_old.setText("");
                            pwd_renew.setText("");
                        } else {
                            String id = sp.getString("id", null);
                            Thread changePwd = new ChangePwd(handler, id, pwd_old.getText().toString(), pwd_new.getText().toString());
                            changePwd.start();
                            try {
                                changePwd.join(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (status.equals("wrong")) {
                                Toast.makeText(My_Information.this, "输入的旧密码错误", Toast.LENGTH_SHORT).show();
                                pwd_old.setText("");
                                pwd_renew.setText("");
                            } else if (status.equals("right")) {
                                Toast.makeText(My_Information.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                                pwd_old.setText("");
                                pwd_new.setText("");
                                pwd_renew.setText("");
                                try {
                                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
        builder.create().show();
    }


    //与选择图片上传有关
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_RESULT_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                //有数据返回直接使用返回的图片地址
                imagePath = ImageUtils.getFilePathByFileUri(this, data.getData());
            } else {
                //无数据使用指定的图片路径
                imagePath = mImagePath;
            }
            clipPhoto(Uri.fromFile(new File(imagePath)));
        } else if (requestCode == REQUEST_CODE_CLIP_PHOTO) {
            onClipPhotoFinished(resultCode, data);
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

    //裁剪完成
    private void onClipPhotoFinished(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "裁剪取消", Toast.LENGTH_SHORT)
                    .show();
            return;
        } else if (resultCode != RESULT_OK) {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        //设置图片
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        my_image.setImageBitmap(bm);
        System.out.println("图片路径:" + imagePath);
        //上传图片
        Thread uploadImage = new Upload_Image(this, imagePath);
        uploadImage.start();
        //ImageView photoIv = (ImageView) findViewById(R.id.photo );
        //photoIv.setImageBitmap(bm);
    }

    //裁剪图片
    // http://www.xuanyusong.com/archives/1743
    private void clipPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        /*********************************************/
        imagePath = imagePath.substring(0, imagePath.length() - 4) + "EZuYou" + imagePath.substring(imagePath.length() - 4, imagePath.length());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imagePath)));
        /*******************************************************/
        startActivityForResult(intent, REQUEST_CODE_CLIP_PHOTO);
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
            //生成缓存文件
            //String timestamp = "/" + formatter.format(new Date()) + ".tmp";
            File imageFile = new File(imageFilePath, timestamp);// 通过路径创建保存文件
            //mOutputFile = new File(imageFilePath, timestamp);// 通过路径创建保存文件
            mImagePath = imageFile.getAbsolutePath();
            Uri imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
            startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
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
