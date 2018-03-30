package com.lckiss.videoview;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl;
    private EditText et_path;
    private ImageView bt_play;
    private VideoView videoView;
    private MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        setContentView(R.layout.activity_main);

        et_path=(EditText)findViewById(R.id.et_path);
        bt_play=(ImageView)findViewById(R.id.bt_play);
        videoView=(VideoView)findViewById(R.id.sv);
        mediaController=new MediaController(this);
        videoView.setMediaController(mediaController);
        bt_play.setOnClickListener(this);
        rl=(RelativeLayout)findViewById(R.id.rl);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_play:
                 play();
//                rl.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void play() {
        if (videoView!=null&&videoView.isPlaying()){
            bt_play.setImageResource(android.R.drawable.ic_media_play);
            videoView.stopPlayback();
            return;
        }
//        videoView.setVideoPath("");
    videoView.setVideoURI(Uri.parse(et_path.getText().toString().trim()));
        videoView.start();
        bt_play.setImageResource(android.R.drawable.ic_media_pause);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                bt_play.setImageResource(android.R.drawable.ic_media_play);
            }
        });

    }

}
