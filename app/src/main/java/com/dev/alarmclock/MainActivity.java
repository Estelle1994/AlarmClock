package com.dev.alarmclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.dev.alarmclock.activity.AlarmClockListActivity;
import com.dev.alarmclock.util.AlarmManagerUtil;
import com.dev.alarmclock.view.SelectRemindCyclePopup;
import com.dev.alarmclock.view.SelectRemindWayPopup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TimePickerView pvTime;
    private TextView mTimeSurplus,mTvRepeat,mTvRing,mTvShake,mTvTag,mTvSelectTime;
    private RelativeLayout mSettingRepeat,mSettingRing,mSettingShake,mSettingTag;
    private CheckBox mIsShake;
    private Button mSave,mCancel;
    private RelativeLayout allLayout;

    private int cycle;
    private int ring;
    private String once;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mCancel = (Button) findViewById(R.id.btn_cancel);
        mSave = (Button) findViewById(R.id.btn_save);
        mTvSelectTime = (TextView) findViewById(R.id.tv_select_time);
        mTimeSurplus = (TextView) findViewById(R.id.tv_time_surplus);
        mSettingRepeat = (RelativeLayout) findViewById(R.id.rl_setting_repeat);
        mSettingRing = (RelativeLayout) findViewById(R.id.rl_setting_ring);
        mSettingShake = (RelativeLayout) findViewById(R.id.rl_setting_shake);
        mSettingTag = (RelativeLayout) findViewById(R.id.rl_setting_tag);
        allLayout = (RelativeLayout) findViewById(R.id.all_layout);
        mTvRepeat = (TextView) findViewById(R.id.tv_alarm_clock_setting_repeat);
        mTvRing = (TextView) findViewById(R.id.tv_alarm_clock_setting_ring);
        mTvTag = (TextView) findViewById(R.id.tv_alarm_clock_setting_tag);

        mSettingRepeat.setOnClickListener(this);
        mSettingRing.setOnClickListener(this);
        mSettingShake.setOnClickListener(this);
        mSettingTag.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        pvTime = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                time = getTime(date);
                mTvSelectTime.setText(time);

            }
        });

        mTvSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save://确定
                setClock();
            startActivity(new Intent(MainActivity.this,AlarmClockListActivity.class));
                break;
            case R.id.btn_cancel://取消
                finish();
                break;
            case R.id.rl_setting_repeat://重复
                selectRemindCycle();
                break;
            case R.id.rl_setting_ring://响铃
                selectRingWay();
                break;
            case R.id.rl_setting_shake://震动
                break;
            case R.id.rl_setting_tag://备注
                break;
        }
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    private void setClock() {
        if (time != null && time.length() > 0) {
            String[] times = time.split(":");
            if (cycle == 0) {//是每天的闹钟
                AlarmManagerUtil.setAlarm(this, 0, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), 0, 0, "闹钟响了", ring);
            } if(cycle == -1){//是只响一次的闹钟
                AlarmManagerUtil.setAlarm(this, 1, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), 0, 0, "闹钟响了", ring);
            }else {//多选，周几的闹钟
                String weeksStr = parseRepeat(cycle, 1);
                String[] weeks = weeksStr.split(",");
                for (int i = 0; i < weeks.length; i++) {
                    AlarmManagerUtil.setAlarm(this, 2, Integer.parseInt(times[0]), Integer
                            .parseInt(times[1]), i, Integer.parseInt(weeks[i]), "闹钟响了", ring);
                }
            }
            Toast.makeText(this, "闹钟设置成功", Toast.LENGTH_LONG).show();
        }

    }

    private void selectRingWay() {
        SelectRemindWayPopup fp = new SelectRemindWayPopup(this);
        fp.showPopup(allLayout);
        fp.setOnSelectRemindWayPopupListener(new SelectRemindWayPopup
                .SelectRemindWayPopupOnClickListener() {

            @Override
            public void obtainMessage(int flag) {
                switch (flag) {
                    // 震动
                    case 0:
                        mTvRing.setText("震动");
                        ring = 0;
                        break;
                    // 铃声
                    case 1:
                        mTvRing.setText("铃声");
                        ring = 1;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void selectRemindCycle() {
        final SelectRemindCyclePopup fp = new SelectRemindCyclePopup(this);
        fp.showPopup(allLayout);
        fp.setOnSelectRemindCyclePopupListener(new SelectRemindCyclePopup
                .SelectRemindCyclePopupOnClickListener() {

            @Override
            public void obtainMessage(int flag, String ret) {
                switch (flag) {
                    // 星期一
                    case 0:

                        break;
                    // 星期二
                    case 1:

                        break;
                    // 星期三
                    case 2:

                        break;
                    // 星期四
                    case 3:

                        break;
                    // 星期五
                    case 4:

                        break;
                    // 星期六
                    case 5:

                        break;
                    // 星期日
                    case 6:

                        break;
                    // 确定
                    case 7:
                        int repeat = Integer.valueOf(ret);
                        mTvRepeat.setText(parseRepeat(repeat, 0));
                        cycle = repeat;
                        fp.dismiss();
                        break;
                    case 8:
                        mTvRepeat.setText("每天");
                        cycle = 0;
                        fp.dismiss();
                        break;
                    case 9:
                        mTvRepeat.setText("只响一次");
                        cycle = -1;
                        fp.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * @param repeat 解析二进制闹钟周期
     * @param flag   flag=0返回带有汉字的周一，周二cycle等，flag=1,返回weeks(1,2,3)
     * @return
     */
    public static String parseRepeat(int repeat, int flag) {
        String cycle = "";
        String weeks = "";
        if (repeat == 0) {
            repeat = 127;
        }
        if (repeat % 2 == 1) {
            cycle = "周一";
            weeks = "1";
        }
        if (repeat % 4 >= 2) {
            if ("".equals(cycle)) {
                cycle = "周二";
                weeks = "2";
            } else {
                cycle = cycle + "," + "周二";
                weeks = weeks + "," + "2";
            }
        }
        if (repeat % 8 >= 4) {
            if ("".equals(cycle)) {
                cycle = "周三";
                weeks = "3";
            } else {
                cycle = cycle + "," + "周三";
                weeks = weeks + "," + "3";
            }
        }
        if (repeat % 16 >= 8) {
            if ("".equals(cycle)) {
                cycle = "周四";
                weeks = "4";
            } else {
                cycle = cycle + "," + "周四";
                weeks = weeks + "," + "4";
            }
        }
        if (repeat % 32 >= 16) {
            if ("".equals(cycle)) {
                cycle = "周五";
                weeks = "5";
            } else {
                cycle = cycle + "," + "周五";
                weeks = weeks + "," + "5";
            }
        }
        if (repeat % 64 >= 32) {
            if ("".equals(cycle)) {
                cycle = "周六";
                weeks = "6";
            } else {
                cycle = cycle + "," + "周六";
                weeks = weeks + "," + "6";
            }
        }
        if (repeat / 64 == 1) {
            if ("".equals(cycle)) {
                cycle = "周日";
                weeks = "7";
            } else {
                cycle = cycle + "," + "周日";
                weeks = weeks + "," + "7";
            }
        }

        return flag == 0 ? cycle : weeks;
    }

    /**只响一次的 时间间隔
     * @param givenHour  自定义的小时
     * @param givenMinute  自定义的分钟
     * @return 返回"当前时间"距"给定时间"的时间差（单位为毫秒）      如果"给定时间"<"当前时间"，则返回的是下一天的"给定时间"
     */
    public static int dTime(int givenHour, int givenMinute){

        long currentTimeInMillis = System.currentTimeMillis();

        Calendar givenCalendar = Calendar.getInstance();
        givenCalendar.set(Calendar.HOUR_OF_DAY, givenHour);
        givenCalendar.set(Calendar.MINUTE, givenMinute);
        givenCalendar.set(Calendar.SECOND, 0);
        givenCalendar.set(Calendar.MILLISECOND, 0);
        long givenTimeInMillis = givenCalendar.getTimeInMillis();

        int result;
        if(currentTimeInMillis < givenTimeInMillis){
            result = (int) (givenTimeInMillis - currentTimeInMillis);
        }else{
            result = (int) (24*60*60*1000 - (currentTimeInMillis - givenTimeInMillis));
        }
        return result;
    }
}
