package com.dev.alarmclock.entity;

import java.util.List;

/**
 * @author Created by kyle
 * @project Testssss
 * @package enty
 * @date 2018/6/22
 */
public class DataBean {


    /**
     * image_width : 590
     * image_height : 352
     * face_list : [{"face_id":"2214385550143548003","x":374,"y":179,"width":70,"height":70,"gender":22,"age":26,"expression":58,"beauty":100,"glass":0,"pitch":18,"yaw":4294967283,"roll":13},{"face_id":"2214385551553882723","x":268,"y":61,"width":60,"height":60,"gender":99,"age":27,"expression":0,"beauty":79,"glass":0,"pitch":3,"yaw":0,"roll":4294967295}]
     */

    private int image_width;
    private int image_height;
    private List<FaceListBean> face_list;

    public int getImage_width() {
        return image_width;
    }

    public void setImage_width(int image_width) {
        this.image_width = image_width;
    }

    public int getImage_height() {
        return image_height;
    }

    public void setImage_height(int image_height) {
        this.image_height = image_height;
    }

    public List<FaceListBean> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<FaceListBean> face_list) {
        this.face_list = face_list;
    }


    @Override
    public String toString() {
        return "DataBean{" +
                "image_width=" + image_width +
                ", image_height=" + image_height +
                ", face_list=" + face_list +
                '}';
    }
}
