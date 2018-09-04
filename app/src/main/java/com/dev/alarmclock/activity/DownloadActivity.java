package com.dev.alarmclock.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dev.alarmclock.R;
import com.dev.alarmclock.service.DownloadService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ${Estelle} on 2018/8/29.
 */

public class DownloadActivity extends AppCompatActivity {
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_pause)
    Button btnPause;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (DownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
        Intent intent = new Intent(this,DownloadService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DownloadActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @OnClick({R.id.btn_start, R.id.btn_pause, R.id.btn_cancel})
    public void onViewClicked(View view) {
        if (downloadBinder == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_start:
                String url = "http://139.199.188.134:8080/systest_upAPK/newUpload/Ii5c1nXVi31535535736713app-release-jiaoyu.apk";
                downloadBinder.startDownload(url);
                break;
            case R.id.btn_pause:
                downloadBinder.pauseDownload();
                break;
            case R.id.btn_cancel:
                downloadBinder.cancelDownload();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
