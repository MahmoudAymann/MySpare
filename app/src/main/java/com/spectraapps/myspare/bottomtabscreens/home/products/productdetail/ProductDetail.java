package com.spectraapps.myspare.bottomtabscreens.home.products.productdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.bottomtabscreens.home.products.ProductsFragment;
import com.spectraapps.myspare.helper.BaseBackPressedListener;

import java.util.HashMap;

public class ProductDetail extends Fragment
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SliderLayout mDemoSlider;
    PagerIndicator pagerIndicator;

    public ProductDetail() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);


        MainActivity.mToolbarText.setText("Product 1");

        mDemoSlider =  rootView.findViewById(R.id.slider);
        pagerIndicator = rootView.findViewById(R.id.custom_indicator);

        imageSliderInitilaize();
        fireBackButtonEvent();
        return rootView;
    }

    private void fireBackButtonEvent() {
        ((MainActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()) {
            @Override
            public void onBackPressed() {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new ProductsFragment())
                        .commit();
            }
        });
    }//end back pressed

    private void imageSliderInitilaize() {
        HashMap<String,Integer> file_maps = new HashMap<>();
        file_maps.put("car 1",R.drawable.car_tires);
        file_maps.put("car 2",R.drawable.car_electric);
        file_maps.put("car 3",R.drawable.car_accessories);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());

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
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
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