package com.dev.alarmclock.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.dev.alarmclock.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by ${Estelle} on 2018/8/22.
 */

public class PictureActivity extends AppCompatActivity {
    @BindView(R.id.iv_find_picture)
    ImageView ivFindPicture;
    @BindView(R.id.zoomControls)
    ZoomControls zoomControls;
    private int displayWidth;
    private int dispalyHeight;
    private float scaleWidth = 1;
    private float scaleHeight = 1;
    int bmpWidth;
    int bmpHeight;
    Bitmap bitmapOrg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_find_picture);
        ButterKnife.bind(this);
        //获取屏幕分辨率大小
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        displayWidth = dm.widthPixels;
        dispalyHeight = dm.heightPixels - 80;
        zoomControls.setIsZoomInEnabled(true);
        zoomControls.setIsZoomOutEnabled(true);

        Intent intent = getIntent();
        String picPath = (String)intent.getCharSequenceExtra("path");
        bitmapOrg = BitmapFactory.decodeFile(picPath,null);
        ivFindPicture.setImageBitmap(bitmapOrg);
        bmpWidth = bitmapOrg.getWidth();
        bmpHeight = bitmapOrg.getHeight();
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double scale = 1.25;
                scaleWidth = (float)(scaleWidth*scale);
                scaleHeight = (float)(scaleHeight*scale);
                if (scaleWidth > 1.25){
                    scaleWidth = 1;
                    scaleHeight = 1;
                }
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth,scaleHeight);
                Bitmap resizeBmp = Bitmap.createBitmap(bitmapOrg,0,0,bmpWidth,bmpHeight,matrix,true);
                ivFindPicture.setImageBitmap(resizeBmp);
            }
        });
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double scale = 0.8;
                scaleWidth = (float)(scaleWidth*scale);
                scaleHeight = (float)(scaleHeight*scale);
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth,scaleHeight);
                Bitmap resizeBmp = Bitmap.createBitmap(bitmapOrg,0,0,bmpWidth,bmpHeight,matrix,true);
                ivFindPicture.setImageBitmap(resizeBmp);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0,1,0,"返回");
        menu.add(0,2,0,"退出");
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                Intent intent = new Intent();
                intent.setClass(this,CameraActivity.class);
                startActivity(intent);
                finish();
                return true;
            case 2:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
