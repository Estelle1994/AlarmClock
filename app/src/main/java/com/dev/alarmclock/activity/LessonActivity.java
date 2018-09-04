package com.dev.alarmclock.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.alarmclock.R;
import com.dev.alarmclock.entity.LessonEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;


/**
 * Created by ${Estelle} on 2018/7/30.
 */

public class LessonActivity extends AppCompatActivity {
    public static final String LESSON_CONTENT = "http://139.199.188.134:8080/pgcAPI/courseInfo/getCourseById";
    private Button requestBtn;
    private Button okHttpBtn;
    private TextView contentTv;
    private String userName = "userZhang";
    private String author;
    private String courseContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        initView();
    }

    private void initView() {
        requestBtn = (Button) findViewById(R.id.btn_request);
        contentTv = (TextView) findViewById(R.id.tv_content);
        okHttpBtn = (Button) findViewById(R.id.btn_okhttp);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //请求数据
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestCourseContent();
                    }
                }).start();

            }
        });

        okHttpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        okHttpRequest();
                    }
                }).start();
            }
        });
    }

    private void okHttpRequest() {
        String url =  "http://139.199.188.134:8080/pgcAPI/courseInfo/getCourseInfoById";
        okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient();
        String str = "{\"username\":\"" + userName + "\"}";
        //String str = "{\"username\":\"xadmin\"}";
        okhttp3.RequestBody reqBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse(str), str);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(reqBody)
                .build();
        okhttp3.Call call = okHttpClient.newCall(request);
//        try {
//            okhttp3.Response response = call.execute();
//            if (response.isSuccessful()) {
//                //获取返回数据 可以是String，bytes ,byteStream
//                Log.e("OkHttp", "response ----->" + response.body().string());
//                String json = response.body().string().toString();
//                jiexiJson(json);
//            } else {
//                Log.e("OkHttp",response.body().string());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String json = response.body().string().toString() + "";
                Log.e("OKHttp",json);
                jiexiJson(json);
            }
        });
    }

    private void jiexiJson(String json) {
        if (json != null) {
            Type listType = new TypeToken<List<LessonEntity>>(){}.getType();
            List<LessonEntity> lessonList = new Gson().fromJson(json, listType);
            for (LessonEntity list : lessonList){
                author = list.getAuthor();
                courseContent = list.getCourseContent();
            }
            Log.e("解析出来的结果：" ,"作者：" + author + "诗词内容：" + courseContent);

        }
    }

    private void requestCourseContent() {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LESSON_CONTENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("备课内容请求成功","返回的数据：" + s);
                contentTv.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("备课内容请求失败","错误信息" + volleyError.getMessage());
                byte[] htmlBodyBytes = volleyError.networkResponse.data;
                Log.e("备课内容请求失败", new String(htmlBodyBytes), volleyError);
                Toast.makeText(LessonActivity.this, "请检查网络是否可用", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("userName", userName);
                return map;
            }
        };
        mQueue.add(stringRequest);
    }
}
