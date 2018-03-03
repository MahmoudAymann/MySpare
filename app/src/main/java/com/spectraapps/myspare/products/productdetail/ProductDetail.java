package com.spectraapps.myspare.products.productdetail;

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
import com.github.kimkevin.cachepot.CachePot;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.model.inproducts.ProductsAllModel;
import com.spectraapps.myspare.products.ProductsFragment;
import com.spectraapps.myspare.helper.BaseBackPressedListener;


import java.util.HashMap;
import java.util.List;

public class ProductDetail extends Fragment
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SliderLayout mDemoSlider;
    PagerIndicator pagerIndicator;

    String pName, pId, pPrice, pNumber, pCurrency, uMobile, pImage1, pImage2, uId, uName, pDate, pCountry, pBrand, pModel;

    public ProductDetail() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);

        MainActivity.mToolbarText.setText(pName);

        mDemoSlider = rootView.findViewById(R.id.slider);
        pagerIndicator = rootView.findViewById(R.id.custom_indicator);

        getProductData();

        imageSliderInitilaize();
        fireBackButtonEvent();
        return rootView;
    }

    private void getProductData() {
        try {
            pName = CachePot.getInstance().pop("pName");
            pId = CachePot.getInstance().pop("pId");
            pPrice = CachePot.getInstance().pop("pPrice");
            pNumber = CachePot.getInstance().pop("pNumber");
            pCurrency = CachePot.getInstance().pop("pCurrency");
            pImage1 = CachePot.getInstance().pop("pImage1");
            pImage2 = CachePot.getInstance().pop("pImage2");
            pDate = CachePot.getInstance().pop("pDate");
            pCountry = CachePot.getInstance().pop("pCountry");
            pBrand = CachePot.getInstance().pop("pBrand");
            pModel = CachePot.getInstance().pop("pModel");

            uId = CachePot.getInstance().pop("uId");
            uMobile = CachePot.getInstance().pop("uMobile");
            uName = CachePot.getInstance().pop("uName");
        } catch (Exception e) {
            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void imageSliderInitilaize() {

        HashMap<String, String> file_maps = new HashMap<>();
        file_maps.put(pName, pImage1);
        file_maps.put(pName, pImage2);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

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
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        //Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}//end class