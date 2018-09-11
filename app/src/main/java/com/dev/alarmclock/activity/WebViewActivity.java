package com.dev.alarmclock.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.dev.alarmclock.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ${Estelle} on 2018/9/10.
 */

public class WebViewActivity extends AppCompatActivity {
    @BindView(R.id.tv_url)
    TextView tvUrl;
    @BindView(R.id.btn_open)
    Button btnOpen;
    private static final String URL = "https://oauth.ixiaocong.com/oauth2/authorize?app_id=41d4f7d44f1d45018d194865f18e8bf0&redirect_uri=http://139.199.188.134:8080/pgc/demo.jsp&res&response_type=code&state=ald";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        tvUrl.setText(URL);
    }

    @OnClick(R.id.btn_open)
    public void onViewClicked() {
//        Intent intent = new Intent(this,PublicWebActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("webUrl", URL);
//        bundle.putString("title", "网页");
//        intent.putExtras(bundle);
//        startActivity(intent);
        //跳转到系统浏览器
        Uri uri = Uri.parse(URL);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
