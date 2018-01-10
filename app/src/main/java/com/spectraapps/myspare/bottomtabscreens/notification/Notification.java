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

import com.spectraapps.myspare.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
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

        recyclerView = rootView.findViewById(R.id.notification_recycler);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        }
        else
        {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        }

        notificationDataList = new ArrayList<>();
        notificationDataList.add(new NotificationData("19/5/2017","ولذلك يجب على جميع الرواد بالصعود فورا لمالاقاه الحدث العظيم المبجل الذى لم يتم الصعود إليه من قبل."));
        notificationDataList.add(new NotificationData("19/5/2017","ولذلك يجب على جميع الرواد بالصعود فورا لمالاقاه الحدث العظيم المبجل الذى لم يتم الصعود إليه من قبل."));
        notificationDataList.add(new NotificationData("19/5/2017","ولذلك يجب على جميع الرواد بالصعود فورا لمالاقاه الحدث العظيم المبجل الذى لم يتم الصعود إليه من قبل."));
        notificationDataList.add(new NotificationData("19/5/2017","ولذلك يجب على جميع الرواد بالصعود فورا لمالاقاه الحدث العظيم المبجل الذى لم يتم الصعود إليه من قبل."));
        notificationDataList.add(new NotificationData("19/5/2017","ولذلك يجب على جميع الرواد بالصعود فورا لمالاقاه الحدث العظيم المبجل الذى لم يتم الصعود إليه من قبل."));
        notificationDataList.add(new NotificationData("19/5/2017","ولذلك يجب على جميع الرواد بالصعود فورا لمالاقاه الحدث العظيم المبجل الذى لم يتم الصعود إليه من قبل."));
        notificationDataList.add(new NotificationData("19/5/2017","ولذلك يجب على جميع الرواد بالصعود فورا لمالاقاه الحدث العظيم المبجل الذى لم يتم الصعود إليه من قبل."));
        notificationDataList.add(new NotificationData("19/5/2017","ولذلك يجب على جميع الرواد بالصعود فورا لمالاقاه الحدث العظيم المبجل الذى لم يتم الصعود إليه من قبل."));


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

}
