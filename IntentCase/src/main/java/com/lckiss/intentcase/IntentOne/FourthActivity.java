package com.lckiss.intentcase.IntentOne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lckiss.intentcase.R;
import com.lckiss.intentcase.User;

import java.security.PublicKey;

public class FourthActivity extends AppCompatActivity {

    TextView textView1,textView2;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        textView1=(TextView)findViewById(R.id.text1);
        textView2=(TextView)findViewById(R.id.text2);
        button=(Button)findViewById(R.id.button);


        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        //判断按钮
        if(bundle.getInt("type")==0){
            getData1();
            textView2.setVisibility(View.INVISIBLE);
        }else{
            getData2();
            textView1.setVisibility(View.INVISIBLE);
        }




    }

    public void returnRS(String res){
        Intent intent=new Intent();
        intent.putExtra("extra_data","Hello返回数据"+res);
        setResult(1,intent);
        finish();
    }

    public void getData1(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        int id=bundle.getInt("ID");
        String name=bundle.getString("Name");
        textView1.setText("普通数据传递"+id+"    "+name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnRS("Normal");
            }
        });

    }

    public void getData2(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        User user=(User)bundle.getSerializable("user");
        textView2.setText("类数据传递"+user.getId()+"    "+user.getName());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnRS("Class");
            }
        });

    }
}
