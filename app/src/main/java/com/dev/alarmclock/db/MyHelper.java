package com.dev.alarmclock.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017/8/10.
 */
public class MyHelper extends SQLiteOpenHelper{
    public MyHelper(Context context) {
        super(context, "MyDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//创建表
        db.execSQL("create table if not exists student(id Integer primary key AUTOINCREMENT,name varchar,age Integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
