package com.lckiss.storagecase.qqlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lckiss.storagecase.R;
import com.lckiss.storagecase.qqlogin.utils.Utils;

import java.util.Map;

public class QQLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNumber, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqlogin);
        //初始化控件
        initView();
        //获取存储的信息 有则输出到屏幕上
        Map<String, String> userInfo = Utils.getUserInfo(this);
        if (userInfo != null) {
            etNumber.setText(userInfo.get("number"));
            etPassword.setText(userInfo.get("password"));
        }

    }

    private void initView() {
        etNumber = (EditText) findViewById(R.id.ed_number);
        etPassword = (EditText) findViewById(R.id.ed_password);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String number=etNumber.getText().toString().trim();
        String password =etPassword.getText().toString();
        if (number.equals("")){
            Toast.makeText(this,"账号不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals("")){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
        Log.i("Info","记住密码:"+number+"---"+password);
        //保存信息到偏好存储
        boolean isSaveSuccess=Utils.saveUserInfo(this,number,password);
        if(isSaveSuccess){
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
        }

    }
}
