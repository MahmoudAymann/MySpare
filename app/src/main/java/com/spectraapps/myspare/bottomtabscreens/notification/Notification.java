package com.spectraapps.myspare.bottomtabscreens.notification;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.spectraapps.myspare.adapters.MahmodAdp;
import com.spectraapps.myspare.adapters.Notiu;
import com.spectraapps.myspare.adapters.RecyclerNotificationAdapter;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.model.NotificationModel;
import com.spectraapps.myspare.model.ProfileProdModel;
import com.spectraapps.myspare.network.MyRetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends Fragment {

    RecyclerView recyclerView;
    RecyclerNotificationAdapter recyclerNotificationAdapter;
    ArrayList<Notiu> notificationDataList;
    private ProgressDialog progressDialog;
    private PullRefreshLayout pullRefreshLayout;
    MahmodAdp mahmodAdp;
    NotificationModel.DataBean dataBean;

    public Notification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        fireBackButtonEvent();
        initUI(rootView);
        recyclerView = rootView.findViewById(R.id.notification_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(Notification.this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        //initAdapter();

        notificationDataList = new ArrayList<>();
        notificationDataList.add(new Notiu("namasdsadasdasdasdasde","19-5-8"));

        mahmodAdp = new MahmodAdp(notificationDataList);

        recyclerView.setAdapter(mahmodAdp);
       // serverNotifi();
        return rootView;

    }//end onCreateView

//    private void initAdapter() {
//
//        recyclerNotificationAdapter = new RecyclerNotificationAdapter(notificationDataList, new RecyclerNotificationAdapter.OnItemClickListener() {
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
                //serverNotifi();
                recyclerView.setAdapter(recyclerNotificationAdapter);
            }
        });
    }

//    private void serverNotifi() {
//        progressDialog.show();
//        notificationDataList = new ArrayList<>();
//        try {
//            Api retrofit = MyRetrofitClient.getBase().create(Api.class);
//
//            final Call<NotificationModel> notificationCall = retrofit.notification("26");
//
//            notificationCall.enqueue(new Callback<NotificationModel>() {
//                @Override
//                public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
//                    try {
//                        if (response.isSuccessful()) {
//                            notificationDataList.addAll(response.body().getData());
//                            pullRefreshLayout.setRefreshing(false);
//                            progressDialog.dismiss();
//                            recyclerNotificationAdapter.notifyDataSetChanged();
//                            //recyclerView.setAdapter(recyclerNotificationAdapter);
//                            recyclerNotificationAdapter.notifyDataSetChanged();
//                            Log.e("jkjk", response.body().getData().size() + "");
//                            Log.e("jkjk", notificationDataList.size() + "ss");
//                            Toast.makeText(getContext(), "sssss" + response.body().getStatus().getTitle(), Toast.LENGTH_LONG).show();
//
//                        } else {
//                            pullRefreshLayout.setRefreshing(false);
//                            progressDialog.dismiss();
//                            Toast.makeText(getActivity(), " " + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
//                        }
//                    } catch (Exception e) {
//                        Toast.makeText(getContext(), "NO DATA", Toast.LENGTH_LONG).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<NotificationModel> call, Throwable t) {
//                    Log.v("tagy", t.getMessage());
//                    pullRefreshLayout.setRefreshing(false);
//                    progressDialog.dismiss();
//                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            });
//        } catch (Exception e) {
//            pullRefreshLayout.setRefreshing(false);
//            progressDialog.dismiss();
//            Toast.makeText(getContext(), "Erorr:" + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }

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
