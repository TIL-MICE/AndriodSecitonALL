package com.lckiss.activitycase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {
    private ItemInfo itemInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        //查找对应的控件
        TextView lifeTV=(TextView)findViewById(R.id.tv_life);
        TextView nameTV=(TextView)findViewById(R.id.item_name);
        TextView speedTV=(TextView)findViewById(R.id.tv_speed);
        TextView attackTV=(TextView)findViewById(R.id.tv_attack);
        //生成一个Item对象
        itemInfo=new ItemInfo("金剑",100,20,20);
        //设置Item条目的点击事件
        findViewById(R.id.rl).setOnClickListener(this);

        //将ItemInfo分别设置到各布局
        lifeTV.setText("生命值"+itemInfo.getLife());
        nameTV.setText(itemInfo.getName()+"");
        speedTV.setText("生命值"+itemInfo.getSpeed());
        attackTV.setText("生命值"+itemInfo.getAcctack());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl:
                Intent intent=new Intent();
                intent.putExtra("equipment",itemInfo);
                //返回数据，设置标志码，结束当前的activity
                setResult(2,intent);
                finish();
                break;
        }
    }
}
