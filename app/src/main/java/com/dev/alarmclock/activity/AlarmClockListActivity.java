package com.dev.alarmclock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.dev.alarmclock.MainActivity;
import com.dev.alarmclock.R;

/**
 * Created by ${Estelle} on 2018/4/19.
 */

public class AlarmClockListActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView add;
    private ListView alarmClockList;
    private AlarmClockAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock_list);
        add = (ImageView) findViewById(R.id.iv_alarm_clock_add);
        alarmClockList = (ListView) findViewById(R.id.lv_alarm_clock_list);
        add.setOnClickListener(this);
     }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_alarm_clock_add:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private class AlarmClockAdapter {

    }
}
