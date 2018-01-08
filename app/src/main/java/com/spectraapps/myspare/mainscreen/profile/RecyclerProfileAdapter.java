package com.spectraapps.myspare.mainscreen.profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spectraapps.myspare.R;
import com.spectraapps.myspare.helper.MenueHelper;
import com.spectraapps.myspare.mainscreen.favourite.FavouriteData;

import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 03/01/2018.
 */

public class RecyclerProfileAdapter extends RecyclerView.Adapter<RecyclerProfileAdapter.MyViewHolder>{

    private Context mContext;
    private ArrayList<ProfileData> mProfileDataList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ProfileData profileData);
    }

    RecyclerProfileAdapter(Context context, ArrayList<ProfileData> profileDataList, OnItemClickListener listener) {
        this.mProfileDataList = profileDataList;
        this.listener = listener;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_profile_view, parent, false);
        return new RecyclerProfileAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ProfileData listy = mProfileDataList.get(position);
        holder.bind(listy, listener);
    }

    @Override
    public int getItemCount() {
        return mProfileDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  nameTextView;
        ImageView imageView;
        ImageView overflowMenu;
        MyViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name1);
            imageView = itemView.findViewById(R.id.thumbnail);
        }

        private void bind(final ProfileData profileData, final OnItemClickListener listener) {
            nameTextView.setText(profileData.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    listener.onItemClick(profileData);
                }
            });
        }
    }//end class MyViewHolder

}//end class adapter
