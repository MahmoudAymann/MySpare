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
import com.github.kimkevin.cachepot.CachePot;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.adapters.adpFav.RecyclerFavouriteAdapter;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.model.FavouriteModel;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.utility.ListSharedPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favourite extends Fragment {
    RecyclerView recyclerView;
    RecyclerFavouriteAdapter recyclerFavouriteAdapter;
    ArrayList<FavouriteModel.DataBean> mFavouriteDataList;
    RecyclerFavouriteAdapter mFavouriteAdapter;

    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;

    ProgressDialog progressDialog;

    PullRefreshLayout pullRefreshLayout;

    String uEmail, language;

    public Favourite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);
        MainActivity.mToolbarText.setText(getString(R.string.favourite));

        setSharedPreference = new ListSharedPreference.Set(Favourite.this.getContext().getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(Favourite.this.getContext().getApplicationContext());

        fireBackButtonEvent();
        initUI(rootView);
        initRecyclerView();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        try {
            uEmail = getSharedPreference.getEmail();
            language = getSharedPreference.getLanguage();
        } catch (Exception e) {
            Toast.makeText(getContext(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        turnOnServer();
        initAdapterProfileProducts();
        recyclerView.setAdapter(recyclerFavouriteAdapter);
    }

    private void initUI(View rootView) {
        initPullRefreshLayout(rootView);
        recyclerView = rootView.findViewById(R.id.fav_recycler);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void initPullRefreshLayout(View rootView) {
        pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutFav);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                turnOnServer();
                mFavouriteAdapter.notifyDataSetChanged();
            }
        });
    }

    private void turnOnServer() {
        serverProfile();
    }

    private void initAdapterProfileProducts() {
        mFavouriteAdapter = new RecyclerFavouriteAdapter(mFavouriteDataList,
                new RecyclerFavouriteAdapter.ListAllListeners() {
                    @Override
                    public void onCardViewClick(FavouriteModel.DataBean favouriteDataBean) {
                        CachePot.getInstance().push("pName", favouriteDataBean.getProductName());
                        CachePot.getInstance().push("pId", favouriteDataBean.getProductNumber());
                        CachePot.getInstance().push("pPrice", favouriteDataBean.getProductPrice());
                        CachePot.getInstance().push("pNumber", favouriteDataBean.getProductNumber());
                        CachePot.getInstance().push("pCurrency", favouriteDataBean.getCurrency());
                        CachePot.getInstance().push("pImage1", favouriteDataBean.getImage1());
                        CachePot.getInstance().push("pImage2", favouriteDataBean.getImage2());
                        CachePot.getInstance().push("pDate", favouriteDataBean.getDate());
                        CachePot.getInstance().push("pCountry", favouriteDataBean.getCountry());
                        CachePot.getInstance().push("pBrand", favouriteDataBean.getBrand());
                        CachePot.getInstance().push("pModel", favouriteDataBean.getModel());

                        CachePot.getInstance().push("uMobile", favouriteDataBean.getMobile());
                        CachePot.getInstance().push("uName", favouriteDataBean.getUser_name());

                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_frameLayout, new FavProductDetail()).commit();

                    }

                    @Override
                    public void onFavButtonClick(View v, int position, boolean isFav) {
                        if (isFav)
                            serverAddToFav();
                        else
                            removeFromFav();
                    }
                });
    }

    private void removeFromFav() {
        Toast.makeText(getContext(), "removed", Toast.LENGTH_SHORT).show();
    }

    private void serverAddToFav() {
        Toast.makeText(getContext(), "added", Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }
    }//end initRecyclerView()

    private void serverProfile() {
        progressDialog.show();
        mFavouriteDataList = new ArrayList<>();
        try {
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<FavouriteModel> favouriteCall = retrofit.favourite("nashwaali@gmail.com", "en");

            favouriteCall.enqueue(new Callback<FavouriteModel>() {
                @Override
                public void onResponse(Call<FavouriteModel> call, Response<FavouriteModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            mFavouriteDataList.addAll(response.body().getData());
                            pullRefreshLayout.setRefreshing(false);
                            progressDialog.dismiss();

                            mFavouriteAdapter.notifyDataSetChanged();
                            Log.e("jkjk", response.body().getData().size() + "");
                            Log.e("jkjk", mFavouriteDataList.size() + "ss");
                            Toast.makeText(getContext(), "sssss" + response.body().getStatus().getTitle(), Toast.LENGTH_LONG).show();

                        } else {
                            pullRefreshLayout.setRefreshing(false);
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), " " + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "NO DATA" + e, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<FavouriteModel> call, Throwable t) {
                    Log.v("tagy", t.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            pullRefreshLayout.setRefreshing(false);
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Erorr: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }//end serverProfile()

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

}