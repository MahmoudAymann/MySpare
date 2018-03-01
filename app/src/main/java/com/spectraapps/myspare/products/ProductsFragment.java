package com.spectraapps.myspare.products;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.kimkevin.cachepot.CachePot;
import com.michael.easydialog.EasyDialog;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.SplashScreen;
import com.spectraapps.myspare.adapters.AllProductsAdapter;
import com.spectraapps.myspare.adapters.ProductsRecyclerAdapter;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.bottomtabscreens.favourite.Favourite;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.model.inproducts.ProductsAllModel;
import com.spectraapps.myspare.model.inproducts.ProductsModel;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.products.productdetail.ProductDetail;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsFragment extends Fragment {

    FloatingActionButton fabButton;
    EditText editText;
    Spinner spinner1, spinner2, spinner3, spinner5;

    RecyclerView recyclerView;
    ProductsRecyclerAdapter mProductsRecyclerAdapter;
    AllProductsAdapter mAllProductsAdapter;

    ArrayList<ProductsModel.DataBean> mProductDataList = new ArrayList<>();

    ArrayList<ProductsAllModel.DataBean> mProductAllDataList = new ArrayList<>();

    PullRefreshLayout pullRefreshLayout;

    Calendar mCalendar;
    boolean isFav = false;

    FButton fButton;

    String mUserID;

    String lang_key;

    public ProductsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_products, container, false);
        getUserInfo();
        fireBackButtonEvent();
        initUI(rootView);
        initRecyclerView();

        return rootView;
    }//end onCreateView()

    private String getLangKey() {

        switch (SplashScreen.LANG_NUM) {
            case 1:
                return lang_key = "en";

            case 2:
                return lang_key = "ar";
            default:
                return "en";
        }
    }

    private void initRecyclerView() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }
    }//end initRecyclerView()

    private void initUI(View rootView) {
        initPullRefreshLayout(rootView);

        fabButton = rootView.findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();
            }
        });
        YoYo.with(Techniques.BounceInUp)
                .duration(700)
                .repeat(1)
                .playOn(fabButton);

        recyclerView = rootView.findViewById(R.id.products_recycler);

        //btnFav = rootView.findViewById(R.id.imageButtonFav);

    }//end initUI

    private void initPullRefreshLayout(View rootView) {
        pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutProducts);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                turnOnServers(MainActivity.login_key);
            }
        });
    }

    private void showPopUp() {

        @SuppressLint("InflateParams")
        View popupView = this.getLayoutInflater().inflate(R.layout.popup_filter_layout, null);
        new EasyDialog(getActivity())
                //.setLayoutResourceId(R.layout.popup_filter_layout)//layout resource id
                .setLayout(popupView)
                .setBackgroundColor(ProductsFragment.this.getResources().getColor(R.color.white_gray))
                .setLocationByAttachedView(fabButton)
                .setGravity(EasyDialog.GRAVITY_TOP)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 800, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(300, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(300, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(true)
                .setMarginLeftAndRight(30, 30)
                .show();

        spinner1 = popupView.findViewById(R.id.spinner_brand_popup);
        spinner2 = popupView.findViewById(R.id.spinner_country_popup);
        spinner3 = popupView.findViewById(R.id.spinner_year_popup);
        spinner5 = popupView.findViewById(R.id.spinner_model_popup);

        fButton = popupView.findViewById(R.id.flatButton);
        fButton.setButtonColor(getResources().getColor(R.color.dark_yellow));
        fButton.setShadowColor(getResources().getColor(R.color.white_gray));
        fButton.setCornerRadius(10);
        fButton.setShadowEnabled(true);
        fButton.setShadowHeight(7);

        editText = popupView.findViewById(R.id.editText1_pop);
        mCalendar = Calendar.getInstance();
        addYears(popupView);
    }

    private void addYears(View view) {
        int current_year = mCalendar.get(Calendar.YEAR);

        ArrayList<Integer> year_array = new ArrayList<>();
        for (int i = current_year; i >= 1990; i--) {
            year_array.add(i);
        }

        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                view.getContext(), android.R.layout.simple_spinner_item, year_array);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(spinnerArrayAdapter);

    }//end addYears();

    private void serverProductsAll() {
        try {
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<ProductsAllModel> productsCall = retrofit.productsAll(getLangKey(), Home.CATEGK_KEY.toString());

            productsCall.enqueue(new Callback<ProductsAllModel>() {
                @Override
                public void onResponse(Call<ProductsAllModel> call, Response<ProductsAllModel> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), R.string.loading, Toast.LENGTH_SHORT).show();

                        mProductAllDataList.addAll(response.body().getData());
                        mAllProductsAdapter.notifyDataSetChanged();
                        //Toast.makeText(getActivity(), "asd" + response.body().getData(), Toast.LENGTH_SHORT).show();
                        Log.v("jkjk", response.body().getData().size() + "");
                        pullRefreshLayout.setRefreshing(false);
                    } else {
                        pullRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ProductsAllModel> call, Throwable t) {
                    //if (call != null){
                    Log.v("tagy", t.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), "Erorr::" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        turnOnServers(MainActivity.login_key);
    }

    private void serverproductsWithMail() {

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        final Call<ProductsModel> productsCall = retrofit.productsWithMail(getLangKey(), Home.CATEGK_KEY.toString(), mUserID);

        productsCall.enqueue(new Callback<ProductsModel>() {
            @Override
            public void onResponse(Call<ProductsModel> call, Response<ProductsModel> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), R.string.loading, Toast.LENGTH_SHORT).show();

                    mProductDataList.addAll(response.body().getData());
                    mProductsRecyclerAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "asd" + response.body().getData(), Toast.LENGTH_SHORT).show();
                    pullRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProductsModel> call, Throwable t) {
                Log.v("tagy", t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void turnOnServers(Integer key) {
        switch (key) {
            case 1:
                initAdapterAllWith();
                recyclerView.setAdapter(mProductsRecyclerAdapter);
                serverproductsWithMail();
                mProductsRecyclerAdapter.notifyDataSetChanged();
                break;
            case 2:
                initAdapterAllWith();
                recyclerView.setAdapter(mProductsRecyclerAdapter);
                serverproductsWithMail();
                mProductsRecyclerAdapter.notifyDataSetChanged();

                break;
            case 3:
                initAdapterAllProducts();
                recyclerView.setAdapter(mAllProductsAdapter);
                serverProductsAll();
                mAllProductsAdapter.notifyDataSetChanged();
                break;
            default:
                Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                break;

        }
    }
    private void initAdapterAllProducts() {
        mAllProductsAdapter = new AllProductsAdapter(getContext(), mProductAllDataList,
                new AllProductsAdapter.ListAllListeners() {
            @Override
            public void onCardViewClick(ProductsAllModel.DataBean produtsAllModel) {
                Log.e("plz", produtsAllModel.getProductName());

                CachePot.getInstance().push("pName", produtsAllModel.getProductName());
                CachePot.getInstance().push("pId", produtsAllModel.getProductNumber());
                CachePot.getInstance().push("pPrice", produtsAllModel.getProductPrice());
                CachePot.getInstance().push("pNumber", produtsAllModel.getProductNumber());
                CachePot.getInstance().push("pCurrency", produtsAllModel.getCurrency());
                CachePot.getInstance().push("pImage1", produtsAllModel.getImage1());
                CachePot.getInstance().push("pImage2", produtsAllModel.getImage2());
                CachePot.getInstance().push("pDate", produtsAllModel.getDate());
                CachePot.getInstance().push("pCountry", produtsAllModel.getCountry());
                CachePot.getInstance().push("pBrand", produtsAllModel.getBrand());
                CachePot.getInstance().push("pModel", produtsAllModel.getModel());

                CachePot.getInstance().push("uId", produtsAllModel.getId());
                CachePot.getInstance().push("uMobile", produtsAllModel.getMobile());
                CachePot.getInstance().push("uName", produtsAllModel.getName());

                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new ProductDetail()).commit();

            }

            @Override
            public void onFavButtonClick(View v, int position, boolean isFav) {
                Log.e("plz", "" + isFav + " pos " + position);
            }
        });
    }


    private void initAdapterAllWith() {
        /////////
    }//end

    private void fireBackButtonEvent() {
        ((MainActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()) {
            @Override
            public void onBackPressed() {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new Home())
                        .commit();
            }
        });
    }//end back pressed

    private void getUserInfo() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        mUserID = prefs.getString("email", "nashwa@gmail.com");
    }

}