package com.dev.alarmclock.entity;

/**
 * @author Created by kyle
 * @project Testssss
 * @package enty
 * @date 2018/6/22
 */
public class FaceProfileBean {
    /**
     * x : 380
     * y : 207
     */

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    @Override
    public String toString() {
        return "FaceProfileBean{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
