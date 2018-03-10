package com.spectraapps.myspare.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spectraapps.myspare.R;
import com.spectraapps.myspare.model.NotificationModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 02/01/2018.
 */

public class RecyclerNotificationAdapter extends RecyclerView.Adapter<RecyclerNotificationAdapter.MyViewHolder> {

    private final OnItemClickListener listener;
    private ArrayList<NotificationModel.DataBean> mNotificationDataList;

    public RecyclerNotificationAdapter(ArrayList<NotificationModel.DataBean> mNotificationDataList, OnItemClickListener listener) {
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
        void onItemClick(NotificationModel.DataBean notificationData);
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

         private void bind(final NotificationModel.DataBean notificationModel, final OnItemClickListener listener) {
             textTextView.setText(notificationModel.getText());
             dateTextView.setText(notificationModel.getCreated_at());

             Picasso.with(itemView.getContext())
                     .load(notificationModel.getImage())
                     .placeholder(R.drawable.place_holder)
                     .error(R.drawable.place_holder)
                     .into(imageView);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                      listener.onItemClick(notificationModel);
                 }
             });
         }
     }
}