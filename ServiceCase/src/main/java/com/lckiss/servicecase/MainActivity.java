package com.lckiss.servicecase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button doBindService,getService,undoService,getProcess,autoGetProcess;
    private Intent intent,bindService;
    private MyBindService myBindService;
    private TextView showSign;
    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start=(Button)findViewById(R.id.start);
        final Button stop=(Button)findViewById(R.id.stop);
        intent=new Intent(this,MyService.class);

        doBindService=(Button) findViewById(R.id.doBindService);
        bindService=new Intent(this,MyBindService.class);

        getService=(Button) findViewById(R.id.getService);
        undoService=(Button) findViewById(R.id.undoService);
        getProcess=(Button) findViewById(R.id.getProcess);

        autoGetProcess=(Button) findViewById(R.id.autoGetProcess);

        showSign=(TextView)findViewById(R.id.showSign);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });

        doBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connection==null){
                    connection=new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            //该方法是当服务成功绑定到前台时执行
                            //参数二是服务对象onBind传递过来的句柄
                            MyBindService.MyBinder myBinder =(MyBindService.MyBinder)service;
                            myBindService=myBinder.getServiceObject();
                            //主动提交
                            myBindService.setProgressListener(new ProgressListener() {
                                @Override
                                public void onProgressListener(final int current) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("info", "myBindService.setProgressListener: 运行");
                                            showSign.setText(current+"");
                                        }
                                    });

                                }
                            });
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName name) {

                        }
                    };
                }
                //第一个要使用的服务意图 第二个是Serviceconnection，服务与前台的桥梁,第三个是没启动就自动启动
                bindService(bindService,connection, Context.BIND_AUTO_CREATE);
            }
        });
        getService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSign.setText(myBindService.getCurrent()+"");
            }
        });

        undoService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            unbindService(connection);
                connection=null;
            }
        });

        getProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBindService.work();
            }
        });

        autoGetProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBindService.autowork();
            }
        });
    }
}
