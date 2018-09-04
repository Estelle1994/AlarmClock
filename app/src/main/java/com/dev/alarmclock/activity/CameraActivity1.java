package com.dev.alarmclock.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dev.alarmclock.R;

/**
 * Created by ${Estelle} on 2018/6/6.
 */

public class CameraActivity1 extends AppCompatActivity{
    private Button takePicture;
    private ImageView cameraIv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera1);
        cameraIv = (ImageView) findViewById(R.id.imageView);
        takePicture = (Button) findViewById(R.id.btn_take_picture);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity1.this,CameraActivity.class);
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0 && requestCode == 100) {
            String path = data.getStringExtra("path");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            ImageSize imageSize = getImageViewSize(cameraIv);
            options.inSampleSize = caculateInSampleSize(options, imageSize.width, imageSize.height);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            cameraIv.setImageBitmap(bitmap);
            cameraIv.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cameraIv.setVisibility(View.GONE);
                }
            }, 3000);//3秒后执行Runnable中的run方法
        }
    }

    public static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);

            inSampleSize = Math.max(widthRadio, heightRadio);
        }

        return inSampleSize;
    }

    public static ImageSize getImageViewSize(ImageView imageView) {

        ImageSize imageSize = new ImageSize();
        DisplayMetrics displayMetrics = imageView.getContext().getResources()
                .getDisplayMetrics();

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) imageView.getLayoutParams();

        int width = imageView.getWidth();// 获取imageview的实际宽度
        if (width <= 0) {
            width = lp.width;// 获取imageview在layout中声明的宽度
        }

        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }

        int height = imageView.getHeight();// 获取imageview的实际高度
        if (height <= 0) {
            height = lp.height;// 获取imageview在layout中声明的宽度
        }

        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }
        imageSize.width = width;
        imageSize.height = height;

        return imageSize;
    }

    public static class ImageSize {
        int width;
        int height;
    }
}
