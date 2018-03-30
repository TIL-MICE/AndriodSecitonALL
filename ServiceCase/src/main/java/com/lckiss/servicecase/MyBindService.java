package com.lckiss.servicecase;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyBindService extends Service {
    private int current=1000;
    Thread thread;

   private ProgressListener progressListener;

    public void setProgressListener(ProgressListener progressListener){
        Log.d("info", "setProgressListener: 运行");
        this.progressListener=progressListener;
    }

    public MyBindService() {
    }

    public int getCurrent(){return current;}

    @Override
    public IBinder onBind(Intent intent) {

        //返回给前台onServiceconnection的第二个值 服务对象的句柄，通过这个可以找到服务对象
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("info","解绑成功");
      try{
        if (thread.isAlive()){
            thread.interrupt();
        }}catch (Exception e){
            e.printStackTrace();
        }
        current=200;
        return super.onUnbind(intent);
    }

    class MyBinder extends Binder {
        //该类中一点有一个返回值就是这个服务类型
        public MyBindService getServiceObject(){
            return MyBindService.this;
        }
    }

   public void work(){
       thread=new Thread(new Runnable() {
           @Override
           public void run() {
               for (int i=0;i<20;i++){
                   Log.i("info","work i="+(i+1));
                   current=(i+1);
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
   }
   public void autowork(){
       thread=new Thread(new Runnable() {
           @Override
           public void run() {
               for (int i=0;i<20;i++){
                   Log.i("info","autowork i="+(i+1));
//主动服务
                   Log.d("info", "autowork: 运行");
                  progressListener.onProgressListener(i+1);
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
   }

}
