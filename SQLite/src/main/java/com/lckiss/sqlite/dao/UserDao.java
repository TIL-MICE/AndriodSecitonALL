package com.lckiss.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.lckiss.sqlite.bean.User;

/**
 * Created by deng_ on 2017/4/5.
 */

public class UserDao {
    private MyDBHelper helper;

    public UserDao(Context context) {

        this.helper = new MyDBHelper(context);
    }

    public void add(User user){
        SQLiteDatabase db=helper.getWritableDatabase();
//        第一步：生成一个参数值集合对象：类型ContentValues,本质上就是键值对
//        ContentValues contentValues=new ContentValues();
//        contentValues.put("name",user.getName());
//        contentValues.put("number",user.getNumber());
//        long id=db.insert("users",null,contentValues);
//        //返回值：行号，流水编号，在这里返回的就是自动增长的字段值

        db.execSQL("insert into users (name,number) values(?,?)",
                new Object[]{user.getName(),user.getNumber()});
        db.close();
    }

    public int update(String name,String newName){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",newName);
        //返回受影响行数
        int number=db.update("users",contentValues,"name=?",new String[]{name});
        db.close();
        return number;
    }

    public int delete(User user){
        SQLiteDatabase db=helper.getWritableDatabase();
        //返回受影响行数
        int number=db.delete("users","name=?",new String[]{user.getName()});
        db.close();
        return number;
    }

    public List<User> findAll(){
        SQLiteDatabase db=helper.getReadableDatabase();
        List<User> users=new ArrayList<User>();
        //参数分别为：表名，查询列名，查询条件，条件值，分组条件，having条件，排序方式
        Cursor cursor=db.query("users",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String number=cursor.getString(cursor.getColumnIndex("number"));
            users.add(new User(name,number));
        }
        cursor.close();
        db.close();
        return users;
    }
}
