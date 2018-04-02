package com.spectraapps.myspare.adapters.adpProfile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.model.ProfileProdModel;
import com.spectraapps.myspare.utility.ListSharedPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 03/01/2018.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {

    private ListAllListeners listAllListeners;
    private ArrayList<ProfileProdModel.DataBean> mProfileArrayList;
    private Context mContext;
    private ListSharedPreference.Set setSharedPreference ;
    private ListSharedPreference.Get getSharedPreference ;


    public ProfileAdapter(Context mContext, ArrayList<ProfileProdModel.DataBean> productsAllModelList,
                          ListAllListeners listAllListeners) {
        this.mContext = mContext;
        this.mProfileArrayList = productsAllModelList;
        this.listAllListeners = listAllListeners;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_profile_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (mProfileArrayList != null)
            return mProfileArrayList.size();
        else
            return 0;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        setSharedPreference = new ListSharedPreference.Set(mContext);
        getSharedPreference = new ListSharedPreference.Get(mContext);

        holder.nameTV.setText(mProfileArrayList.get(position).getProductName());
        holder.priceTV.setText(mProfileArrayList.get(position).getProductPrice());
        holder.currencyTV.setText(mProfileArrayList.get(position).getCurrency());

        Picasso.with(holder.itemView.getContext())
                .load(mProfileArrayList.get(position).getImage1())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(holder.imageView);


        if (mProfileArrayList.get(holder.getAdapterPosition()).getIsFavorite().equals("true")) {
            setSharedPreference.setFav(mProfileArrayList.get(holder.getAdapterPosition()).getPid(), "true");
            holder.btnFav.setImageResource(R.drawable.ic_favorite_full_24dp);
        } else {
            setSharedPreference.setFav(mProfileArrayList.get(holder.getAdapterPosition()).getPid(), "false");
            holder.btnFav.setImageResource(R.drawable.ic_favorite_empty_24dp);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAllListeners.onCardViewClick(mProfileArrayList.get(holder.getAdapterPosition()));
            }
        });

        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSharedPreference.getFav(mProfileArrayList.get(holder.getAdapterPosition()).getPid()).equals("true")) {
                    holder.btnFav.setImageResource(R.drawable.ic_favorite_empty_24dp);
                    setSharedPreference.setFav(mProfileArrayList.get(holder.getAdapterPosition()).getPid(), "false");
                    listAllListeners.onFavButtonClick(view, holder.getAdapterPosition(), false);
                } else {
                    holder.btnFav.setImageResource(R.drawable.ic_favorite_full_24dp);
                    setSharedPreference.setFav(mProfileArrayList.get(holder.getAdapterPosition()).getPid(), "true");
                    listAllListeners.onFavButtonClick(view, holder.getAdapterPosition(), true);
                }
            }
        });
    }//end onBindViewHolder()

    public interface ListAllListeners {

        void onCardViewClick(ProfileProdModel.DataBean produtsAllModel);

        void onFavButtonClick(View v, int position, boolean isFav);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, priceTV, currencyTV;
        SelectableRoundedImageView imageView;
        ImageButton btnFav;

        MyViewHolder(View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.textName);
            priceTV = itemView.findViewById(R.id.textPrice);
            currencyTV = itemView.findViewById(R.id.textCurrency);
            btnFav = itemView.findViewById(R.id.imageButtonFav);

            imageView = itemView.findViewById(R.id.image);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setCornerRadiiDP(4, 4, 0, 0);
        }
    }//end class MyViewHolder

}//end class