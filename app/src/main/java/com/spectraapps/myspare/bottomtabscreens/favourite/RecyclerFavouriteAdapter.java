package com.spectraapps.myspare.bottomtabscreens.favourite;

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

import com.spectraapps.myspare.helper.MenueHelper;
import com.spectraapps.myspare.R;

import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 03/01/2018.
 */

public class RecyclerFavouriteAdapter extends RecyclerView.Adapter<RecyclerFavouriteAdapter.MyViewHolder>{

    private Context mContext;
    private ArrayList<FavouriteData> mFavouriteDataList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FavouriteData favouriteData);
    }

    RecyclerFavouriteAdapter(Context context, ArrayList<FavouriteData> favouriteDataList, OnItemClickListener listener) {
        this.mFavouriteDataList = favouriteDataList;
        this.listener = listener;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_favourite_view, parent, false);
        return new RecyclerFavouriteAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        FavouriteData listy = mFavouriteDataList.get(position);
        holder.bind(listy, listener);

        holder.overflowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflowMenu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFavouriteDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  nameTextView;
        TextView  name2TextView;
        ImageView imageView;
        ImageView overflowMenu;
        MyViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name1);
            name2TextView = itemView.findViewById(R.id.name2);
            imageView = itemView.findViewById(R.id.thumbnail);
            overflowMenu = itemView.findViewById(R.id.overflow);
        }

        private void bind(final FavouriteData favouriteData, final OnItemClickListener listener) {
            nameTextView.setText(favouriteData.getName());
            name2TextView.setText(favouriteData.getName2());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    listener.onItemClick(favouriteData);
                }
            });
        }
    }//end class MyViewHolder
/////////////////////////////////////////////////////////////////////////////////
    //popup menu"overflow" click listeners.
    private void showPopupMenu(View view) {
        // inflate menu
        MenueHelper popup = new MenueHelper(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.fav_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements MenueHelper.OnMenuItemClickListener {

        MyMenuItemClickListener() {

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_delete:
                    //action delete here
                    Toast.makeText(mContext, "delete", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }

    }//end class menu
    //////////////////////////////////////////////

}//end class adapter
