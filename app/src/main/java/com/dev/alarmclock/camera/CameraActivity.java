package com.dev.alarmclock.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;


import com.dev.alarmclock.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ${Estelle} on 2018/8/20.
 */

public class CameraActivity extends AppCompatActivity {
    @BindView(R.id.btn_camera)
    Button btnCamera;
    @BindView(R.id.iv_camera_picture)
    ImageView ivCameraPicture;
    @BindView(R.id.sv_camera)
    SurfaceView surfaceView;

    private SurfaceHolder holder;
    private android.hardware.Camera camera;
    private String filePath = "/sdcard/Pictures/";
    private boolean isClicked = false;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        //设置拍摄方向
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_camera2);
        ButterKnife.bind(this);
        holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceCallback());
        mContext = this;
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private final class SurfaceCallback implements SurfaceHolder.Callback {
        private boolean preview;

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            android.hardware.Camera.Parameters parameters = camera.getParameters();
            parameters.setPictureFormat(PixelFormat.JPEG);
            camera.setParameters(parameters);
            camera.setDisplayOrientation(90);
            camera.startPreview();//开始预览
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            //开启相机
            if (camera == null) {
                camera = android.hardware.Camera.open();
                try {
                    camera.setPreviewDisplay(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            //关闭预览并释放资源
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @OnClick({R.id.btn_camera, R.id.iv_camera_picture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                takePicture();
                break;
            case R.id.iv_camera_picture:
                //String picPath = (String)view.getTag();
                Intent intent = new Intent();
                intent.putExtra("path",filePath);
                intent.setClass(mContext,PictureActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void takePicture() {
        if (!isClicked) {//如果没有点击
            camera.autoFocus(new android.hardware.Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, android.hardware.Camera camera) {
                    if (success) {
                        android.hardware.Camera.Parameters parameters = camera.getParameters();
                        parameters.setPictureFormat(PixelFormat.JPEG);
                        camera.setParameters(parameters);
                        camera.takePicture(null,null,jpeg);
                    }
                }
            });
            isClicked = true;
        } else {
            camera.startPreview();
            isClicked = false;
        }
    }

    android.hardware.Camera.PictureCallback jpeg = new android.hardware.Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
            String filePath1 = "/sdcard/Tencent/QQfile_recv/用户名：userzhang密码：user123.jpg";
            //Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
            Bitmap bitmap = BitmapFactory.decodeFile(filePath1);
            String aa = String.valueOf(bitmap.getByteCount());
            Log.e("原始图片大小",aa);
            Bitmap bitmap11 = compress(bitmap);
            String bb = String.valueOf(bitmap11.getByteCount());
            Log.e("压缩后的图片大小",bb);
            ivCameraPicture.setImageBitmap(bitmap11);
            //将bitmap图片保存到指定文件夹
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            String date = sDateFormat.format(new Date());
            File fileDir = new File(filePath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            String filePath2 = filePath + date + ".jpg";
            File file = new File(filePath2);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap11.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datas = baos.toByteArray();
                fos.write(datas);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    private Bitmap compress(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>500) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 20, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩

    }

    private Bitmap compressImage(Bitmap image1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image1.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>500) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image1.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    //将图片进行缩放并显示到相应的位置上
    public BitmapDrawable changeBitmapToDrawable(Bitmap bitmapOrg){
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        int newWidth = 100;
        float scaleWidth = (float) newWidth/width;
        float scaleHeight = scaleWidth;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg,0,0,width,height,matrix,true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(resizedBitmap);
        return bitmapDrawable;

    }
}
