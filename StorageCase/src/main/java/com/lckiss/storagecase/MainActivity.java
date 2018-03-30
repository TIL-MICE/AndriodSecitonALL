package com.lckiss.storagecase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lckiss.storagecase.qqlogin.QQLoginActivity;
import com.lckiss.storagecase.xmlweather.WeatherActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//天气案例
    public void Weather(View v){
        Intent intent=new Intent(this,WeatherActivity.class);
        startActivity(intent);
       // finish();
    }
//QQ登录案例
    public void QQLogin(View v){
        Intent intent=new Intent(this,QQLoginActivity.class);
        startActivity(intent);
    }
}
