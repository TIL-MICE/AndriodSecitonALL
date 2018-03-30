package com.lckiss.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.lckiss.aidlservice.IService;

/**
 * @author lckiss
 */
public class AliPayService extends Service {
private String TAG="AliPay";

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    private class MyBinder extends IService.Stub{

        @Override
        public void callALipayService() throws RemoteException {
            //当另一个app调用时开始执行，按生命周期进行相应提示处理
            methodInService();
        }
    }

    private void methodInService(){
        Log.i(TAG,"开始付费买装备");
    }

    @Override
    public void onCreate() {
        Log.i(TAG,"调用支付宝成功");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.v(TAG,"关闭支付宝");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG,"取消付费");
        return super.onUnbind(intent);
    }
}
