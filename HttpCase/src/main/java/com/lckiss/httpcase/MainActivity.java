package com.lckiss.httpcase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    protected static final int CHANGE_UI = 1;
    protected static final int ERROR = 2;
    private EditText et_path;
    private ImageView iv;

    //猪线程创建消息处理器
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CHANGE_UI) {
                Bitmap bitmap = (Bitmap) msg.obj;
                iv.setImageBitmap(bitmap);
            } else if (msg.what == ERROR) {
                Toast.makeText(MainActivity.this, "显示图片错误", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_path = (EditText) findViewById(R.id.et_path);
        iv = (ImageView) findViewById(R.id.iv);

    }

    public void click(View v) {
        final String path = et_path.getText().toString().trim();
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(this, "图片路径不能为空", Toast.LENGTH_SHORT);
        } else {
            //子线程请求网络，安卓4.0之后访问网络不能放在主线程中
            new Thread() {
                private HttpURLConnection conn;
                private Bitmap bitmap;

                public void run() {
                    try {
                        //url对象
                        URL url = new URL(path);
                        //根据url发送http请求
                        conn = (HttpURLConnection) url.openConnection();
                        //设置请求方式
                        conn.setRequestMethod("GET");
                        //设置超时时间
                        conn.setConnectTimeout(5000);
                        //设置请求头User-Agent的浏览器版本
                        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Safari/537.36");
                        //得到服务器的返回相应码
                        int code = conn.getResponseCode();
                        if (code == 200) {
                            //获取输入流
                            InputStream is = conn.getInputStream();
                            //将流转化成BitMap对象
                            bitmap = BitmapFactory.decodeStream(is);
                            //告诉主线程：帮我改变界面
                            Message msg = new Message();
                            msg.what = CHANGE_UI;
                            msg.obj = bitmap;
                            handler.sendMessage(msg);
                        } else {
                            //否则返回失败
                            Message msg = new Message();
                            msg.what = ERROR;
                            handler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Message msg = new Message();
                        msg.what = ERROR;
                        handler.sendMessage(msg);
                    }
                }
            }.start();

        }
    }

    public void byOkHttp(View v) {
        final String path = et_path.getText().toString().trim();
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(this, "图片路径不能为空", Toast.LENGTH_SHORT);
        } else {
            //子线程请求网络，安卓4.0之后访问网络不能放在主线程中
            new Thread() {
                public void run() {
                    getImageByClient(path);
                }
            }.start();
        }

    }

    public void getImageByClient(String path) {

        OkHttpClient client = new OkHttpClient();
            //创建一个Request
            Request request = new Request.Builder()
                    .url(path)
                    .get()
                    .build();
            //发起异步请求，并加入回调
            client.newCall(request).enqueue(new Callback()
            {
                private Bitmap bitmap;
                @Override
                public void onFailure(Call call, IOException e){
                    //请求失败回调
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    //请求成功回调
//                    Log.e("okHttp","get="+response.body().string());
                    bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    //告诉主线程：帮我改变界面
                    Message msg = new Message();
                    msg.what = CHANGE_UI;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                }
            });


    }
}
