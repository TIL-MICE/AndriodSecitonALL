package com.lckiss.listview;

import android.view.View;

/**
 * Created by root on 17-4-18.
 */

class Music {
    private String musicName;
    private int musicImg;

    public Music(String musicName, int musicImg) {
        this.musicName = musicName;
        this.musicImg = musicImg;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public int getMusicImg() {
        return musicImg;
    }

    public void setMusicImg(int musicImg) {
        this.musicImg = musicImg;
    }
}
