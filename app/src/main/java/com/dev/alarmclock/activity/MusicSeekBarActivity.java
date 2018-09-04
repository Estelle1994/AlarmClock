package com.dev.alarmclock.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dev.alarmclock.R;

import java.io.IOException;

/**
 * Created by ${Estelle} on 2018/6/4.
 */

public class MusicSeekBarActivity extends AppCompatActivity{
    private SeekBar SoundseekBar,ProceseekBar2;
    private Button button,back;
    private MediaPlayer mediaPlayer = null;
    private TextView nowPlayTime,allTime,volumeView,maxVolumeTextView;
    private AudioManager audioManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_seekbar);
        ProceseekBar2=(SeekBar)findViewById(R.id.seekBar1);
        SoundseekBar=(SeekBar)findViewById(R.id.seekBar2);
        button=(Button)findViewById(R.id.button1);
        back = (Button) findViewById(R.id.button2);
        nowPlayTime=(TextView)findViewById(R.id.textView1);
        allTime=(TextView)findViewById(R.id.textView2);
        volumeView=(TextView)findViewById(R.id.textView3);
        maxVolumeTextView=(TextView)findViewById(R.id.textView4);
        button.setOnClickListener(new ButtonListener());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.testmusic);
        audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);//获取音量服务
        int MaxSound=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//获取系统音量最大值
        maxVolumeTextView.setText(String.valueOf(MaxSound));
        SoundseekBar.setMax(MaxSound);//音量控制Bar的最大值设置为系统音量最大值
        int currentSount=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//获取当前音量
        SoundseekBar.setProgress(currentSount);//音量控制Bar的当前值设置为系统音量当前值
        SoundseekBar.setOnSeekBarChangeListener(new SeekBarListener());
        ProceseekBar2.setOnSeekBarChangeListener(new ProcessBarListener());

    }

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                try {
                    /*读取sd卡内容，注意需要权限
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource("/sdcard/test.mp3");
                    mediaPlayer.prepare();
                    mediaPlayer.start();*/
                    mediaPlayer.start();
                    StrartbarUpdate();
                    int Alltime= mediaPlayer.getDuration();
                    allTime.setText(ShowTime(Alltime));
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }

//播放进度条

    class ProcessBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // TODO Auto-generated method stub
            if (fromUser==true) {
                mediaPlayer.seekTo(progress);
                nowPlayTime.setText(ShowTime(progress));
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

    }

//音量进度条

    class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // TODO Auto-generated method stub
            if (fromUser) {
                int SeekPosition=seekBar.getProgress();
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, SeekPosition, 0);
            }
            volumeView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

    }
    //时间显示函数,我们获得音乐信息的是以毫秒为单位的，把把转换成我们熟悉的00:00格式
    public String ShowTime(int time){
        time/=1000;
        int minute=time/60;
        int hour=minute/60;
        int second=time%60;
        minute%=60;
        return String.format("%02d:%02d", minute, second);
    }
    Handler handler=new Handler();
    public void StrartbarUpdate(){
        handler.post(r);
    }
    Runnable r=new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            int CurrentPosition=mediaPlayer.getCurrentPosition();
            nowPlayTime.setText(ShowTime(CurrentPosition));
            int mMax=mediaPlayer.getDuration();
            ProceseekBar2.setMax(mMax);
            ProceseekBar2.setProgress(CurrentPosition);
            handler.postDelayed(r, 100);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
