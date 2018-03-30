package com.lckiss.storagecase.xmlweather;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.lckiss.storagecase.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {
    /*
    * 选择的城市 天气 温度 风力 pm值
    * */
    private TextView selected_city, selected_weather, selected_temp, selected_wind, selected_pm;
    //天气的Map 用于存储天气信息
    private Map<String, String> map;

    private List<Map<String, String>> list;

    private String temp, weather, name, pm, wind;

    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //控件查找
        selected_city = (TextView) findViewById(R.id.selected_city);
        selected_weather = (TextView) findViewById(R.id.selected_weather);
        selected_temp = (TextView) findViewById(R.id.temp);
        selected_wind = (TextView) findViewById(R.id.wind);
        selected_pm = (TextView) findViewById(R.id.pm);
        icon = (ImageView) findViewById(R.id.colorPic);
//设置点击事件
        findViewById(R.id.city_bj).setOnClickListener(this);
        findViewById(R.id.city_jl).setOnClickListener(this);
        findViewById(R.id.city_sh).setOnClickListener(this);

        try {
            //通过WeatherService调用asset中的文件解析xml数据
            List<WeatherInfo> infos = WeatherService.getWeatherInfo(getAssets().open("weather.xml"));
            //将数据存储在list中
            list = new ArrayList<Map<String, String>>();
            for (WeatherInfo info : infos) {
                map = new HashMap<String, String>();
                map.put("temp", info.getTemp());
                map.put("weather", info.getWeather());
                map.put("name", info.getName());
                map.put("pm", info.getPm());
                map.put("wind", info.getWind());
                list.add(map);

            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "解析失败", Toast.LENGTH_SHORT).show();
        }
        //默认显示的城市编号是1，并设置图片的背景色 以用于区分
        getMap(1, 255);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //四川
            case R.id.city_sh:
                getMap(0, 11);
                break;
            //北京
            case R.id.city_bj:
                getMap(1, 122);
                break;
            //吉林
            case R.id.city_jl:
                getMap(2, 212);
                break;
        }
    }

    /*
    *城市id 天气图片背景色
    * */
    private void getMap(int number, int color) {
        //拿到List中的Map集合
        Map<String, String> bjMap = list.get(number);
        temp = bjMap.get("temp");
        weather = bjMap.get("weather");
        wind = bjMap.get("wind");
        name = bjMap.get("name");
        pm = bjMap.get("pm");
//设置文本 颜色
        selected_city.setText(name);
        selected_weather.setText(weather);
        selected_temp.setText(temp);
        selected_pm.setText(pm);
        selected_wind.setText(wind);

        icon.setBackgroundColor(Color.rgb(color, 0, 0));
    }
}
