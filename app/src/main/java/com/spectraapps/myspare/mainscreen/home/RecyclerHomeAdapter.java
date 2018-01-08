package com.spectraapps.myspare.mainscreen.home;

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

public class RecyclerHomeAdapter extends RecyclerView.Adapter<RecyclerHomeAdapter.MyViewHolder> {

    private ArrayList<HomeData> mHomeDataList;
    private final OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(HomeData homeData);
    }

    RecyclerHomeAdapter(ArrayList<HomeData> mHomeDataList, OnItemClickListener listener) {
        this.mHomeDataList = mHomeDataList;
        this.listener = listener;
    }

    @Override
    public RecyclerHomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_home_view, parent, false);
        return new RecyclerHomeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHomeAdapter.MyViewHolder holder, int position) {
        holder.bind(mHomeDataList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mHomeDataList.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

         MyViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text);
            imageView = itemView.findViewById(R.id.main_image_view);
        }

         private void bind(final HomeData homeData, final OnItemClickListener listener) {
             nameTextView.setText("Car");
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                      listener.onItemClick(homeData);
                 }
             });
         }
     }
}