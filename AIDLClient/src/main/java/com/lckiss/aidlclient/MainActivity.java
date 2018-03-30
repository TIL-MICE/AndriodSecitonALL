package com.lckiss.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lckiss.aidlservice.IService;

public class MainActivity extends AppCompatActivity {
    private Intent service;
    private IService iService;
    private MyConn conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//连接到另一个app的服务
        service = new Intent();
        service.setAction("com.lckiss.alipay");
        service.setPackage("com.lckiss.aidlservice");
    }

    public void bind(View v) {
        conn = new MyConn();
        bindService(service, conn, BIND_AUTO_CREATE);
    }

    public void call(View v) {
        try {
            iService.callALipayService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iService = IService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    public void unbind(View v) {
        unbindService(conn);
    }
}

