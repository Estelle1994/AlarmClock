package com.dev.alarmclock.entity;


/**
 * @author Created by kyle
 * @project Testssss
 * @package enty
 * @date 2018/6/22
 */
public class FaceListBean {


    /**
     * face_id : 2214385550143548003
     * x : 374
     * y : 179
     * width : 70
     * height : 70
     * gender : 22
     * age : 26
     * expression : 58
     * beauty : 100
     * glass : 0
     * pitch : 18
     * yaw : 4294967283
     * roll : 13
     * face_shape : {"face_profile":"","left_eye":"","right_eye":"","left_eyebrow":"","right_eyebrow":"","mouth":"","nose":""}
     */

    private String face_id;
    private int x;
    private int y;
    private int width;
    private int height;
    private int gender;
    private int age;
    private int expression;
    private int beauty;
    private int glass;
    private int pitch;
    private long yaw;
    private long roll;
    private FaceShapeBean face_shape;

    public String getFace_id() {
        return face_id;
    }

    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }

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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getExpression() {
        return expression;
    }

    public void setExpression(int expression) {
        this.expression = expression;
    }

    public int getBeauty() {
        return beauty;
    }

    public void setBeauty(int beauty) {
        this.beauty = beauty;
    }

    public int getGlass() {
        return glass;
    }

    public void setGlass(int glass) {
        this.glass = glass;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public long getYaw() {
        return yaw;
    }

    public void setYaw(long yaw) {
        this.yaw = yaw;
    }

    public long getRoll() {
        return roll;
    }

    public void setRoll(long roll) {
        this.roll = roll;
    }

    public FaceShapeBean getFace_shape() {
        return face_shape;
    }

    public void setFace_shape(FaceShapeBean face_shape) {
        this.face_shape = face_shape;
    }

    @Override
    public String toString() {
        return "FaceListBean{" +
                "face_id='" + face_id + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", gender=" + gender +
                ", age=" + age +
                ", expression=" + expression +
                ", beauty=" + beauty +
                ", glass=" + glass +
                ", pitch=" + pitch +
                ", yaw=" + yaw +
                ", roll=" + roll +
                ", face_shape=" + face_shape +
                '}';
    }
}
