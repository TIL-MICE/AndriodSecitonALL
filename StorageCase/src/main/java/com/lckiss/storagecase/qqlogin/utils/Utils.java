package com.lckiss.storagecase.qqlogin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 17-6-19.
 */

public class Utils {
    //储存信息
    public static boolean saveUserInfo(Context context,String number,String passward){
        SharedPreferences sp=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("userName",number);
        editor.putString("userPwd",passward);
        editor.commit();
        return true;
    }
//获取储存的信息
    public static Map<String,String>getUserInfo(Context context){
        SharedPreferences sp=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String number=sp.getString("userName",null);
        String password=sp.getString("userPwd",null);
        Map<String,String> userMap=new HashMap<String,String>();
        userMap.put("number",number);
        userMap.put("password",password);
        return  userMap;
    }
}
