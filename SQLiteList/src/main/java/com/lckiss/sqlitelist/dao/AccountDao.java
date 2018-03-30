package com.lckiss.sqlitelist.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;


import com.lckiss.sqlitelist.bean.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-6-22.
 */

public class AccountDao {
    private MyHelper myHelper;
    public AccountDao (Context context){
        //创建dao时创建helper
        myHelper=new MyHelper(context);
    }
    public void insert(Account account){
        //获取数据库对象
        SQLiteDatabase db=myHelper.getWritableDatabase();

        //用来装载要插入的数据的Map<列名，列的值>
        ContentValues values=new ContentValues();

        values.put("name",account.getName());
        values.put("balance",account.getBalance());
        //向account表插入数据values
        long id=db.insert("account",null,values);
        account.setId(id);
        db.close();
    }

    //根据id删除数据
    public int delete(long id){
        SQLiteDatabase db=myHelper.getWritableDatabase();
        //按条件删除指定表中的数据，返回受影响的行数
        int count=db.delete("account","_id=?",new String[]{id+""});
        db.close();
        return count;
    }

    public void deleteAll(){
        SQLiteDatabase db=myHelper.getWritableDatabase();
        db.execSQL("delete from account");//清空数据
        db.execSQL("update sqlite_sequence SET seq = 0 where name ='account';"); //自增长ID为0
    }
    public int update(Account account){
        SQLiteDatabase db=myHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",account.getName());
        values.put("balance",account.getBalance());
        int count=db.update("account",values,"_id=?",new String[]{account.getId()+""});
        db.close();
        return count;
    }
    public List<Account> queryAll(){
        SQLiteDatabase db=myHelper.getWritableDatabase();
        Cursor c=db.query("account",null,null,null,null,null,"balance DESC");
        List<Account> list=new ArrayList<Account>();
        while (c.moveToNext())
        {
            //可根根据列名获取索引
            long id=c.getLong(c.getColumnIndex("_id"));
            String name=c.getString(1);
            int balance=c.getInt(2);
            list.add(new Account(id,name,balance));
        }
        c.close();
        db.close();
        return list;

    }
}
