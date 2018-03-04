package com.spectraapps.myspare.bottomtabscreens.home;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.spectraapps.myspare.ListSharedPreference;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.SplashScreen;
import com.spectraapps.myspare.adapters.HomeAdapter;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.login.LoginActivity;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.model.CategoriesModel;
import com.spectraapps.myspare.products.ProductsFragment;
import com.spectraapps.myspare.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    View rootView;
    ListSharedPreference listSharedPreference = new ListSharedPreference();
    ProgressDialog progressDialog;
    PullRefreshLayout pullRefreshLayout;

    ArrayList<CategoriesModel.DataBean> dataBeanArrayList;
    HomeAdapter homeAdapter;
    RecyclerView recyclerView;

    HomeCallBack homeCallBack;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        setData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeCallBack = (HomeCallBack) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        MainActivity.mToolbarText.setText(R.string.home_title);

        onTouchEvent(rootView, true);


        fireBackButtonEvent();
        initUI(rootView);
        initRecyclerView();

        serverCategories();

        return rootView;
    }

    public boolean onTouchEvent(View view, boolean isTouchable) {

        if (!isTouchable) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }
        return false;
    }

    private void serverCategories() {
        progressDialog.show();
        dataBeanArrayList = new ArrayList<>();
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<CategoriesModel> categoriesCall = retrofit.categories(getLang_key());
        categoriesCall.enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {

                if (response.isSuccessful()) {
                    dataBeanArrayList.addAll(response.body().getData());
                    pullRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();
                    homeAdapter.notifyDataSetChanged();
                } else {
                    progressDialog.dismiss();
                    pullRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<CategoriesModel> call, Throwable t) {
                progressDialog.dismiss();
                pullRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }//end serverCategories

    private String getLang_key() {
        switch (listSharedPreference.getLanguage(Home.this.getActivity().getApplicationContext())) {
            case "en":
                return "en";
            case "ar":
                return "ar";

            default:
                return "en";
        }
    }

    private void fireBackButtonEvent() {
        ((MainActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()) {
            @Override
            public void onBackPressed() {
                getActivity().finish();
            }
        });
    }//end back pressed

    private void initUI(View rootView) {
        initPullRefreshLayout(rootView);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        recyclerView = rootView.findViewById(R.id.home_recycler);

    }//end initUI

    private void initRecyclerView() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        recyclerView.setNestedScrollingEnabled(false);
    }//end initRecyclerView()

    private void setData() {

        homeAdapter = new HomeAdapter(dataBeanArrayList, new HomeAdapter.ListAllListeners() {
            @Override
            public void onCardViewClick(CategoriesModel.DataBean categoriesModel) {
                homeCallBack.HomeFrag(categoriesModel.getId());
            }
        });
        recyclerView.setAdapter(homeAdapter);
    }

    private void initPullRefreshLayout(final View rootView) {

        pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutHome);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                serverCategories();
                onTouchEvent(rootView, false);
            }
        });
    }


    public interface HomeCallBack {
        void HomeFrag(String categ);
    }


}//end Home