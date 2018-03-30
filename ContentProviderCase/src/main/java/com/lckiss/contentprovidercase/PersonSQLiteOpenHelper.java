package com.lckiss.contentprovidercase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by root on 17-6-22.
 */

public class PersonSQLiteOpenHelper extends SQLiteOpenHelper{
    public PersonSQLiteOpenHelper(Context context) {
        super(context, "person.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table person(id integer primary key autoincrement,name varchar(20),number varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("info","数据库的版本升级啦");
    }
}
