package com.lckiss.sqlitelist.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by root on 17-6-21.
 * 初始化表
 */

public class MyHelper extends SQLiteOpenHelper {

    public MyHelper(Context context) {
        super(context, "product.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("info","SQL onCreate");

        db.execSQL("create table account(_id INTEGER PRIMARY KEY autoincrement,name VARCHAR(20),balance INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("info","onUpgrade");
    }
}
