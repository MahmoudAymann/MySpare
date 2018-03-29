package com.spectraapps.myspare;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.VideoView;

import com.spectraapps.myspare.login.LoginActivity;
import com.spectraapps.myspare.utility.ListSharedPreference;

public class VideoActivity extends Activity {

    VideoView videoHolder;
    boolean mIsLogged;
    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        setSharedPreference = new ListSharedPreference.Set(VideoActivity.this.getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(VideoActivity.this.getApplicationContext());

        mIsLogged = getSharedPreference.getLoginStatus();

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
            checkForLogging();
            finish();
    }

    void checkForLogging() {

        if (mIsLogged) {
            Intent intent = new Intent(VideoActivity.this, MainActivity.class);
            startActivity(intent);

        } else {
            Intent intent = new Intent(VideoActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

}