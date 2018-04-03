package com.spectraapps.myspare.adapters.adpFav;

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
import com.spectraapps.myspare.model.FavouriteModel;
import com.spectraapps.myspare.utility.ListSharedPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 03/01/2018.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {

    private Context context;
    private ListAllListeners listAllListeners;
    private ArrayList<FavouriteModel.DataBean> mFavArrayList;
    private ListSharedPreference.Set setSharedPreference ;
    private ListSharedPreference.Get getSharedPreference ;

    public FavouriteAdapter(Context context, ArrayList<FavouriteModel.DataBean> FavArrayList,
                            ListAllListeners listAllListeners) {
        this.mFavArrayList = FavArrayList;
        this.listAllListeners = listAllListeners;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_favourite_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (mFavArrayList != null)
            return mFavArrayList.size();
        else
            return 0;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        setSharedPreference = new ListSharedPreference.Set(context);
        getSharedPreference = new ListSharedPreference.Get(context);

        holder.nameTV.setText(mFavArrayList.get(position).getProductName());
        holder.priceTV.setText(mFavArrayList.get(position).getProductPrice());
        holder.currencyTV.setText(mFavArrayList.get(position).getCurrency());

        Picasso.with(holder.itemView.getContext())
                .load(mFavArrayList.get(position).getImage1())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(holder.imageView);


        if (mFavArrayList.get(holder.getAdapterPosition()).getIsFavorite().equals("true")) {
            setSharedPreference.setFav(mFavArrayList.get(holder.getAdapterPosition()).getPid(), "true");
            holder.btnFav.setImageResource(R.drawable.ic_favorite_full_24dp);
        } else {
            setSharedPreference.setFav(mFavArrayList.get(holder.getAdapterPosition()).getPid(), "false");
            holder.btnFav.setImageResource(R.drawable.ic_favorite_empty_24dp);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAllListeners.onCardViewClick(mFavArrayList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });

        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSharedPreference.getFav(mFavArrayList.get(holder.getAdapterPosition()).getPid()).equals("true")) {
                    holder.btnFav.setImageResource(R.drawable.ic_favorite_empty_24dp);
                    setSharedPreference.setFav(mFavArrayList.get(holder.getAdapterPosition()).getPid(), "false");
                    listAllListeners.onFavButtonClick(view, holder.getAdapterPosition(), false);
                } else {
                    holder.btnFav.setImageResource(R.drawable.ic_favorite_full_24dp);
                    setSharedPreference.setFav(mFavArrayList.get(holder.getAdapterPosition()).getPid(), "true");
                    listAllListeners.onFavButtonClick(view, holder.getAdapterPosition(), true);
                }
            }
        });

    }//end onBindViewHolder()

    public interface ListAllListeners {

        void onCardViewClick(FavouriteModel.DataBean favouriteDataBean, int adapterPosition);

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

            imageView = itemView.findViewById(R.id.fav_image);

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageView.setCornerRadiiDP(4, 4, 0, 0);
        }
    }//end class MyViewHolder

}//end class