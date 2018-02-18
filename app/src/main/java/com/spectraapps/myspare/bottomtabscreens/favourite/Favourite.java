package com.spectraapps.myspare.bottomtabscreens.favourite;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.baoyz.widget.RefreshDrawable;
import com.spectraapps.myspare.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favourite extends Fragment {
    RecyclerView recyclerView;
    RecyclerFavouriteAdapter recyclerFavouriteAdapter;
    ArrayList<FavouriteData> mFavouriteDataList;

    public Favourite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);

        PullRefreshLayout pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutFav);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // startRefresh
                recyclerFavouriteAdapter.notifyDataSetChanged();
            }
        });
        // refresh complete
        pullRefreshLayout.setRefreshing(false);

        recyclerView = rootView.findViewById(R.id.fav_recycler);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }

        else
        {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }

        mFavouriteDataList = new ArrayList<FavouriteData>();
        mFavouriteDataList.add(new FavouriteData("اسم المنتج","19/2/2017"));
        mFavouriteDataList.add(new FavouriteData("اسم المنتج","18/3/2016"));
        mFavouriteDataList.add(new FavouriteData("اسم المنتج","1/9/1995"));
        mFavouriteDataList.add(new FavouriteData("اسم المنتج","2/2/2014"));
        mFavouriteDataList.add(new FavouriteData("اسم المنتج","4/9/2017"));
        mFavouriteDataList.add(new FavouriteData("اسم المنتج","7/1/2017"));
        mFavouriteDataList.add(new FavouriteData("اسم المنتج","7/1/2017"));

        recyclerFavouriteAdapter = new RecyclerFavouriteAdapter(getActivity(),mFavouriteDataList, new RecyclerFavouriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FavouriteData favouriteData) {
                Toast.makeText(getActivity(), ""+favouriteData.getName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), ""+favouriteData.getName2(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(recyclerFavouriteAdapter);
        recyclerFavouriteAdapter.notifyDataSetChanged();

        return rootView;
    }
}