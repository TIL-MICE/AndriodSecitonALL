package com.lckiss.contentresolver.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;


import com.lckiss.contentresolver.bean.SmsInfo;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by root on 17-6-23.
 */

public class SmsUtils {
    public static void backUpSms(List<SmsInfo> smsInfos, Context context) {
        try {

            XmlSerializer serializer = Xml.newSerializer();
            File file = new File(Environment.getExternalStorageDirectory(), "sms.xml");
            FileOutputStream os = new FileOutputStream(file);
            //初始化文件以及指定编码
            serializer.setOutput(os, "utf-8");
            serializer.startDocument("utf-8", true);
            //构建根节点
            serializer.startTag(null, "smss");
            for (SmsInfo info : smsInfos) {
                //父节点
                serializer.startTag(null, "sms");
                serializer.attribute(null, "id", info.getId() + "");
                //子节点body
                serializer.startTag(null, "body");
                serializer.text(info.getBody());
                serializer.endTag(null, "body");
                //address
                serializer.startTag(null, "address");
                serializer.text(info.getAddress());
                serializer.endTag(null, "address");
//type
                serializer.startTag(null, "type");
                serializer.text(info.getType() + "");
                serializer.endTag(null, "type");
//date
                serializer.startTag(null, "date");
                serializer.text(info.getDate() + "");
                serializer.endTag(null, "date");
//父节点结束标签
                serializer.endTag(null, "sms");
            }
            serializer.endTag(null,"smss");
            serializer.endDocument();
            os.close();
            Toast.makeText(context,"备份成功",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "备份失败", Toast.LENGTH_SHORT).show();
        }

    }
}
