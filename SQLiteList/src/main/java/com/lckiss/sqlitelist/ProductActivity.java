package com.lckiss.sqlitelist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lckiss.sqlitelist.bean.Account;
import com.lckiss.sqlitelist.dao.AccountDao;

import java.util.List;

public class ProductActivity extends AppCompatActivity {
    //需要适配的数据集合
    private List<Account> list;
    //数据库增删改查操作类
    private AccountDao dao;

    private EditText nameET;
    private EditText banlanceET;

    private MyAdapter adapter;
    private ListView accountLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
//控件初始化
        initView();
       //查询所有数据
        dao=new AccountDao(this);
        list=dao.queryAll();
        //将数据设置到listview
        adapter=new MyAdapter();
        accountLV.setAdapter(adapter);
    }

    private void initView(){
        accountLV=(ListView)findViewById(R.id.accountLV);
        nameET=(EditText) findViewById(R.id.nameET);
        banlanceET=(EditText) findViewById(R.id.balanceET);
        accountLV.setOnItemClickListener(new MyOnItemClickListener());
    }

    public void add(View v){
        String name=nameET.getText().toString().trim();
        String balance=banlanceET.getText().toString().trim();
        //三目运算符
        Account a=new Account(name,balance.equals("")?0:Integer.parseInt(balance));
        dao.insert(a);
        list.add(a);
//        数据库改动后执行通知
        adapter.notifyDataSetChanged();
        //设置当前选中的数据条目 比如如果只能显示10条，当添加第11条时，将第1条划出屏外
        accountLV.setSelection(accountLV.getCount()-1);

        nameET.setText("");
        banlanceET.setText("");

    }


    //删除操作
    public void del(View v){
        list.clear();
        dao.deleteAll();
        adapter.notifyDataSetChanged();
       //设置文本为空
        nameET.setText("");
        banlanceET.setText("");

    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item=convertView!=null?convertView:View.inflate(getApplicationContext(),R.layout.product_item,null);
            TextView idTV=(TextView)item.findViewById(R.id.idTV);
            TextView nameTV=(TextView)item.findViewById(R.id.nameTV);
            TextView balanceTV=(TextView)item.findViewById(R.id.balanceTV);
            //根据当前位置获取Account对象
            final Account a=list.get(position);
            //放入数据
            idTV.setText(a.getId()+"");
            nameTV.setText(a.getName());
            balanceTV.setText(a.getBalance()+"");

            ImageView upTV=(ImageView)item.findViewById(R.id.upTV);
            ImageView downTV=(ImageView)item.findViewById(R.id.downTV);
            ImageView deleteTV=(ImageView)item.findViewById(R.id.deleteIV);
            //向上箭头
            upTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a.setBalance(a.getBalance()+2);
                    notifyDataSetChanged();
                    dao.update(a);
                }
            });
            //下
            downTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a.setBalance(a.getBalance()-1);
                    notifyDataSetChanged();
                    dao.update(a);
                }
            });
            //删除图片
            deleteTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除前弹出对话框
                    DialogInterface.OnClickListener listener=new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            list.remove(a);
                            dao.delete(a.getId());
                            notifyDataSetChanged();
                        }
                    };
                    //创建对话框
                    AlertDialog.Builder builder=new AlertDialog.Builder(ProductActivity.this);
                    builder.setTitle("确认删除吗？");
                    //确认按钮 第二个参数为调用的函数
                    builder.setPositiveButton("确定",listener);
                    builder.setNegativeButton("取消",null);
                    builder.show();
                }
            });
           return item;
        }
    }

    //自定义一个item点击事件
    private class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //获取点击位置上的数据
            Account a=(Account)parent.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(),a.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
