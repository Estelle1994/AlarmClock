package com.dev.alarmclock.entity;

/**
 * @author Created by kyle
 * @project Testssss
 * @package enty
 * @date 2018/6/22
 */
public class MainData {

    /**
     * ret : 0
     * msg : ok
     * data : {"image_width":590,"image_height":352,"face_list":""}
     */

    private int ret;
    private String msg;
    private DataBean data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MainData{" +
                "ret=" + ret +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
