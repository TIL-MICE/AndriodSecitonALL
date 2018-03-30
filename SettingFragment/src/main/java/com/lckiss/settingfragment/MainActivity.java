package com.lckiss.settingfragment;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
private FragmentTransaction transaction;
    //设置文字
    private String[][] settingText ={ { "主题", "系统壁纸" }, { "云账户", "百度云账户" },
            { "通知", "通知栏推送" }, { "移动数据", "便携式WIFI热点" }, { "WLAN", "更多" },
            { "蓝牙", "可被发现" },{ "天气", "温度" }, { "通话音量", "媒体音量" },
            { "密码锁定", "定位服务" },{ "语言", "输入法设置" },  { "设置快捷手势", "触摸反馈" },
            { "设备名称", "存储" } };
    //设置图标
    private int[] settingicons= { R.drawable.theme, R.drawable.clound,
            R.drawable.notifycation, R.drawable.internet, R.drawable.wifi,
            R.drawable.bluetooth, R.drawable.wether, R.drawable.volume,
            R.drawable.gps, R.drawable.language, R.drawable.gesture,
            R.drawable.info };
    //获取图标数组的方法
    public int[] getIcons(){
        return settingicons;
    }
    //获取文字
    public String[][] getSettingText(){
        return settingText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建Fragment
        SettingListFragment fragment=new SettingListFragment();

        SettingiconFragment icFragment=new SettingiconFragment();
//获取事务
        transaction=getFragmentManager().beginTransaction();
        //添加Fragment
        transaction.replace(R.id.settingcontent,fragment);
        transaction.replace(R.id.settinglist,icFragment);
        //提交事务
        transaction.commit();


    }
}
