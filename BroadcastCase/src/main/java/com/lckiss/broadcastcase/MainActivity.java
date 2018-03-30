package com.lckiss.broadcastcase;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_number;
    private SharedPreferences sp;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_number = (EditText) findViewById(R.id.et_number);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        et_number.setText(sp.getString("ipnumber", ""));
    }

    public void click(View v) {
        //判断是否获取了权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            Do();
        } else {
            Do();
        }
    }

    public void Do() {
        //将输入的IP存入SharedPreferences
        String ipnumber = et_number.getText().toString().trim();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ipnumber", ipnumber);
        editor.commit();
        Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
    }

    //自定义广播
    public void Send(View v){
        Intent intent=new Intent();
        //定义广播的事件类型
        intent.setAction("toast");
        sendBroadcast(intent);
        Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
    }

    //有序广播
    public void orderBroadcast(View v){
        Intent intent=new Intent();
        //定义广播的事件类型
        intent.setAction("toast");
        sendOrderedBroadcast(intent,null);
        Toast.makeText(this, "发送有序成功", Toast.LENGTH_SHORT).show();
    }


}
