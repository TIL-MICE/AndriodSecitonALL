package com.lckiss.threadcase;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt=(TextView)findViewById(R.id.txt);
        Log.i("myinfo","我是主："+Thread.currentThread().getId()+"");
    }

    //方法一: handel
//    private MyHandler myHandler=new MyHandler(){
//        @Override
//        public void handleMessage(Message msg) {
//            该方法就是读取消息队列中的消息（就是子线程发来的）;
//            if(msg.what==1) {
//                String str = msg.obj.toString();
//                txt.setText(str);
//            }else{
//                txt.setText("网上获取数据失败");
//            }
//        }
//    };
//
//    private class MyHandler  extends Handler{
//    }

//方法二:
    private void handlerData(final String data){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //该处代码在主线程中运行，供子线程来调用，接收子线程的工作成果
                txt.setText(data);
                Log.i("myinfo","我是主："+Thread.currentThread().getId()+"");
            }
        });
    }

    public void getNetData(View view){
        timeConsuming();
    }

    private void timeConsuming(){
//               在该方法里有耗时操作，我们就要用子线程完成;
//                run方法里的代码在子线程中运行
        new Thread(new Runnable() {
            @Override
            public void run() {
//                模拟从网上获取回来数据
                String netData="abc";
//                txt.setText(netData);
//                 上面这行代码是错的，应为安卓不许在子线程干与UI有关的工作;
//                给主线程发送一个消息，我已经完成了工作，内容如下;
//
//  方法一:
//                Message msg=new Message();
//                if(netData==null)//网上获取失败
//                    msg.what=2;
//                msg.what=1;
//                msg.obj=netData;
//                myHandler.sendMessage(msg);

                Log.i("myinfo","我是子："+Thread.currentThread().getId()+"");
//                方法二：使用含有runOnUiThread关键字的主线程方法来发送子线程的消息
                handlerData(netData);

            }
        }).start();

    }

}
