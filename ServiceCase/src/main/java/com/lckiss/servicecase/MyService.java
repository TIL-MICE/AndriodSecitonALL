package com.lckiss.servicecase;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.util.Log;


public class MyService extends Service {
    Thread thread;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.i("info","onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<20;i++){
                    Log.i("info","i="+(i+1));
                    try {
                        Thread.sleep(1000);
                        //如果正在执行这个sleep，就被动终止线程，会抛出一个interruptException异常

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //解决代码 0表示正常退出，1表示非正常退出
                        //System.exit(1);
                        break;
                    }
                }
            }
        });
        thread.start();
        Log.i("info","onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i("info","onDestroy");
        thread.interrupt();
        super.onDestroy();
    }
}
