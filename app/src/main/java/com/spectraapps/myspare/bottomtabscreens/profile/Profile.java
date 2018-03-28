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
import com.spectraapps.myspare.model.AddToFavModel;
import com.spectraapps.myspare.model.ProfileProdModel;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.utility.ListSharedPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment {

    RecyclerView recyclerView;
    RecyclerProfileAdapter mRecyclerProfileAdapter;
    ArrayList<ProfileProdModel.DataBean> mProfileDataList;
    PullRefreshLayout pullRefreshLayout;

    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;

    String uEmail, language;
    private ProgressDialog progressDialog;
    private String uId;

    public Profile() {
        // Required empty public constructo
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        MainActivity.mToolbarText.setText(getString(R.string.profile_title));

        setSharedPreference = new ListSharedPreference.Set(Profile.this.getContext().getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(Profile.this.getContext().getApplicationContext());

        fireBackButtonEvent();
        initUI(rootView);
        initRecyclerView();

        return rootView;
    }//end oncreate

    @Override
    public void onStart() {
        super.onStart();

        try {
            uEmail = getSharedPreference.getEmail();
            uId = getSharedPreference.getUId();
            language = getSharedPreference.getLanguage();
        } catch (Exception e) {
            Toast.makeText(getContext(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        turnOnServer();
        initAdapterProfileProducts();
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
                    public void onCardViewClick(ProfileProdModel.DataBean profileProdData) {

                        CachePot.getInstance().push("pName", profileProdData.getProductName());
                        CachePot.getInstance().push("pId", profileProdData.getProductNumber());
                        CachePot.getInstance().push("pPrice", profileProdData.getProductPrice());
                        CachePot.getInstance().push("pNumber", profileProdData.getProductNumber());
                        CachePot.getInstance().push("pCurrency", profileProdData.getCurrency());

                        setSharedPreference.setimg1(profileProdData.getImage1());
                        setSharedPreference.setimg2(profileProdData.getImage2());

                        CachePot.getInstance().push("pDate", profileProdData.getDate());
                        CachePot.getInstance().push("pCountry",profileProdData.getCountry());
                        CachePot.getInstance().push("pBrand",  profileProdData.getBrand());
                        CachePot.getInstance().push("pModel",  profileProdData.getModel());

                        CachePot.getInstance().push("uEmail", profileProdData.getId());
                        CachePot.getInstance().push("uMobile", profileProdData.getMobile());
                        CachePot.getInstance().push("uName", profileProdData.getName());
                        CachePot.getInstance().push("uImage", profileProdData.getImage());

                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_frameLayout, new ProfileProductDetail()).commit();

                       Log.v("jkjkl", profileProdData.getImage() + " push ");
                       Log.v("jkjkl", profileProdData.getName() + profileProdData.getMobile() + "push");
                    }

                    @Override
                    public void onFavButtonClick(View v, int position, boolean isFav) {
                        if (isFav) {
                            serverAddToFav(mProfileDataList.get(position).getPid());
                        } else {
                            serverRemoveFromFav(mProfileDataList.get(position).getPid());
                        }

                    }
                });
    }

    private void serverRemoveFromFav(String pId) {
        progressDialog.show();
        mProfileDataList = new ArrayList<>();
        try {
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<AddToFavModel> addCall = retrofit.addToFavourite(uEmail,pId ,false);

            addCall.enqueue(new Callback<AddToFavModel>() {
                @Override
                public void onResponse(@NonNull Call<AddToFavModel> call, @NonNull Response<AddToFavModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            pullRefreshLayout.setRefreshing(false);
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "" +response.body().getStatus().getTitle(), Toast.LENGTH_LONG).show();
                        } else {
                            pullRefreshLayout.setRefreshing(false);
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), " " + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "NO DATA"+e, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AddToFavModel> call, @NonNull Throwable t) {
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

    }

    private void serverAddToFav(String pId) {
        try {
        progressDialog.show();
        mProfileDataList = new ArrayList<>();
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<AddToFavModel> addCall = retrofit.addToFavourite(uEmail,pId ,false);

            addCall.enqueue(new Callback<AddToFavModel>() {
                @Override
                public void onResponse(@NonNull Call<AddToFavModel> call, @NonNull Response<AddToFavModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            pullRefreshLayout.setRefreshing(false);
                            progressDialog.dismiss();
                        } else {
                            pullRefreshLayout.setRefreshing(false);
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), " "+response.body().getStatus().getTitle(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "NO DATA"+e, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AddToFavModel> call, @NonNull Throwable t) {
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

            final Call<ProfileProdModel> productsCall = retrofit.profile(uId, language);

            productsCall.enqueue(new Callback<ProfileProdModel>() {
                @Override
                public void onResponse(@NonNull Call<ProfileProdModel> call, @NonNull Response<ProfileProdModel> response) {
                    try {
                        if (response.isSuccessful())
                        {
                            mProfileDataList.addAll(response.body().getData());
                            pullRefreshLayout.setRefreshing(false);
                            progressDialog.dismiss();
                            recyclerView.setAdapter(mRecyclerProfileAdapter);
                            mRecyclerProfileAdapter.notifyDataSetChanged();
                            Log.e("jkjk", response.body().getData().size() + "");
                            Log.e("jkjk", mProfileDataList.size() + "ss");
                        } else {
                            pullRefreshLayout.setRefreshing(false);
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), " " + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ignored) {
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProfileProdModel> call, @NonNull Throwable t) {
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
                        .replace(R.id.main_frameLayout, new Home())
                        .commit();
            }
        });
    }//end back pressed

}