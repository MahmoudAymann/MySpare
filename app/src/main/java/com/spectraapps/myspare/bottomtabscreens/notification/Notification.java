package com.spectraapps.myspare.bottomtabscreens.notification;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.helper.BaseBackPressedListener;

import java.util.ArrayList;

public class Notification extends Fragment {

    RecyclerView recyclerView;
    RecyclerNotificationAdapter recyclerNotificationAdapter;
    ArrayList<NotificationData> notificationDataList;

    public Notification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        fireBackButtonEvent();

        recyclerView = rootView.findViewById(R.id.notification_recycler);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        }

        else
        {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        }

        recyclerNotificationAdapter = new RecyclerNotificationAdapter(notificationDataList, new RecyclerNotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NotificationData notificationData) {
                Toast.makeText(getActivity(), ""+notificationData.getDate(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(recyclerNotificationAdapter);
        recyclerNotificationAdapter.notifyDataSetChanged();

        return rootView;
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
}
