package com.spectraapps.myspare.bottomtabscreens.home;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.spectraapps.myspare.products.ProductsFragment;
import com.spectraapps.myspare.utility.ListSharedPreference;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.adapters.adpHome.HomeAdapter;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.model.CategoriesModel;
import com.spectraapps.myspare.R;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    View rootView;
    ProgressDialog progressDialog;
    PullRefreshLayout pullRefreshLayout;

    ArrayList<CategoriesModel.DataBean> dataBeanArrayList;
    HomeAdapter homeAdapter;
    RecyclerView recyclerView;

    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        serverCategories();
        setData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        MainActivity.mToolbarText.setText(R.string.home_title);
        setSharedPreference = new ListSharedPreference.Set(Home.this.getContext().getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(Home.this.getContext().getApplicationContext());

        fireBackButtonEvent();
        initUI(rootView);
        initRecyclerView();

        return rootView;
    }

    public void onTouchEvent(View view, boolean isTouchable) {
        if (!isTouchable) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }
    }

    private void serverCategories() {
        progressDialog.show();
        dataBeanArrayList = new ArrayList<>();
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<CategoriesModel> categoriesCall = retrofit.categories(getLang_key());
        categoriesCall.enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesModel> call, @NonNull Response<CategoriesModel> response) {

                if (response.isSuccessful()) {
                    dataBeanArrayList.addAll(response.body().getData());
                    pullRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();
                    homeAdapter.notifyDataSetChanged();
                } else {
                    progressDialog.dismiss();
                    pullRefreshLayout.setRefreshing(false);
                    if (response.body() != null)
                        Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<CategoriesModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                pullRefreshLayout.setRefreshing(false);
            }
        });

    }//end serverCategories

    private String getLang_key() {
        return getSharedPreference.getLanguage();
    }

    private void fireBackButtonEvent() {
        ((MainActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()) {
            @Override
            public void onBackPressed() {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }//end back pressed

    private void initUI(View rootView) {
        MainActivity.imageButtonFav.setVisibility(View.INVISIBLE);
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
                setSharedPreference.setCategory(categoriesModel.getId());
                setSharedPreference.setCategoryName(categoriesModel.getName());
                setSharedPreference.setKeyFilter(0);

                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new ProductsFragment()).commit();


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
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new Home()).commit();
            }
        });
    }


}//end Home