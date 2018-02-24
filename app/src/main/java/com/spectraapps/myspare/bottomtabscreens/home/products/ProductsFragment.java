package com.spectraapps.myspare.bottomtabscreens.home.products;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.michael.easydialog.EasyDialog;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.SplashScreen;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.model.ProductsModel;
import com.spectraapps.myspare.network.MyRetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsFragment extends Fragment{
    FloatingActionButton fabButton;
    EditText editText;
    Spinner spinner1, spinner2, spinner3, spinner5;

    RecyclerView recyclerView;
    ProductsRecyclerAdapter mProductsRecyclerAdapter;
    ArrayList<ProductsModel.DataBean> mProductDataArrayList = new ArrayList<>();

    PullRefreshLayout pullRefreshLayout;

    Calendar mCalendar;

    FButton fButton;

    String mUserID;

    public ProductsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products, container, false);
        getUserInfo();
        fireBackButtonEvent();
        initUI(rootView);
        initRecyclerView();

        recyclerView.setAdapter(mProductsRecyclerAdapter);
        serverProductsAll();


        return rootView;
    }//end onCreateView()

    private void initRecyclerView() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }

        mProductsRecyclerAdapter = new ProductsRecyclerAdapter(mProductDataArrayList,
                new ProductsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductsModel.DataBean productsModel) {
               //Toast.makeText(getActivity(), ""+productsModel.getName(), Toast.LENGTH_SHORT).show();
            }
        });

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

    }//end initUI

    private void initPullRefreshLayout(View rootView) {
        pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutProducts);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                serverProductsAll();
                mProductsRecyclerAdapter.notifyDataSetChanged();
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

    private void serverProductsAll(){

       Api retrofit = MyRetrofitClient.getBase().create(Api.class);
       String lang_key = "";
       switch (SplashScreen.LANG_NUM) {
           case 1:
               lang_key = "en";
               break;
           case 2:
               lang_key = "ar";
               break;
       }

       final Call<ProductsModel> productsCall = retrofit.productsAll(lang_key,Home.CATEGK_KEY.toString());

       productsCall.enqueue(new Callback<ProductsModel>() {
           @Override
           public void onResponse(Call<ProductsModel> call, Response<ProductsModel> response) {

               if (response.isSuccessful()) {
                   Toast.makeText(getActivity(), R.string.loading, Toast.LENGTH_SHORT).show();

                   mProductDataArrayList.addAll(response.body().getData());
                   mProductsRecyclerAdapter.notifyDataSetChanged();
                   pullRefreshLayout.setRefreshing(false);
               } else {
                   Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onFailure(Call<ProductsModel> call, Throwable t) {
               Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
           }
       });

   }

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