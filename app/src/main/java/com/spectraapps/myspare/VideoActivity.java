package com.spectraapps.myspare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.kimkevin.cachepot.CachePot;
import com.spectraapps.myspare.login.LoginActivity;

public class VideoActivity extends Activity {

    VideoView videoHolder;
    boolean mIsLogged;
ListSharedPreference listSharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mIsLogged = listSharedPreference.getLoginStatus(VideoActivity.this.getApplicationContext());

        try {
            videoHolder = VideoActivity.this.findViewById(R.id.videoView1);
            Uri video = Uri.parse("android.resource://" + getPackageName()
                    + "/" + R.raw.loady);
            videoHolder.setVideoURI(video);

            videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer mp) {
                    jump();
                  //  Toast.makeText(VideoActivity.this, ""+mIsLogged, Toast.LENGTH_SHORT).show();
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
           // Toast.makeText(this, "finish", Toast.LENGTH_SHORT).show();

    }

    void checkForLogging() {

        if (mIsLogged) {
            Intent intent = new Intent(VideoActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(VideoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

}