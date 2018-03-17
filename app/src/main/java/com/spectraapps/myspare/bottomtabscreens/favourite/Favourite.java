package com.spectraapps.myspare.bottomtabscreens.favourite;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.adapters.adpFav.RecyclerFavouriteAdapter;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.model.AddToFavModel;
import com.spectraapps.myspare.model.FavouriteModel;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.utility.ListSharedPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Favourite extends Fragment {

    RecyclerView recyclerView;
    RecyclerFavouriteAdapter mFavAdapter;
    ArrayList<FavouriteModel.DataBean> mFavDataList;

    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;

    ProgressDialog progressDialog;

    PullRefreshLayout pullRefreshLayout;

    public Favourite() {
        // Required empty public constructor

    }

    @Override
    public void onStart() {
        super.onStart();
        serverFavourites();
        initAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);

        MainActivity.mToolbarText.setText(R.string.favourite);
        setSharedPreference = new ListSharedPreference.Set(Favourite.this.getContext().getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(Favourite.this.getContext().getApplicationContext());

        fireBackButtonEvent();
        initUI(rootView);
        initRecyclerView();
        return rootView;
    }

    private void serverFavourites() {
        progressDialog.show();
        mFavDataList = new ArrayList<>();
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<FavouriteModel> categoriesCall = retrofit.favourite(getUEmail(),getLang_key());
        categoriesCall.enqueue(new Callback<FavouriteModel>() {
            @Override
            public void onResponse(Call<FavouriteModel> call, Response<FavouriteModel> response) {

                if (response.isSuccessful()) {
                    mFavDataList.addAll(response.body().getData());
                    pullRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();
                    recyclerView.setAdapter(mFavAdapter);
                    mFavAdapter.notifyDataSetChanged();
                } else {
                    progressDialog.dismiss();
                    pullRefreshLayout.setRefreshing(false);
                    Log.v( "titler" ," "+response.body().getStatus().getTitle());
                    Toast.makeText(getContext(), ""+response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FavouriteModel> call, Throwable t) {
                progressDialog.dismiss();
                pullRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }//end serverCategories


    private String getUEmail() {
        Log.v("tagLang",getSharedPreference.getEmail());
        return getSharedPreference.getEmail();
    }

    private String getLang_key() {
        Log.v("tagLang",getSharedPreference.getLanguage());
       return getSharedPreference.getLanguage();
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

    private void initUI(View rootView) {
        initPullRefreshLayout(rootView);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        recyclerView = rootView.findViewById(R.id.fav_recycler);

    }//end initUI

    private void initRecyclerView() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }
        recyclerView.setNestedScrollingEnabled(false);
    }//end initRecyclerView()

    private void initAdapter() {
        mFavAdapter = new RecyclerFavouriteAdapter(Favourite.this.getContext(),mFavDataList, new RecyclerFavouriteAdapter.ListAllListeners() {
            @Override
            public void onCardViewClick(FavouriteModel.DataBean favModel) {

            }

            @Override
            public void onFavButtonClick(View v, int position, boolean isFav) {
                if (isFav){
                    serverAddToFav(position);
                    //Toast.makeText(getContext(), ""+isFav, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Toast.makeText(getContext(), ""+isFav, Toast.LENGTH_SHORT).show();
                    serverRemoveFromFav(position);
                }
            }
        });
    }

    private void serverRemoveFromFav(int position) {

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        final Call<AddToFavModel> productsCall = retrofit.addToFavourite(getUEmail(), mFavDataList.get(position).getPid(),false);

        productsCall.enqueue(new Callback<AddToFavModel>() {
            @Override
            public void onResponse(Call<AddToFavModel> call, Response<AddToFavModel> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(getContext(), ""+response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                   serverFavourites();

                } else {
                    Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddToFavModel> call, Throwable t) {
                Log.v("tagy", t.getMessage());
                pullRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void serverAddToFav(int position) {
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        final Call<AddToFavModel> productsCall = retrofit.addToFavourite(getUEmail(), mFavDataList.get(position).getPid(),true);

        productsCall.enqueue(new Callback<AddToFavModel>() {
            @Override
            public void onResponse(Call<AddToFavModel> call, Response<AddToFavModel> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(getContext(), ""+response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                    serverFavourites();
                } else {
                    Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddToFavModel> call, Throwable t) {
                Log.v("tagy", t.getMessage());
                pullRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initPullRefreshLayout(final View rootView) {

        pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutFav);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_CIRCLES);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                serverFavourites();
                initAdapter();
            }
        });
    }

}//end Home