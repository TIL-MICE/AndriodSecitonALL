package com.lckiss.adaptercase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private ListView listView;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView=(ListView)findViewById(R.id.list_view);


//        String[] datas={"湖南","湖南","湖南","湖南","湖南","湖南","湖南","湖南","湖南"};
        final List<String> datas;
        datas=new ArrayList<>();
        for(int i=0;i<=15;i++){
            datas.add("宝宝"+i);
        }

        adapter=new ArrayAdapter<String>(this,R.layout.item,datas);
//        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"宝宝是"+(position+1)+"号,打你哟",Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               //长按移除指定位置
                datas.remove(position);
                //刷新识别器
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"你打死了宝宝"+(position+1),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
