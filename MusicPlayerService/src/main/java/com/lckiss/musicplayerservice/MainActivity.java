package com.lckiss.musicplayerservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;


import com.lckiss.musicplayerservice.Service.MusicService;

import java.io.File;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText path;
    private Intent intent;
    private myConn conn;
    MusicService.MyBinder binder;
    private SeekBar seekBar;
    private Thread thread;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                //接收子线程传过来的当前进度并更新
                    int currentPosition = (Integer) msg.obj;
                    seekBar.setProgress(currentPosition);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        path = (EditText) findViewById(R.id.et_inputpath);
        findViewById(R.id.bt_play).setOnClickListener(this);
        findViewById(R.id.bt_pause).setOnClickListener(this);
        findViewById(R.id.bt_replay).setOnClickListener(this);
        findViewById(R.id.bt_stop).setOnClickListener(this);
        seekBar = (SeekBar) findViewById(R.id.seekBar1);

        //新建一个连接
        conn = new myConn();
        intent = new Intent(this, MusicService.class);
        //绑定服务
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

//初始化进度条
    private void initSeekBar() {
        int musicWidth = binder.getMusicWidth();
        seekBar.setMax(musicWidth);
    }
//更新进度条
    private void UpdataProgress() {
        thread = new Thread() {
            @Override
            public void run() {
                while (!interrupted()) {
                    //调用服务中的获取当前播放进度
                    int currentPosition = binder.getCurrentPosition();
                    Message message = Message.obtain();
                    message.obj = currentPosition;
                    message.what = 100;
                    handler.sendMessage(message);
                }
            }
        };
        thread.start();
    }

    private class myConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (MusicService.MyBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    @Override
    protected void onDestroy() {
        //终止进程并解绑服务
        if (thread!=null&thread.isInterrupted()){
            thread.interrupt();
        }
        unbindService(conn);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        String pathway = path.getText().toString().trim();
        File SDpath = Environment.getExternalStorageDirectory();
        File file = new File(SDpath, pathway);
        String path = file.getAbsolutePath();
        Log.i("info-----------",path);
        switch (view.getId()) {
            case R.id.bt_play:
                if (file.exists() && file.length() > 0) {
                    binder.plays(path);
                    initSeekBar();
                    UpdataProgress();
                } else {
                    Toast.makeText(this, "找不到文件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_pause:
                binder.pauses();
                break;
            case R.id.bt_replay:
                binder.replays(pathway);
                break;
            case R.id.bt_stop:
                thread.interrupt();
                if (thread.isInterrupted()){
                    binder.stops();
                }
                break;
            default:
                break;
        }
    }
}
