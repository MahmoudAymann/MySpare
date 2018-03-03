package com.spectraapps.myspare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashScreen extends Activity {

    Button button_ar, button_en;
    ListSharedPreference listSharedPreference = new ListSharedPreference();

    boolean isFirstRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        button_ar = findViewById(R.id.btn_ar);
        button_en = findViewById(R.id.btn_en);

        insertAnimation();
        initButtonClickListener();

        isFirstRun = listSharedPreference.getFirstLaunch(getApplicationContext());

        if (isFirstRun) {
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();

    }//end oncreate

    private void initButtonClickListener() {
        button_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listSharedPreference.setLanguage(getApplicationContext(), "en");
                listSharedPreference.setFirstLaunch(getApplicationContext(), false);
                Intent intent = new Intent(SplashScreen.this, VideoActivity.class);
                startActivity(intent);

                finish();
            }
        });

        button_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listSharedPreference.setLanguage(getApplicationContext(), "ar");
                listSharedPreference.setFirstLaunch(getApplicationContext(), false);
                Intent intent = new Intent(SplashScreen.this, VideoActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }


    private void hideButtons() {

    }

    private void insertAnimation() {

        Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.splash_anim);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(800);

        ImageView splash = findViewById(R.id.splach_screen);
        splash.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                YoYo.with(Techniques.BounceIn).playOn(button_ar);
                YoYo.with(Techniques.BounceIn).playOn(button_en);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }//end insert anim

}
