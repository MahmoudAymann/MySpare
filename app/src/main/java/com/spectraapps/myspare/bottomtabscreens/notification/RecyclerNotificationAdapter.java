package com.spectraapps.myspare.bottomtabscreens.notification;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spectraapps.myspare.R;

import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 02/01/2018.
 */

public class RecyclerNotificationAdapter extends RecyclerView.Adapter<RecyclerNotificationAdapter.MyViewHolder> {

    private final OnItemClickListener listener;
    private ArrayList<NotificationData> mNotificationDataList;

    RecyclerNotificationAdapter(ArrayList<NotificationData> mNotificationDataList, OnItemClickListener listener) {
        this.mNotificationDataList = mNotificationDataList;
        this.listener = listener;
    }

    @Override
    public RecyclerNotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_notification_view, parent, false);
        return new RecyclerNotificationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerNotificationAdapter.MyViewHolder holder, int position) {
        holder.bind(mNotificationDataList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        if ( mNotificationDataList != null) {
            return mNotificationDataList.size();
        }else
            return 0;
    }

    public interface OnItemClickListener {
        void onItemClick(NotificationData notificationData);
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textTextView,dateTextView;
        ImageView imageView;

         MyViewHolder(View itemView) {
            super(itemView);
            textTextView = itemView.findViewById(R.id.text_noti);
            dateTextView = itemView.findViewById(R.id.text_date_noti);
            imageView = itemView.findViewById(R.id.thumbnail_noti);
        }

         private void bind(final NotificationData notificationData, final OnItemClickListener listener) {
             textTextView.setText(notificationData.getText());
             dateTextView.setText(notificationData.getDate());

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                      listener.onItemClick(notificationData);
                 }
             });
         }
     }
}