package com.spectraapps.myspare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.kimkevin.cachepot.CachePot;

public class SplashScreen extends Activity {

    Button button_ar, button_en;
    ListSharedPreference listSharedPreference = new ListSharedPreference();
    RelativeLayout relativeLayout;
    boolean isFirstRun;
    boolean isLogged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        button_ar = findViewById(R.id.btn_ar);
        button_en = findViewById(R.id.btn_en);

        relativeLayout = findViewById(R.id.splash_rela);
        relativeLayout.setBackgroundResource(R.drawable.app_background);

        insertAnimation();
        initButtonClickListener();

        isFirstRun = listSharedPreference.getFirstLaunch(getApplicationContext());

    }//end oncreate

    private void initButtonClickListener() {

        button_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listSharedPreference.setLanguage(getApplicationContext(), "en");
                CachePot.getInstance().push("langs", "en");
                CachePot.getInstance().push("islogged", false);
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
                listSharedPreference.setFirstLaunch(SplashScreen.this.getApplicationContext(), false);
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
                    intent.putExtra("islog",isLogged);
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
