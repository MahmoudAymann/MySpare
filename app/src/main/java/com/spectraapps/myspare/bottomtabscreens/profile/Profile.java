package com.spectraapps.myspare.bottomtabscreens.profile;

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

import com.spectraapps.myspare.R;
import com.spectraapps.myspare.adapters.RecyclerProfileAdapter;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.model.ProfileProdModel;
import com.spectraapps.myspare.network.MyRetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment
{

    RecyclerView recyclerView;
    RecyclerProfileAdapter mRecyclerProfileAdapter;
    ArrayList<ProfileProdModel.DataBean> mProfileDataList;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        recyclerView = rootView.findViewById(R.id.profile_recycler);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }

        mProfileDataList = new ArrayList<>();

        mRecyclerProfileAdapter = new RecyclerProfileAdapter(getActivity(), mProfileDataList,
                new RecyclerProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProfileProdModel.DataBean productsModel) {

            }
        }, new RecyclerProfileAdapter.OnFavClickListener() {
            @Override
            public void onFavClick(ProfileProdModel.DataBean productsModel) {

            }
        });

        recyclerView.setAdapter(mRecyclerProfileAdapter);

        serverProductsAll();


        return rootView;
    }//end oncreate

    private void serverProductsAll() {
        try {
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<ProfileProdModel> productsCall = retrofit.profile("en", "42");

            productsCall.enqueue(new Callback<ProfileProdModel>() {
                @Override
                public void onResponse(Call<ProfileProdModel> call, Response<ProfileProdModel> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), R.string.loading, Toast.LENGTH_SHORT).show();

                        mProfileDataList.addAll(response.body().getData());
                        mRecyclerProfileAdapter.notifyDataSetChanged();

                        Log.v("jkjk", response.body().getData().size() + "");
                        //pullRefreshLayout.setRefreshing(false);

                    } else {
                        //pullRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ProfileProdModel> call, Throwable t) {

                    Log.v("tagy", t.getMessage());
                    //pullRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            //pullRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), "Erorr:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}