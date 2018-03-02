package com.spectraapps.myspare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.widget.VideoView;

import com.spectraapps.myspare.login.LoginActivity;

public class VideoActivity extends Activity {

    VideoView videoHolder;
    boolean mIsLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        try {
            videoHolder = VideoActivity.this.findViewById(R.id.videoView1);
            Uri video = Uri.parse("android.resource://" + getPackageName()
                    + "/" + R.raw.loady);
            videoHolder.setVideoURI(video);

            videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer mp) {
                    jump();
                }
            });
            videoHolder.start();
        } catch (Exception ex) {
            jump();
        }
    }//end VideoActivity

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        jump();
        return true;
    }

    private void jump() {
        if (isFinishing())
        {
            checkForLogging();
            return;
        }
        if (mIsLogged)
            startActivity(new Intent(VideoActivity.this, MainActivity.class));
        else
            startActivity(new Intent(VideoActivity.this, LoginActivity.class));
        finish();
    }

    void checkForLogging(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mIsLogged = prefs.getBoolean("isLoggedIn", false);
    }

}