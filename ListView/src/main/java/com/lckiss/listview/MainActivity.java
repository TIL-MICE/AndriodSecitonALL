package com.lckiss.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
//    private List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

    private List<Music> datas = new ArrayList<Music>();

    //简单listview适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);


        Music one=new Music("1",R.mipmap.ic_launcher);
        Music two=new Music("2",R.mipmap.ic_launcher);
        Music three=new Music("3",R.mipmap.ic_launcher);
        datas.add(one);
        datas.add(two);
//        datas.add(three);

//简单适配器
//        取值
//         getDatas();
//        来源去向
//        String[] from = {"musicImg", "musicName"};
//        int[] to = {R.id.musicImg, R.id.musicName};
//
//        //新建一个简单适配器
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, datas, R.layout.item, from, to);
//        listView.setAdapter(simpleAdapter);


        MyAdapter myAdapter=new MyAdapter(this,datas);
        listView.setAdapter(myAdapter);
    }

//    private void getDatas() {
//        for (int i = 0; i < 10; i++) {
//            HashMap<String, Object> data = new HashMap<String, Object>();
//            data.put("musicImg", R.mipmap.ic_launcher);
//            data.put("musicName", "NAME" + i);
//            datas.add(data);
//        }
//
//    }
}
