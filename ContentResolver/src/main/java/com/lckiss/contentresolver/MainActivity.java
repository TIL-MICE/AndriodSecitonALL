package com.lckiss.contentresolver;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lckiss.contentresolver.bean.SmsInfo;
import com.lckiss.contentresolver.utils.SmsUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//安卓6.0以上需要去系统中允许权限 否则崩溃
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Click(View v){
        //查询系统所有的短信uri
        Uri uri= Uri.parse("content://sms/");
        //获取contentResolver对象
        ContentResolver resolver=getContentResolver();
        //通过resolver对象查询系统短信
        Cursor cursor=resolver.query(uri,new String[]{"address","date","type","body"},null,null,null);
        List smsInfos=new ArrayList<>();

        while (cursor.moveToNext()){
            String address =cursor.getString(0);
            long date=cursor.getLong(1);
            int type=cursor.getInt(2);
            String body=cursor.getString(3);
            SmsInfo smsInfo=new SmsInfo(date,type,body,address);
            smsInfos.add(smsInfo);
        }
        cursor.close();
        SmsUtils.backUpSms(smsInfos,this);
    }

   public void  receiver(View v){
       Intent intent=new Intent(this,ReceiverSms.class);
       startActivity(intent);
   }
}
