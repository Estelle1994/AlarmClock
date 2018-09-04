package com.dev.alarmclock.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dev.alarmclock.R;

import java.io.File;


/**
 * Created by ${Estelle} on 2018/6/26.
 */

public class CropActivity extends AppCompatActivity {
    private ImageView originalImg,cropImg;
    private Button cropBtn;
    private static final int CROP_PICTURE = 0x11;
    private Uri data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        initView();
    }

    private void initView() {
        originalImg  = (ImageView) findViewById(R.id.iv_picture);
        cropImg = (ImageView) findViewById(R.id.iv_crop);
        cropBtn = (Button) findViewById(R.id.btn_crop);
        String path = "/sdcard/相机/凌乱的我.jpg";
        /*File file = new File(path);
        Glide.with(this)
                .load(file)
                .into(originalImg);*/
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        originalImg.setImageBitmap(bitmap);

        cropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImageView();
            }
        });
    }

    private void cropImageView() {
        File f = new File(getFilesDir(),"相机");
        File targetFile = new File (f,"final.jpg");
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data = FileProvider.getUriForFile(this, "com.dev.alarmclock.fileprovider", targetFile);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(targetFile);
        }
        intent.setDataAndType(Uri.fromFile(new File("/sdcard/相机/凌乱的我.jpg")), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(targetFile));
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG);
        intent.putExtra("outputX", 720);
        intent.putExtra("outputY", 720);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, CROP_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CROP_PICTURE && resultCode == RESULT_OK) {

        }
    }
}
