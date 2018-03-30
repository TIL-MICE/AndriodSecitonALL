package com.lckiss.videoplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, SurfaceHolder.Callback {
    private SurfaceView sv;
    private SurfaceHolder holder;
    private MediaPlayer mediaPlayer;
    private int position;
    private RelativeLayout rl;
    private Timer timer;
    private TimerTask task;
    private SeekBar seekBar;
    private ImageView play;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);

        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.sbar);
        play = (ImageView) findViewById(R.id.play);
        seekBar.setOnSeekBarChangeListener(this);
        sv = (SurfaceView) findViewById(R.id.sv);
        //初始化计时器
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    int progress = mediaPlayer.getCurrentPosition();
                    int total = mediaPlayer.getDuration();
                    seekBar.setMax(total);
                    seekBar.setProgress(progress);
                }
            }
        };
        //设置TimerTask延迟500ms，每隔500ms执行一次
        timer.schedule(task, 500, 500);
        rl = (RelativeLayout) findViewById(R.id.rl);
        //得到SurfaceView的容器，界面的内容是显示在容器里面的
        holder = sv.getHolder();
        holder.addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rl.getVisibility() == View.INVISIBLE) {
                    rl.setVisibility(View.VISIBLE);
                    //倒计时3秒
                    CountDownTimer cdt = new CountDownTimer(3000, 1000) {
                        @Override
                        public void onTick(long l) {
                            Log.i(TAG, "onTick: " + l);
                        }

                        @Override
                        public void onFinish() {
                            rl.setVisibility(View.INVISIBLE);
                        }
                    };
                    cdt.start();
                } else if (rl.getVisibility() == View.VISIBLE) {
                    rl.setVisibility(View.INVISIBLE);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        task.cancel();
        timer = null;
        task = null;
        super.onDestroy();
    }

    public void click(View v) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            play.setImageResource(android.R.drawable.ic_media_play);
        } else {
            mediaPlayer.start();
            play.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    //进度条改变时
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    //进度条开始拖动时出发
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    //进度条拖动停止时触发
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int position = seekBar.getProgress();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(position);
        }
    }
//创建完成时触发
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try{
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            File extDir = Environment.getExternalStorageDirectory();
            Log.i(TAG, "surfaceCreated: "+extDir+"/1.mkv");
//            mediaPlayer.setDataSource(extDir+"/1.mkv");
            mediaPlayer.setDataSource("http://lckiss-10056459.cos.myqcloud.com/%E5%8F%AF%E6%83%9C%E6%B2%A1%E5%A6%82%E6%9E%9C.mkv");
            mediaPlayer.setDisplay(holder);

            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    if (position>0){
                        mediaPlayer.seekTo(position);
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(this,"播放失败",Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }
//大小变化时触发
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }
//注销时触发
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
position=mediaPlayer.getCurrentPosition();//记录上次播放的位置
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;
    }
}
