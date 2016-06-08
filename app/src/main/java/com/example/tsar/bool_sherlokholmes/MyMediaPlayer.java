package com.example.tsar.bool_sherlokholmes;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by tsar on 24.12.2015.
 */
public class MyMediaPlayer {
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private android.os.Handler durationHandler = new android.os.Handler();
    private SeekBar seekBar;
    private static int oneTimeOnly = 0;
    private ImageView play, pause;

    public MyMediaPlayer(Context context, SeekBar sb, ImageView bt_play, ImageView bt_pause) {
        mediaPlayer = MediaPlayer.create(context, R.raw.theadventuresofsherlockholmes001adventure1ascandalinbohemia);
        seekBar = sb;
        play = bt_play;
        pause = bt_pause;
        finalTime = mediaPlayer.getDuration();
        seekBar.setMax((int) finalTime);
    }

    public void play() {
        mediaPlayer.start();
        startTime = mediaPlayer.getCurrentPosition();

        seekBar.setProgress((int) startTime);
        durationHandler.postDelayed(UpdateSongTime, 1000);

        play.setVisibility(View.GONE);
        pause.setVisibility(View.VISIBLE);
    }

    public void pause() {
        try {
        mediaPlayer.pause();
       //     mediaPlayer.release();
        pause.setVisibility(View.GONE);
        play.setVisibility(View.VISIBLE);}
        catch(Exception e){}
    }

    public void activity_pause(){
        int time = mediaPlayer.getCurrentPosition();
        if(time != 0){
        startTime = time;
        pause();}


    }
    public void activity_resume(){
        mediaPlayer.seekTo((int) startTime);


    }
    public void activity_saveIS(Bundle outState){
        outState.putInt("possition", mediaPlayer.getCurrentPosition());
        mediaPlayer.pause();

    }
    public void activity_RestoreIS(Bundle savedInstanceState){
        int pos = savedInstanceState.getInt("possition");
        if(pos != 0)
        seekBarChange(true,pos);
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            seekBar.setProgress((int) startTime);
            durationHandler.postDelayed(this, 100);
        }
    };

    public void seekBarChange(Boolean fromUser, int progress){
        if (fromUser) {
            mediaPlayer.seekTo(progress);
            seekBar.setProgress(progress);
        }
    }


}
