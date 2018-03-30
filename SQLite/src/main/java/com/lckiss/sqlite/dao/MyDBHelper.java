package com.lckiss.sqlite.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by deng_ on 2017/4/5.
 * 自定义数据库工具类
 */

public class MyDBHelper extends SQLiteOpenHelper{

    public MyDBHelper(Context context){

        super(context,"student.db",null,5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(" +
                "id integer primary key autoincrement," +
                "name varchar(20)," +
                "number varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table users add account varchar(20)");
    }

}
