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
import com.spectraapps.myspare.model.inproducts.ProductsAllModel;
import com.spectraapps.myspare.model.inproducts.ProductsModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favourite extends Fragment {
    RecyclerView recyclerView;
    RecyclerFavouriteAdapter recyclerFavouriteAdapter;
    ArrayList<ProductsAllModel.DataBean> mFavouriteDataList;

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
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }

        mFavouriteDataList = new ArrayList<>();
        recyclerFavouriteAdapter = new RecyclerFavouriteAdapter(getContext(), mFavouriteDataList, new RecyclerFavouriteAdapter.ListAllListeners() {
            @Override
            public void onCardViewClick(ProductsAllModel.DataBean produtsAllModel) {

            }

            @Override
            public void onFavButtonClick(View v, int position, boolean isFav) {

            }
        });

        recyclerView.setAdapter(recyclerFavouriteAdapter);
        recyclerFavouriteAdapter.notifyDataSetChanged();

        return rootView;
    }
}