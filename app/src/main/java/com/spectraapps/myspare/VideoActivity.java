package com.spectraapps.myspare;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    VideoView videoHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        try {
            videoHolder.findViewById(R.id.videoView);
            setContentView(videoHolder);
            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.loady);
            videoHolder.setVideoURI(video);

        } catch (Exception ex) {
            jump();
        }
    }//end VideoActivity

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //jump();
       return true;
    }

    private void jump() {
        if (isFinishing())
            return;
        startActivity(new Intent(VideoActivity.this, MainActivity.class));
        finish();
    }
}