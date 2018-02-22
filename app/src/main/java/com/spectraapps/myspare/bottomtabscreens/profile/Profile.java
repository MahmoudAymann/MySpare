package com.spectraapps.myspare.bottomtabscreens.profile;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spectraapps.myspare.R;

import java.util.ArrayList;

public class Profile extends Fragment
{

    RecyclerView recyclerView;
    RecyclerProfileAdapter mRecyclerProfileAdapter;
    ArrayList<ProfileData> mProfileDataList;

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

        mRecyclerProfileAdapter = new RecyclerProfileAdapter(getActivity(),mProfileDataList, new RecyclerProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProfileData profileData) {
                Toast.makeText(getActivity(), ""+profileData.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(mRecyclerProfileAdapter);
        mRecyclerProfileAdapter.notifyDataSetChanged();

        return rootView;
    }
}