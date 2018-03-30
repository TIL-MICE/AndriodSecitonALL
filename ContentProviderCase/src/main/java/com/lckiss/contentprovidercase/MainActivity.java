package com.lckiss.contentprovidercase;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.lckiss.contentprovidercase.bean.Person;
import com.lckiss.contentprovidercase.dao.PersonDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private MyAdapter myAdapter;
    private List<Person> persons;
    private  ContentResolver contentResolver;

    private static final String TAG = "MainActivity";

    //创建一个Handler对象用于线程通信
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //接收到数据查询完毕的消息
                case 100:
                    //UI线程适配ListView
                    myAdapter=new MyAdapter();
                    lv.setAdapter(myAdapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);

        new Thread() {
            @Override
            public void run() {
                adddata();
                //查询已有数据
                getPersons();
                if (persons.size() > 0) {
                    handler.sendEmptyMessage(100);
                }

            }
        }.start();
    }



    //向表中插入10条数据
    public void adddata() {
        PersonDao dao = new PersonDao(this);
        long number = 885900001;
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            dao.add("wangwu" + i, Long.toString(number + i), random.nextInt(5000));
        }

    }

    //利用ContentResolver对象查询本应用程序使用ContentProvider暴露的数据
    private void getPersons() {
        //首先获取查询的uri
        String path = "content://com.lckiss.personprovider/query";
        Uri uri = Uri.parse(path);
        //获取对象
        contentResolver = getContentResolver();
        //拿到一个cursor的对象
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        persons = new ArrayList<Person>();
        //cursor为空立即结束这个方法
        if (cursor == null) {
            return;
        }
        //否则添加到persons的list中
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            Person p = new Person(id, name, number);
            persons.add(p);
        }
        cursor.close();
    }

    public void deleteAll(View v){
        //首先获取查询的uri
        String path = "content://com.lckiss.personprovider/deleteall";
        Uri uri = Uri.parse(path);
        contentResolver.delete(uri,null,null);
        persons.clear();
        myAdapter.notifyDataSetChanged();
    }

    public void updateListView(View v){
        //新插入一条数据
        insertPerson();
        getPersons();
        myAdapter.notifyDataSetChanged();
        //增加一条移除一条
        lv.setSelection(lv.getCount()-1);
    }

    private void insertPerson() {
        String insert = "content://com.lckiss.personprovider/insert";
        Uri uriInsert = Uri.parse(insert);

        ContentValues values=new ContentValues();
        values.put("name","xxxx");
        values.put("number","111111");
        if (contentResolver.insert(uriInsert,values).toString().equals("true")){
            Log.i(TAG, "insertPerson: 插入成功");
        }else {
            Log.i(TAG, "insertPerson: 插入失败");
        }

    }

    //适配器
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Object getItem(int i) {
            return persons.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View contentView, ViewGroup viewGroup) {
            //重新获取数据
            getPersons();

            View view = View.inflate(MainActivity.this, R.layout.list_item, null);
            //Item的点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "测试" + position, Toast.LENGTH_SHORT).show();
                }
            });

            //设置Item的信息
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_phone = view.findViewById(R.id.tv_phone);
            //得到某个位置的person对象
            Person person = persons.get(position);
            tv_name.setText("姓名：" + person.getName());
            tv_phone.setText("电话：" + person.getNumber());


            return view;
        }
    }
}
