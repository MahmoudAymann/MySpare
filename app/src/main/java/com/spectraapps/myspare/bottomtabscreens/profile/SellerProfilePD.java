package com.spectraapps.myspare.bottomtabscreens.profile;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.spectraapps.myspare.adapters.adpProfile.RecyclerProfileAdapter;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.model.ProfileProdModel;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.utility.ListSharedPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerProfilePD extends Fragment {

    RecyclerView recyclerView;
    RecyclerProfileAdapter mRecyclerProfileAdapter;
    ArrayList<ProfileProdModel.DataBean> mProfileDataList;
    PullRefreshLayout pullRefreshLayout;

    String uId;
    private ProgressDialog progressDialog;
    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;

    public SellerProfilePD() {
        // Required empty public constructo
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_seller_profile, container, false);
        MainActivity.mToolbarText.setText(getString(R.string.profile_title));

        setSharedPreference = new ListSharedPreference.Set(SellerProfilePD.this.getContext().getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(SellerProfilePD.this.getContext().getApplicationContext());


        fireBackButtonEvent();
        initUI(rootView);
        initRecyclerView();

        try {
            uId = CachePot.getInstance().pop("suid");

        } catch (Exception e) {
            Toast.makeText(getContext(), "exc: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        return rootView;
    }//end oncreate

    @Override
    public void onStart() {
        super.onStart();
        turnOnServer();
        initAdapterProfileProducts();
        recyclerView.setAdapter(mRecyclerProfileAdapter);
    }

    private String getLang() {
        return getSharedPreference.getLanguage();
    }

    private void initUI(View rootView) {
        initPullRefreshLayout(rootView);
        recyclerView = rootView.findViewById(R.id.profile_recycler);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);

    }

    private void initPullRefreshLayout(View rootView) {
        pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutProfile);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                turnOnServer();
            }
        });
    }

    private void turnOnServer() {

        serverProfile();

    }

    private void initAdapterProfileProducts() {
        mRecyclerProfileAdapter = new RecyclerProfileAdapter(getContext(), mProfileDataList,
                new RecyclerProfileAdapter.ListAllListeners() {
                    @Override
                    public void onCardViewClick(ProfileProdModel.DataBean produtsAllModel) {

                        CachePot.getInstance().push("pName", produtsAllModel.getProductName());
                        CachePot.getInstance().push("pId", produtsAllModel.getProductNumber());
                        CachePot.getInstance().push("pPrice", produtsAllModel.getProductPrice());
                        CachePot.getInstance().push("pNumber", produtsAllModel.getProductNumber());
                        CachePot.getInstance().push("pCurrency", produtsAllModel.getCurrency());

                        if (produtsAllModel.getImage1() != null)
                            CachePot.getInstance().push("pImage1", produtsAllModel.getImage1());
                        if (produtsAllModel.getImage2() != null)
                            CachePot.getInstance().push("pImage2", produtsAllModel.getImage2());

                        CachePot.getInstance().push("pDate", produtsAllModel.getDate());
                        CachePot.getInstance().push("pCountry", produtsAllModel.getCountry());
                        CachePot.getInstance().push("pBrand", produtsAllModel.getBrand());
                        CachePot.getInstance().push("pModel", produtsAllModel.getModel());

                        CachePot.getInstance().push("uEmail", produtsAllModel.getId());
                        CachePot.getInstance().push("uMobile", produtsAllModel.getMobile());
                        CachePot.getInstance().push("uName", produtsAllModel.getName());
                        CachePot.getInstance().push("uImage", produtsAllModel.getImage());

                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_frameLayout, new ProfileProductDetail()).commit();


                        Log.v("jkjkl", produtsAllModel.getImage1() + " push ");
                        Log.v("jkjkl", produtsAllModel.getImage2() + "  push");
                    }

                    @Override
                    public void onFavButtonClick(View v, int position, boolean isFav) {
                        if (isFav) {
                            serverAddToFav();
                        } else {
                            removeFromFav();
                        }

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
        mProfileDataList = new ArrayList<>();
        try {
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<ProfileProdModel> productsCall = retrofit.profile(uId, getLang());

            productsCall.enqueue(new Callback<ProfileProdModel>() {
                @Override
                public void onResponse(@NonNull Call<ProfileProdModel> call, @NonNull Response<ProfileProdModel> response) {

                    if (response.isSuccessful()) {
                        mProfileDataList.addAll(response.body().getData());
                        pullRefreshLayout.setRefreshing(false);
                        progressDialog.dismiss();
                        mRecyclerProfileAdapter.notifyDataSetChanged();
                    } else {
                        pullRefreshLayout.setRefreshing(false);
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), " " + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ProfileProdModel> call, Throwable t) {
                    Log.v("tagy", t.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            pullRefreshLayout.setRefreshing(false);
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Erorr:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }//end serverProfile()

    private void fireBackButtonEvent() {
        ((MainActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()) {
            @Override
            public void onBackPressed() {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new Profile())
                        .commit();
            }
        });
    }//end back pressed

}