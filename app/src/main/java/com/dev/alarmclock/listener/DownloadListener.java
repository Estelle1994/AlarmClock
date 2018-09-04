package com.dev.alarmclock.listener;

/**
 * Created by ${Estelle} on 2018/8/29.
 */

public interface DownloadListener {
    void onProgress(int progress);//通知当前的下载进度
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
