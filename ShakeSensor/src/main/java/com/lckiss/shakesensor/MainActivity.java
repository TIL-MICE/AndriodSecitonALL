package com.lckiss.shakesensor;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ShakeListener mShakeListener = null;
    Vibrator mVibrator;
    private RelativeLayout mImgUp;
    private RelativeLayout mImgDn;
    private SoundPool sndPool;
    private Map<Integer, Integer> loadSound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化数据
        init();
        // 调用工具类方法把assets目录下的声音存放在map中，返回一个HashMap
        loadSound = Utils.loadSound(sndPool, this);
        // 创建加速度监听器的对象
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShakeListener = new ShakeListener(this);
        // 加速度传感器，达到速度阀值，播放动画
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                Utils.startAnim(mImgUp, mImgDn); // 开始 摇一摇手掌动画
                mShakeListener.stop();// 停止加速度传感器
                sndPool.play(loadSound.get(0), (float) 1, (float) 1, 0, 0,
                        (float) 1.2);// 摇一摇时播放map中存放的第一个声音

                startVibrato();// 震动
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        sndPool.play(loadSound.get(1), (float) 1, (float) 1, 0,
                                0, (float) 1.0);// 摇一摇结束后播放map中存放的第二个声音
                        Toast.makeText(getApplicationContext(),
                                "抱歉，暂时没有找到\n在同一时刻摇一摇的人。\n再试一次吧！", Toast.LENGTH_SHORT).show();
                        mVibrator.cancel();// 震动关闭
                        mShakeListener.start();// 再次开始检测加速度传感器值
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }

    private void init() {
        mVibrator = (Vibrator) getApplication().getSystemService(
                VIBRATOR_SERVICE);
        mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
        mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);
        sndPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 5);
    }

    public void startVibrato() { // 定义震动
        mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1); // 第一个｛｝里面是节奏数组，
    }

    public void shake_activity_back(View v) { // 标题栏 返回按钮
        this.finish();
    }

    public void linshi(View v) { // 标题栏
        Utils.startAnim(mImgUp, mImgDn);
    }

}