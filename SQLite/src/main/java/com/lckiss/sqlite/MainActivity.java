package com.lckiss.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.lckiss.sqlite.bean.User;
import com.lckiss.sqlite.dao.UserDao;

public class MainActivity extends AppCompatActivity {

    private List<User> users;
    private UserDao userDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        users=new ArrayList<User>();
    }

    public void createdb(View view){
        userDao=new UserDao(this);
        //this：表示数据db文件存放在默认路径
    }
    public void insertdb(View view){
        for(int i=0;i<3;i++){
            User user=new User("张三"+i,"14010"+i);
            userDao.add(user);
            users.add(user);
        }
    }
    public void updatedb(View view){
        for(int i=0;i<3;i++){
            //改之前的姓名
            String name=users.get(i).getName();
            users.get(i).setName("李四"+i);
            userDao.update(name,users.get(i).getName());
        }
    }
    public void deldb(View view){
        users.remove(1);
        userDao.delete(users.get(1));
    }
    public void querydb(View view){
        users.clear();
        users=userDao.findAll();
        for(User user:users){
            Log.i("myinfo","name:"+user.getName()+"   number:"+user.getNumber());
        }
    }
}
