package com.lckiss.intentcase;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.maintext);
    }

    //显式Intent
    public void MyOnClick1(View v) {
        Intent intent = new Intent();
        //包名加完整类名
        intent.setClassName("com.lckiss.intentcase", "com.lckiss.intentcase.IntentOne.SecondActivity");
        startActivity(intent);

    }

    //通过Action来查找组件
    public void MyOnClick2(View v) {
        Intent intent = new Intent();
        //完整类名
        intent.setAction("Soft");
        startActivity(intent);

    }

    //通过Action和Category来查找组件
    public void MyOnClick3(View v) {
        Intent intent = new Intent();
        //完整类名
        intent.setAction("Soft");
        intent.addCategory("Third");
        startActivity(intent);

    }

    //多个目标组件满足条件
    public void MyOnClick4(View v) {
        Intent intent = new Intent();
        //完整类名
        intent.setAction("Soft");
        startActivity(intent);

    }

    public void MyOnClick5(View v) {
//调用浏览器
        //       Uri webViewUri=Uri.parse("http://www.baidu.com");
        //     Intent intent=new Intent(Intent.ACTION_VIEW,webViewUri);
        //   startActivity(intent);
        //调用电
        Uri diaUri = Uri.parse("tel:10086");
        Intent intent = new Intent(Intent.ACTION_DIAL, diaUri);
        startActivity(intent);

    }

    public void MyOnClick6(View v) {

        Intent intent = new Intent();
        intent.setAction("Soft1");
        Bundle bundle = new Bundle();
        //0传递普通数据
        bundle.putInt("type",0);
        bundle.putInt("ID", 1);
        bundle.putString("Name", "Jack");
        intent.putExtras(bundle);
        //普通意图
//        startActivity(intent);
        //带返回的意图
        startActivityForResult(intent, 1);
    }

    public void MyOnClick7(View v) {

        Intent intent = new Intent();
        intent.setAction("Soft1");
        Bundle bundle = new Bundle();
        //1传递类数据
        bundle.putInt("type",1);
        User user = new User(0, "Jack");
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        //普通意图
//        startActivity(intent);
        //带返回的意图
        startActivityForResult(intent, 1);
    }


    @Override
    public void onActivityResult(int RequsetCode, int resultCode, Intent data) {
        super.onActivityResult(RequsetCode, resultCode, data);
        if (resultCode == 1) {
            String dataR = data.getStringExtra("extra_data");
            textView.setText(dataR);
        }


    }

}
