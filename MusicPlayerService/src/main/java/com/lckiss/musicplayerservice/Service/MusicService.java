package com.lckiss.musicplayerservice.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by root on 17-6-23.
 */

public class MusicService extends Service {
    private static final String TAG = "MusicService";
    public MediaPlayer mediaPlayer;

//传递给MainAcitivity中的onServiceConnected
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
    //增强可扩展性
   public class MyBinder extends Binder {
        public void plays(String path) {
            play(path);
        }

        public void pauses() {
            pause();
        }

        public void replays(String path) {
            replay(path);
        }

        public void stops() {
            stop();
        }

        public int getCurrentPosition() {
            return getCurrentProgress();
        }

        public int getMusicWidth() {
            return getMusicLength();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        super.onDestroy();
    }

    private int getMusicLength() {
        if (mediaPlayer!=null){
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    private int getCurrentProgress() {
        if (mediaPlayer!=null&mediaPlayer.isPlaying()){
            Log.i(TAG,"获取当前进度");
            return mediaPlayer.getCurrentPosition();
        }else if (mediaPlayer!=null&(!mediaPlayer.isPlaying()))
        {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    private void stop() {
        if (mediaPlayer!=null){
            Log.i(TAG,"停止播放");
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }else {
            Toast.makeText(getApplicationContext(),"已停止",Toast.LENGTH_SHORT).show();
        }
    }

    private void replay(String path) {
        Log.i(TAG,"重新播放");
        mediaPlayer.seekTo(0);
        try{
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    private void pause() {
        if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
            Log.i(TAG,"播放暂停");
            mediaPlayer.pause();
        }else if (mediaPlayer!=null&&(!mediaPlayer.isPlaying())){
            mediaPlayer.start();
        }
    }

    private void play(String path) {
        try {
            if (mediaPlayer == null) {
                Log.i(TAG, "开始播放音乐");
                //创建
                mediaPlayer = new MediaPlayer();
                //指定参数
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });
            } else {
                int position = getCurrentProgress();
                mediaPlayer.seekTo(position);
                try {
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
