package com.dev.alarmclock.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.dev.alarmclock.R;

/**
 * Created by ${Estelle} on 2018/7/23.
 */

public class ShowPictureActivity extends AppCompatActivity {
    private ImageView showPicture;
    private Button shareBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);
        showPicture = (ImageView)findViewById(R.id.iv_picture_show);
        shareBtn = (Button)findViewById(R.id.btn_share);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        byte[] bytes = b.getByteArray("bitmap");

        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        showPicture.setImageBitmap(bmp);
    }
}
