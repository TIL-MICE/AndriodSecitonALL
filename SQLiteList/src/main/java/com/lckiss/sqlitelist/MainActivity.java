package com.lckiss.sqlitelist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    //显示的应用名称 应用icons
    private String[] names = {"one", "Two", "Three", "Four", "Five", "Six"};
    private int[] icons = {R.drawable.acorn, R.drawable.b612, R.drawable.facetune, R.drawable.linguee, R.drawable.seeyou, R.drawable.yqg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取控件
        listView = (ListView) findViewById(R.id.lv);
//获取一个BaseAdapter,并将其设置到listview中
        MyBaseAdapter myBaseAdapter = new MyBaseAdapter();
        listView.setAdapter(myBaseAdapter);

    }

    //继承BaseAdapter的自定义基本适配器
    class MyBaseAdapter extends BaseAdapter {

        //Item总数
        @Override
        public int getCount() {
            return names.length;
        }

        //得到Item代表的对象
        @Override
        public Object getItem(int position) {
            return names[position];
        }

        //得到Item的id
        @Override
        public long getItemId(int position) {
            return position;
        }

        //得到Item的View视图
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this, R.layout.listview_item, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_list);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            //根据位置设置文本 图片
            textView.setText(names[position]);
            imageView.setBackgroundResource(icons[position]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "我是条目", Toast.LENGTH_SHORT).show();
                    //点击跳转到另外一个 产品案例
                    Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                    startActivity(intent);
                }
            });
            return view;
        }
    }
}

