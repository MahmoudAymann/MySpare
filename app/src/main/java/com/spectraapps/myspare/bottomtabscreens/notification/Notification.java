package com.spectraapps.myspare.bottomtabscreens.notification;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.adapters.NotificationAdapter;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.bottomtabscreens.profile.SellerProfilePD;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.model.NotificationModel;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.utility.ListSharedPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends Fragment {

    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    ArrayList<NotificationModel.DataBean> notificationDataList;
    private ProgressDialog progressDialog;
    private PullRefreshLayout pullRefreshLayout;

    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;

    public Notification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        fireBackButtonEvent();
        setSharedPreference = new ListSharedPreference.Set(Notification.this.getContext().getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(Notification.this.getContext().getApplicationContext());

        initUI(rootView);
        recyclerView = rootView.findViewById(R.id.notification_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(Notification.this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);



        //recyclerView.setAdapter(notificationAdapter);
        serverNotifi();
        notificationAdapter = new NotificationAdapter(notificationDataList, new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NotificationModel.DataBean notificationData) {

            }
        });
        return rootView;

    }//end onCreateView

//    private void initAdapter() {
//
//        notificationAdapter = new NotificationAdapter(notificationDataList, new NotificationAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(NotificationModel.DataBean notificationData) {
//                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void initPullRefreshLayout(View rootView) {
        pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutProfile);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                serverNotifi();
            }
        });
    }

    private void serverNotifi() {
        try {
        progressDialog.show();
        notificationDataList = new ArrayList<>();
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<NotificationModel> notificationCall = retrofit.notification("1");

            notificationCall.enqueue(new Callback<NotificationModel>() {
                @Override
                public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {

                        if (response.isSuccessful()) {
                            notificationDataList.addAll(response.body().getData());
                            pullRefreshLayout.setRefreshing(false);
                            progressDialog.dismiss();
                            notificationAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(notificationAdapter);
                            //Toast.makeText(getContext(), "sssss" + response.body().getStatus().getTitle(), Toast.LENGTH_LONG).show();

                        } else {
                            pullRefreshLayout.setRefreshing(false);
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), " " + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }

                }

                @Override
                public void onFailure(Call<NotificationModel> call, Throwable t) {
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
    }

}
