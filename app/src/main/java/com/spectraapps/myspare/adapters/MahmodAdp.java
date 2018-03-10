package com.spectraapps.myspare.adapters;

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
 * Created by mahmo on 3/10/2018.
 */

public class MahmodAdp extends RecyclerView.Adapter<MahmodAdp.MyViewHolder> {


    private ArrayList<Notiu> mNotificationDataList;

    public MahmodAdp (ArrayList<Notiu> mNotificationDataList) {
        this.mNotificationDataList = mNotificationDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_notification_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mNotificationDataList.get(position));
    }

    @Override
    public int getItemCount() {
        if ( mNotificationDataList != null) {
            return mNotificationDataList.size();
        }else
            return 4;
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

        private void bind(final Notiu notificationModel) {
            textTextView.setText(notificationModel.ahmed);
            dateTextView.setText(notificationModel.date);
//
//            Picasso.with(itemView.getContext())
//                    .load(notificationModel.getImage())
//                    .placeholder(R.drawable.place_holder)
//                    .error(R.drawable.place_holder)
//                    .into(imageView);
        }
    }

}