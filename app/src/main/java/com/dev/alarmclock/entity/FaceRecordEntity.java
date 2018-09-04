package com.dev.alarmclock.entity;

import android.graphics.Bitmap;

/**
 * Created by ${Estelle} on 2018/7/4.
 */

public class FaceRecordEntity {
    private String name;
    private String author;
    private int pages;
    private double price;
    private Bitmap picture;

    public FaceRecordEntity(String name, String author, int pages, double price, Bitmap picture) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.price = price;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}
