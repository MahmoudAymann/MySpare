package com.spectraapps.myspare.bottomtabscreens.favourite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joooonho.SelectableRoundedImageView;
import com.spectraapps.myspare.helper.MenueHelper;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.model.inproducts.ProductsAllModel;
import com.spectraapps.myspare.model.inproducts.ProductsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 03/01/2018.
 */

public class RecyclerFavouriteAdapter extends RecyclerView.Adapter<RecyclerFavouriteAdapter.MyViewHolder>{

    ListAllListeners listAllListeners;
    private ArrayList<ProductsAllModel.DataBean> mProductsAllModelList;
    private Context mContext;
    private boolean isFav = false;


    public RecyclerFavouriteAdapter(Context mContext, ArrayList<ProductsAllModel.DataBean> productsAllModelList,
                              ListAllListeners listAllListeners) {
        this.mContext = mContext;
        this.mProductsAllModelList = productsAllModelList;
        this.listAllListeners = listAllListeners;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_favourite_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (mProductsAllModelList != null)
            return mProductsAllModelList.size();
        else
            return 5;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.nameTV.setText(mProductsAllModelList.get(position).getProductName());
        holder.priceTV.setText(mProductsAllModelList.get(position).getProductPrice());

        Picasso.with(holder.itemView.getContext())
                .load(mProductsAllModelList.get(position).getImage1())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAllListeners.onCardViewClick(mProductsAllModelList.get(holder.getAdapterPosition()));
            }
        });

        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isFav) {
                    holder.btnFav.setImageResource(R.drawable.ic_favorite_empty_24dp);
                    isFav = false;
                } else {
                    holder.btnFav.setImageResource(R.drawable.ic_favorite_full_24dp);
                    isFav = true;
                }
                listAllListeners.onFavButtonClick(view, holder.getAdapterPosition(), isFav);
            }
        });
    }//end onBindViewHolder()

    public interface ListAllListeners {

        void onCardViewClick(ProductsAllModel.DataBean produtsAllModel);

        void onFavButtonClick(View v, int position, boolean isFav);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, priceTV;
        SelectableRoundedImageView imageView;
        ImageButton btnFav;


        MyViewHolder(View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.textName);
            priceTV = itemView.findViewById(R.id.textPrice);

            btnFav = itemView.findViewById(R.id.imageButtonFav);

            imageView = itemView.findViewById(R.id.image);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setCornerRadiiDP(4, 4, 0, 0);
        }
    }//end class MyViewHolder

}//end class