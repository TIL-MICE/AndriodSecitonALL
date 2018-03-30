package com.lckiss.fragmentcase;


import android.app.FragmentManager;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button one,two,three,four;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDefaultFragment();

        one=(Button)findViewById(R.id.one);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultFragment();
            }
        });

        two=(Button)findViewById(R.id.two);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTwoFragment();
            }
        });
    }

    public void setDefaultFragment(){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();

        transaction.replace(R.id.fl_content,new FragmentOne());
        transaction.commit();
    }

    public void setTwoFragment(){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction  transaction=fragmentManager.beginTransaction();

        transaction.replace(R.id.fl_content,new FragmentTwo());

        transaction.commit();

    }
}
