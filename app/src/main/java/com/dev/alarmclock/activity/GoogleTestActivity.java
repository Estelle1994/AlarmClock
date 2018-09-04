package com.dev.alarmclock.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dev.alarmclock.R;

import java.io.IOException;

/**
 * Created by ${Estelle} on 2018/8/7.
 */

public class GoogleTestActivity extends Activity {
    String[] cailiao = {"hello", "word", "i", "love", "you"};
    private static MediaPlayer mp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //        String path="http://translate.google.com/translate_tts?ie=UTF-8&q=hello&tl=en&prev=input";     //这里给一个歌曲的网络地址就行了
//        Uri  uri  =  Uri.parse(path);
//        MediaPlayer   player  =   MediaPlayer.create(MainActivity.this, uri);
//        player.start();


//            Uri uri  =  Uri.parse(getUri(word));
//            MediaPlayer player  =   MediaPlayer.create(GoogleTestActivity.this, uri);
//            player.start();
//            uri = null ;
//            player = null ;
        musicPlay(getUri("hello"));


    }

    private String getUri(String word) {
        String path = "http://translate.google.com/translate_tts?ie=UTF-8&q=" + word + "&tl=en&prev=input";
        Log.e("URL",path);
        return path;
    }

    private void musicPlay(String url) {
        mp = new MediaPlayer();
        mp.reset();
        try {
            mp.setDataSource(url);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mp != null) {
            mp.stop();
            mp.release();
        }

    }

}
