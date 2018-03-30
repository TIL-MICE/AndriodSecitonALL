package com.lckiss.activitycase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DataActivity extends AppCompatActivity {
    TextView tv_name, tv_pwd, tv_sex;
    Button sendBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//返回按钮
        sendBack = (Button) findViewById(R.id.sendBack);
//显示控件
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_pwd = (TextView) findViewById(R.id.tv_pwd);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        //获取Intent并进行
        Intent intent = getIntent();
        //获取Intent中的数据
        String name = intent.getStringExtra("name");
        String pwd = intent.getStringExtra("pwd");
        String sex = intent.getStringExtra("sex");
        //设置获取到的数据进行显示
        tv_name.setText("用户名：" + name);
        tv_pwd.setText("密码：" + pwd);
        tv_sex.setText("性别：" + sex);

        sendBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent();
                intentBack.putExtra("BackData", "这是返回数据");
                //不同的result标志码 和另一个案例进行区分
                setResult(1, intentBack);
                finish();
            }
        });
    }

}
