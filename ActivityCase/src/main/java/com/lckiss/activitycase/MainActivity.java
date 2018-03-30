package com.lckiss.activitycase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button openCamera, send;
    private TextView name, pwd;
    private RadioButton male;

    private ProgressBar progressBarLife, progressBarSpeed, progressBarAttack;
    private TextView Life_text, Speed_text, Attack_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //打开相机按钮
        openCamera = (Button) findViewById(R.id.openCamera);
        //打开相机按钮的点击事件
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //指定意图
                intent.setAction("android.media.action.IMAGE_CAPTURE");
                //添加分类
                intent.addCategory("android.intent.category.DEFAULT");
                //使用意图开启activity
                startActivity(intent);
            }
        });

        //案例二 传递数据案例内容
        name = (TextView) findViewById(R.id.name);
        pwd = (TextView) findViewById(R.id.pwd);
        male = (RadioButton) findViewById(R.id.male);
        //传送按钮
        send = (Button) findViewById(R.id.send);
        //传送按钮的点击事件
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递数据
                passData();
            }
        });

        //案例三 回传数据案例
        Life_text = (TextView) findViewById(R.id.life_text);
        Speed_text = (TextView) findViewById(R.id.Speed_text);
        Attack_text = (TextView) findViewById(R.id.Attack_text);
        //初始化进度条
        initProgress();

    }

    //跳转intent案例
    public void click(View v) {
        //跳转到shopActivity
        Intent intent = new Intent(this, ShopActivity.class);
        //设为带返回结果的启动方式 标志码为2 和其他案例进行区分
        startActivityForResult(intent, 2);
    }

    public void passData() {
        //跳转到接收数据的DataActivity
        Intent intent = new Intent(this, DataActivity.class);
        //将数据传递到另外一个Activity 参数一：标识名 参数二：内容
        intent.putExtra("name", name.getText().toString().trim());
        intent.putExtra("pwd", pwd.getText().toString().trim());
        String str = "";
        if (male.isChecked()) {
            str = "男";
        } else {
            str = "女";
        }
        intent.putExtra("sex", str);
        //startActivity(intent);
        //带返回的启动方式
        startActivityForResult(intent, 1);
    }

    public void initProgress() {
        //找到3个进度条
        progressBarLife = (ProgressBar) findViewById(R.id.progressBarLife);
        progressBarSpeed = (ProgressBar) findViewById(R.id.progressBarSpeed);
        progressBarAttack = (ProgressBar) findViewById(R.id.progressBarAttack);
        //分别设置最大值
        progressBarLife.setMax(1000);
        progressBarSpeed.setMax(1000);
        progressBarAttack.setMax(1000);
    }

    private void updateProgress(ItemInfo info) {
        //拿到已存在的数据
        int progressLife = progressBarLife.getProgress();
        int progressSpeed = progressBarSpeed.getProgress();
        int progressAttack = progressBarAttack.getProgress();
        //数据叠加
        progressBarLife.setProgress(progressLife + info.getLife());
        progressBarSpeed.setProgress(progressSpeed + info.getSpeed());
        progressBarAttack.setProgress(progressAttack + info.getAcctack());
        //更新进度条的文字
        Life_text.setText(progressBarLife.getProgress() + "");
        Speed_text.setText(progressBarSpeed.getProgress() + "");
        Attack_text.setText(progressBarAttack.getProgress() + "");
    }

    //返回值的获取
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //根据resultcode进行判断，区分不同案例返回的
        if (resultCode == 1) {
            String backData = data.getStringExtra("BackData");
//            设置返回数据
            name.setText(backData + "");
            Log.i("info", backData + "");
        }
        //更为严谨的操作，如果返回不为空才赋值
        if (data != null) {
            if (resultCode == 2) {
                if (requestCode == 2) {
                    ItemInfo itemInfo = (ItemInfo) data.getSerializableExtra("equipment");
                    //拿到返回的数据，并更新进度条
                    updateProgress(itemInfo);
                }
            }
        }
    }


}
