package com.lckiss.storagecase.xmlweather;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-6-19.
 */

public class WeatherService {
//静态类 用于解析xml
    public static  List<WeatherInfo> getWeatherInfo(InputStream is) throws Exception{
//新建一个parser对象
        XmlPullParser parser= Xml.newPullParser();
 //设置编码
        parser.setInput(is,"UTF-8");
//新建一个天气的List并置空
        List<WeatherInfo> weatherInfos=null;
//新建一个空的天气信息对象
        WeatherInfo weatherInfo=null;
//获取事件类型
        int type =parser.getEventType();
//匹配结束标签
        while (type!=XmlPullParser.END_DOCUMENT){
            //匹配开始结束标签 如果是大标签infos 就新建arrayList 如果是city就新建weather对象,其他的就进行weather信息的存储
            switch (type){
                case XmlPullParser.START_TAG:
                if ("infos".equals(parser.getName())) {
                    weatherInfos=new ArrayList<WeatherInfo>();
                }else if ("city".equals(parser.getName())){
                    weatherInfo=new WeatherInfo();
                    String idStr =parser.getAttributeValue(0);
                    weatherInfo.setId(Integer.parseInt(idStr));
                }else if ("temp".equals(parser.getName())){
                    String temp=parser.nextText();
                    weatherInfo.setTemp(temp);
                }else if("weather".equals(parser.getName())){
                    String weather=parser.nextText();
                    weatherInfo.setWeather(weather);
                }else
                    if ("name".equals(parser.getName())){
                        String name=parser.nextText();
                        weatherInfo.setName(name);
                    }else if ("pm".equals(parser.getName())){
                        String pm=parser.nextText();
                        weatherInfo.setPm(pm);
                    }else if ("wind".equals(parser.getName())){
                        String wind=parser.nextText();
                        weatherInfo.setWind(wind);
                    }
                break;
                case XmlPullParser.END_TAG:
                    //一次city循环结束后 添加到 arrayList数组中
                    if ("city".equals(parser.getName())){
                        weatherInfos.add(weatherInfo);
                        weatherInfo=null;
                    }
                    break;
            }
            type=parser.next();
        }

        return weatherInfos;
    }
}
