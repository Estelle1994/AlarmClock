package com.dev.alarmclock.entity;


import java.util.List;

/**
 * @author Created by kyle
 * @project Testssss
 * @package enty
 * @date 2018/6/22
 */
public class FaceShapeBean {
    /**
     * face_profile :
     * left_eye :
     * right_eye :
     * left_eyebrow :
     * right_eyebrow :
     * mouth :
     * nose :
     */

    private List<FaceProfileBean> face_profile;
    private List<FaceProfileBean> left_eye;
    private List<FaceProfileBean> right_eye;
    private List<FaceProfileBean> left_eyebrow;
    private List<FaceProfileBean> right_eyebrow;
    private List<FaceProfileBean> mouth;
    private List<FaceProfileBean> nose;

    public List<FaceProfileBean> getFace_profile() {
        return face_profile;
    }

    public void setFace_profile(List<FaceProfileBean> face_profile) {
        this.face_profile = face_profile;
    }

    public List<FaceProfileBean> getLeft_eye() {
        return left_eye;
    }

    public void setLeft_eye(List<FaceProfileBean> left_eye) {
        this.left_eye = left_eye;
    }

    public List<FaceProfileBean> getRight_eye() {
        return right_eye;
    }

    public void setRight_eye(List<FaceProfileBean> right_eye) {
        this.right_eye = right_eye;
    }

    public List<FaceProfileBean> getLeft_eyebrow() {
        return left_eyebrow;
    }

    public void setLeft_eyebrow(List<FaceProfileBean> left_eyebrow) {
        this.left_eyebrow = left_eyebrow;
    }

    public List<FaceProfileBean> getRight_eyebrow() {
        return right_eyebrow;
    }

    public void setRight_eyebrow(List<FaceProfileBean> right_eyebrow) {
        this.right_eyebrow = right_eyebrow;
    }

    public List<FaceProfileBean> getMouth() {
        return mouth;
    }

    public void setMouth(List<FaceProfileBean> mouth) {
        this.mouth = mouth;
    }

    public List<FaceProfileBean> getNose() {
        return nose;
    }

    public void setNose(List<FaceProfileBean> nose) {
        this.nose = nose;
    }

    @Override
    public String toString() {
        return "FaceShapeBean{" +
                "face_profile=" + face_profile +
                ", left_eye=" + left_eye +
                ", right_eye=" + right_eye +
                ", left_eyebrow=" + left_eyebrow +
                ", right_eyebrow=" + right_eyebrow +
                ", mouth=" + mouth +
                ", nose=" + nose +
                '}';
    }
}
