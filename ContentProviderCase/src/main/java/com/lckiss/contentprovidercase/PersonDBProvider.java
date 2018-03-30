package com.lckiss.contentprovidercase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by root on 17-6-22.
 */

public class PersonDBProvider extends ContentProvider {
    //定义一个uri的匹配器，用于匹配uri，如果路径不满足条件，返回-1
    private static UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
    //对应操作的返回码
    private static final int INSERT=1;
    private static final int DELETE=2;
    private static final int UPDATE=3;
    private static final int QUERY=4;
    private static final int QUERYONE=5;
    private static final int DELETEALL=6;

    private PersonSQLiteOpenHelper helper;

    static {
        //一组匹配规则
        matcher.addURI("com.lckiss.personprovider","insert",INSERT);
        matcher.addURI("com.lckiss.personprovider","delete",DELETE);
        matcher.addURI("com.lckiss.personprovider","update",UPDATE);
        matcher.addURI("com.lckiss.personprovider","query",QUERY);
        matcher.addURI("com.lckiss.personprovider","deleteall",DELETEALL);
        //这里的#（通配符）是指凡是符合“query/”皆返回QUERYONE的返回码
        matcher.addURI("com.lckiss.personprovider","query/#",QUERYONE);
    }
    //当内容创建者被创建时候调用适合数据的初始化
    @Override
    public boolean onCreate() {
        helper=new PersonSQLiteOpenHelper(getContext());
        return false;
    }

    //查询数据操作
    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        if (matcher.match(uri)==QUERY){
            SQLiteDatabase db=helper.getReadableDatabase();
            Cursor cursor=db.query("person",projection,selection,selectionArgs,null,null,sortOrder);
            return cursor;
        }else   if (matcher.match(uri)==QUERYONE){
            long id= ContentUris.parseId(uri);
            SQLiteDatabase db=helper.getReadableDatabase();
            Cursor cursor=db.query("person",projection,"id=?",new String[]{id+""},null,null,sortOrder);
            return cursor;
        }else {
            throw new IllegalArgumentException("路径不匹配，不能执行查询操作");
        }
    }

    //获取当前Uri的数据类型
    @Override
    public String getType(Uri uri) {
        if (matcher.match(uri)==QUERY){
            return "vnd.andriod.cursor.dir/person";
        }else if (matcher.match(uri)==QUERYONE){
            return "vnd.andriod.curson.item/person";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (matcher.match(uri)==INSERT){
            SQLiteDatabase db=helper.getWritableDatabase();
            db.insert("person",null,values);
            return Uri.parse("true");
        }else {
            throw new IllegalArgumentException("路径不匹配，不能执行插入操作");
        }

    }


    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        if (matcher.match(uri)==DELETE){
            SQLiteDatabase db=helper.getWritableDatabase();
            db.delete("person",selection,selectionArgs);
        }else
            if (matcher.match(uri)==DELETEALL){
            SQLiteDatabase db=helper.getWritableDatabase();
            db.execSQL("delete from person");
                Log.i("info", "delete: 已清空");
        }else {
            throw new IllegalArgumentException("路径不匹配，不能执行删除操作");
        }
        return 0;
    }

    @Override
    public int update( Uri uri,  ContentValues contentValues,  String selection,  String[] selectionArgs) {
        if (matcher.match(uri)==UPDATE){
            SQLiteDatabase db=helper.getWritableDatabase();
            db.update("person",contentValues,selection,selectionArgs);
        }else {
            throw new IllegalArgumentException("路径不匹配，不能执行修改操作");
        }

        return 0;
    }
}
