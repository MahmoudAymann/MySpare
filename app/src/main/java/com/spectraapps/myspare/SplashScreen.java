package com.spectraapps.myspare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.spectraapps.myspare.login.LoginActivity;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.splash_anim);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(500);

        ImageView splash = findViewById(R.id.splach_screen);
        splash.startAnimation(animation);

         animation.setAnimationListener(new Animation.AnimationListener() {
             @Override
             public void onAnimationStart(Animation animation) {

             }

             @Override
             public void onAnimationEnd(Animation animation) {
                 Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                 startActivity(intent);
                 finish();
             }

             @Override
             public void onAnimationRepeat(Animation animation) {

             }
         });
    }
}
