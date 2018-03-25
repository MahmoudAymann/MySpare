package com.spectraapps.myspare.products;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.spectraapps.myspare.bottomtabscreens.profile.SellerProfilePD;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.utility.ListSharedPreference;
import com.squareup.picasso.Picasso;


import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetail extends Fragment
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener {

    SliderLayout mDemoSlider;
    PagerIndicator pagerIndicator;

    String pName, pId, pPrice, pNumber, pCurrency, uMobile, pImage1, pImage2, uId, uName, uImage, pDate, pCountry, pBrand, pModel;

    TextView pName_tv, pPrice_tv, pNumber_tv, pCurrency_tv, pDate_tv, pCountry_tv, pBrand_tv, pModel_tv,
            uName_tv, uMobile_tv;

    RelativeLayout relativeLayout;

    String langhere;
    CircleImageView imageView;

    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;

    public ProductDetail() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);

        setSharedPreference = new ListSharedPreference.Set(ProductDetail.this.getContext().getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(ProductDetail.this.getContext().getApplicationContext());

        initUI(rootView);

        getProductData();

        imageSliderInitilaize();
        fireBackButtonEvent();
        langhere = CachePot.getInstance().pop("langy");

        return rootView;
    }

    private void initUI(View rootView) {
        mDemoSlider = rootView.findViewById(R.id.slider);
        pagerIndicator = rootView.findViewById(R.id.custom_indicator);
        imageView = rootView.findViewById(R.id.user_image_PD);
        relativeLayout = rootView.findViewById(R.id.relative_user_info);
        relativeLayout.setOnClickListener(this);

        pName_tv = rootView.findViewById(R.id.get_PName_PD);
        pPrice_tv = rootView.findViewById(R.id.get_PPrice_PD);
        pNumber_tv = rootView.findViewById(R.id.get_PSerialNum_PD);
        pCurrency_tv = rootView.findViewById(R.id.get_curency_PD);
        pDate_tv = rootView.findViewById(R.id.get_Pdate_PD);
        pCountry_tv = rootView.findViewById(R.id.get_PCountry_PD);
        pBrand_tv = rootView.findViewById(R.id.get_PBrand_PD);
        pModel_tv = rootView.findViewById(R.id.get_PModel_PD);

        uName_tv = rootView.findViewById(R.id.user_name_PD);
        uMobile_tv = rootView.findViewById(R.id.textView_phone_PD);
        uMobile_tv.setOnClickListener(this);
    }

    private void setData() {

        uName_tv.setText(uName);
        uMobile_tv.setText(uMobile);
        Picasso.with(getContext()).load(uImage)
                .error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder)
                .into(imageView);
        pName_tv.setText(pName);
        pPrice_tv.setText(pPrice);
        pNumber_tv.setText(pNumber);
        pCurrency_tv.setText(pCurrency);
        pDate_tv.setText(pDate);
        pCountry_tv.setText(pCountry);
        pBrand_tv.setText(pBrand);
        pModel_tv.setText(pModel);
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

            MainActivity.mToolbarText.setText(getSharedPreference.getCategory());

            uId = CachePot.getInstance().pop("uId");
            uMobile = CachePot.getInstance().pop("uMobile");
            uName = CachePot.getInstance().pop("uName");
            uImage = CachePot.getInstance().pop("uImage");
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
        setData();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void imageSliderInitilaize() {

        HashMap<String, String> file_maps = new HashMap<>();
        if (pImage1 != null)
            file_maps.put(pName, pImage1);
        if (pImage2 != null)
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView_phone_PD:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + uMobile));
                startActivity(dialIntent);
                break;
            case R.id.relative_user_info:
                CachePot.getInstance().push("suid", uId);
                CachePot.getInstance().push("slangh", langhere);

                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new SellerProfilePD()).commit();

                break;
        }
    }
}//end class