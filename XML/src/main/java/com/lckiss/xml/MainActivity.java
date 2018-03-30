package com.lckiss.xml;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<User> users;
    private List<User> outUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        users = new ArrayList<User>();

        outUsers = new ArrayList<User>();

        for (int i = 0; i < 3; i++) {
            users.add(new User(i, "张三" + i, 80 - i));
        }
    }

    public void saveObject(View v) {
        try {
            XmlSerializer xmlSerializer = Xml.newSerializer();
            FileOutputStream os = openFileOutput("lckiss.xml", MODE_APPEND);
            xmlSerializer.setOutput(os, "UTF-8");
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "users");
            int count = 0;
            for (User user : users) {
                xmlSerializer.startTag(null, "user");

                xmlSerializer.attribute(null, "id", user.getId().toString());
                xmlSerializer.attribute(null, "name", user.getName());
                xmlSerializer.attribute(null, "score", user.getScore().toString());

                xmlSerializer.endTag(null, "user");
                count++;
            }
            xmlSerializer.endTag(null, "users");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            os.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readXml(View v) {

        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            FileInputStream is = openFileInput("lckiss.xml");
            xmlPullParser.setInput(is, "UTF-8");

            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = xmlPullParser.getName();
                        if ("user".equals(tagName)) {
                            int count = xmlPullParser.getAttributeCount();
                            User user = new User();
                            for (int i = 0; i < count; i++) {
                                String attrName = xmlPullParser.getAttributeName(i);
                                String attrValue = xmlPullParser.getAttributeValue(i);
                                if ("id".equals(attrName)) {
                                    user.setId(Integer.valueOf(attrValue));
                                }else
                                if ("name".equals(attrName)) {
                                    user.setName(attrValue);

                                }else
                                if ("score".equals(attrName)) {
                                    user.setScore(Integer.valueOf(attrValue));
                                }
                            }
                            outUsers.add(user);
                        }
                        Toast.makeText(this, "读取成功", Toast.LENGTH_LONG).show();
                        break;

                        case XmlPullParser.END_TAG:{
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
            is.close();
            for (User user : outUsers) {
                Log.i("myinfo : ", "id :" + user.getId() + " Name :" + user.getName() + " Score :" + user.getScore());
               // Toast.makeText(this, "id :" + user.getId() + "Name :" + user.getName() + "Score :" + user.getScore(), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
