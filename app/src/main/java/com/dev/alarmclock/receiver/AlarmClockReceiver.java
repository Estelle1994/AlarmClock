package com.dev.alarmclock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.dev.alarmclock.activity.AlarmClockActivity;
import com.dev.alarmclock.util.AlarmManagerUtil;

/**
 * Created by ${Estelle} on 2018/4/19.
 */

public class AlarmClockReceiver extends BroadcastReceiver {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        String msg = intent.getStringExtra("msg");
        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        if (intervalMillis != 0) {
            AlarmManagerUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                    intent);
        }
        int flag = intent.getIntExtra("soundOrVibrator", 0);
        Intent clockIntent = new Intent(context, AlarmClockActivity.class);
        clockIntent.putExtra("msg", msg);
        clockIntent.putExtra("flag", flag);
        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(clockIntent);
    }
}
