package com.spectraapps.myspare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.spectraapps.myspare.utility.ListSharedPreference;

public class SplashScreen extends Activity {

    Button button_ar, button_en;
    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;
    RelativeLayout relativeLayout;
    boolean isFirstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        setSharedPreference = new ListSharedPreference.Set(SplashScreen.this.getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(SplashScreen.this.getApplicationContext());

        button_ar = findViewById(R.id.btn_ar);
        button_en = findViewById(R.id.btn_en);

        relativeLayout = findViewById(R.id.splash_rela);
        relativeLayout.setBackgroundResource(R.drawable.app_background);

        insertAnimation();
        initButtonClickListener();

        isFirstRun = getSharedPreference.getFirstRun();

    }//end oncreate

    private void initButtonClickListener() {

        button_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setSharedPreference.setLanguage("en");
                setSharedPreference.setFirstRun(false);

                Intent intent = new Intent(SplashScreen.this, VideoActivity.class);
                startActivity(intent);

                finish();
            }
        });

        button_ar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                setSharedPreference.setLanguage("ar");
                setSharedPreference.setFirstRun(false);

                Intent intent = new Intent(SplashScreen.this, VideoActivity.class);
                startActivity(intent);

                finish();
            }
        });
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
                if (isFirstRun) {
                    button_ar.setVisibility(View.VISIBLE);
                    button_en.setVisibility(View.VISIBLE);
                } else {
                    button_ar.setVisibility(View.INVISIBLE);
                    button_en.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isFirstRun) {
                    YoYo.with(Techniques.BounceIn).playOn(button_ar);
                    YoYo.with(Techniques.BounceIn).playOn(button_en);
                } else {

                    Intent intent = new Intent(SplashScreen.this, VideoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }//end insert anim

}
