package com.spectraapps.myspare.bottomtabscreens.favourite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.bottomtabscreens.profile.SellerProfilePD;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.model.AddToFavModel;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.utility.ListSharedPreference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavProductDetail extends Fragment
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener {

    SliderLayout mDemoSlider;
    PagerIndicator pagerIndicator;

    CircleImageView userImageView;
    String pName, pId, pPrice, pNumber, pCurrency, uMobile, pImage1, pImage2, uId, uName, pDate, pCountry, pBrand, pModel;

    TextView pName_tv, pPrice_tv, pNumber_tv, pCurrency_tv, pDate_tv, pCountry_tv, pBrand_tv, pModel_tv,
            uName_tv, uMobile_tv;

    RelativeLayout relativeLayout;

    ListSharedPreference.Get getSharedPreference;


    String langhere;
    private String uImage;
    private String pIdFav;
    private boolean isFav;

    public FavProductDetail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fav_product_detail, container, false);

        MainActivity.mToolbarText.setText(pName);

        getSharedPreference = new ListSharedPreference.Get(FavProductDetail.this.getContext().getApplicationContext());

        langhere = getSharedPreference.getLanguage();

        initUI(rootView);
        getProductData();

        fireBackButtonEvent();

        return rootView;
    }

    private void initUI(View rootView) {
        mDemoSlider = rootView.findViewById(R.id.slider);
        pagerIndicator = rootView.findViewById(R.id.custom_indicator);


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
        userImageView = rootView.findViewById(R.id.user_image_PD);
    }

    private void setButtonFavUI() {
        if (pIdFav.equals("true")) {
            MainActivity.imageButtonFav.setImageResource(R.drawable.ic_favorite_full_24dp);
            isFav = true;
        } else {
            MainActivity.imageButtonFav.setImageResource(R.drawable.ic_favorite_empty_24dp);
            isFav = false;
        }

        MainActivity.imageButtonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFav) {
                    MainActivity.imageButtonFav.setImageResource(R.drawable.ic_favorite_empty_24dp);
                    serverRemoveFromFav(pId);
                    isFav = false;
                } else {
                    MainActivity.imageButtonFav.setImageResource(R.drawable.ic_favorite_full_24dp);
                    serverAddToFav(pId);
                    isFav = true;
                }
            }
        });
        MainActivity.imageButtonFav.setVisibility(View.VISIBLE);
    }

    private void serverRemoveFromFav(String pId) {
        try {
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<AddToFavModel> addCall = retrofit.addToFavourite(getSharedPreference.getEmail(), pId, false);

            addCall.enqueue(new Callback<AddToFavModel>() {
                @Override
                public void onResponse(@NonNull Call<AddToFavModel> call, @NonNull Response<AddToFavModel> response) {
                    try {
                        if (!response.isSuccessful()) {

                            if (response.body().getStatus() != null)
                                Toast.makeText(getActivity(), " " + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AddToFavModel> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void serverAddToFav(String pId) {
        try {
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);
            final Call<AddToFavModel> addCall = retrofit.addToFavourite(getSharedPreference.getEmail(), pId, true);

            addCall.enqueue(new Callback<AddToFavModel>() {
                @Override
                public void onResponse(@NonNull Call<AddToFavModel> call, @NonNull Response<AddToFavModel> response) {
                    try {
                        if (!response.isSuccessful()) {
                            if (response.body().getStatus() != null)
                                Toast.makeText(getContext(), response.body().getStatus().getTitle(),
                                        Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AddToFavModel> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setData() {

        uName_tv.setText(uName);
        uMobile_tv.setText(uMobile);

        if (uImage != null) {
            Picasso.with(getContext()).load(uImage)
                    .error(R.drawable.place_holder)
                    .placeholder(R.drawable.place_holder)
                    .into(userImageView);
        }
        pName_tv.setText(pName);
        pPrice_tv.setText(pPrice);
        pNumber_tv.setText(pNumber);
        pCurrency_tv.setText(pCurrency);
        pDate_tv.setText(pDate);
        pCountry_tv.setText(pCountry);
        pBrand_tv.setText(pBrand);
        pModel_tv.setText(pModel);
        imageSliderInitilaize();
    }

    private void getProductData() {
        try {
            pId = CachePot.getInstance().pop("pId");
            pName = CachePot.getInstance().pop("pName");
            pPrice = CachePot.getInstance().pop("pPrice");
            pNumber = CachePot.getInstance().pop("pNumber");
            pCurrency = CachePot.getInstance().pop("pCurrency");
            pDate = CachePot.getInstance().pop("pDate");
            pCountry = CachePot.getInstance().pop("pCountry");
            pBrand = CachePot.getInstance().pop("pBrand");
            pModel = CachePot.getInstance().pop("pModel");

            pIdFav = CachePot.getInstance().pop("pIdFav");
            setButtonFavUI();

            pImage1 = getSharedPreference.getImg1();
            pImage2 = getSharedPreference.getImg2();

            MainActivity.mToolbarText.setText(pName);

            uId = CachePot.getInstance().pop("uId");
            uMobile = CachePot.getInstance().pop("uMobile");
            uName = CachePot.getInstance().pop("uName");
            uImage = CachePot.getInstance().pop("uImage");

            Log.v("productinfo", pName + "/" + pId + "/" + pPrice + "/" + pNumber + "/" + pCurrency + "/ " + pImage1 + "/ " + pImage2 + " /" + pDate + "/" + pCountry
                    + "/" + pBrand + "/" + pModel + "/" + uId + "/" + uMobile + "/" + uName + "/ " + uImage);

            setData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fireBackButtonEvent() {
        ((MainActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()) {
            @Override
            public void onBackPressed() {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new Favourite())
                        .commit();
            }
        });
    }//end back pressed

    @Override
    public void onStart() {
        super.onStart();
        imageSliderInitilaize();
    }

    private void imageSliderInitilaize() {

        HashMap<String, String> file_maps = new HashMap<>();
        if (!pImage1.equals(""))
            file_maps.put(pName, pImage1);
        if (!pImage2.equals(""))
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
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new SellerProfilePD()).commit();
                break;
            default:
                break;
        }
    }

}//end class