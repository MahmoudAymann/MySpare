package com.spectraapps.myspare.product.productdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.spectraapps.myspare.R;

import java.util.HashMap;

public class ProductDetail extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    Toolbar mToolbar;
    SliderLayout mDemoSlider;
    PagerIndicator pagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mToolbar = findViewById(R.id.productDetail_toolbar);

        mToolbar.setTitle(getString(R.string.product_detail));

        mDemoSlider =  findViewById(R.id.slider);
        pagerIndicator = findViewById(R.id.custom_indicator);
        imageSliderInitilaize();
    }

    private void imageSliderInitilaize() {
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("car 1",R.drawable.car_tires);
        file_maps.put("car 2",R.drawable.car_battery);
        file_maps.put("car 3",R.drawable.car_accessories);
        file_maps.put("car 4", R.drawable.car_inside);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);


            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);


            mDemoSlider.addSlider(textSliderView);
        }//end for

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
        mDemoSlider.setCustomIndicator(pagerIndicator);
        //mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}//end class