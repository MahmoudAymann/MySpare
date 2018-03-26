package com.spectraapps.myspare.adapters.adpProducts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joooonho.SelectableRoundedImageView;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.adapters.adpProfile.RecyclerProfileAdapter;
import com.spectraapps.myspare.model.ProfileProdModel;
import com.spectraapps.myspare.model.inproducts.ProductsModel;
import com.spectraapps.myspare.utility.ListSharedPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 13/01/2018.
 */

public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.MyViewHolder> {

    private ListAllListeners listAllListeners;
    private ArrayList<ProductsModel.DataBean> mProductsModelList;
    private Context mContext;


    public ProductsRecyclerAdapter(Context mContext, ArrayList<ProductsModel.DataBean> productsModelArrayList, ListAllListeners listener) {
        this.mProductsModelList = productsModelArrayList;
        this.listAllListeners = listener;
        this.mContext = mContext;
    }

    public interface ListAllListeners {

        void onCardViewClick(ProductsModel.DataBean produtsAllModel);

        void onFavButtonClick(View v, int position, boolean isFav);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_products_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.nameTV.setText(mProductsModelList.get(position).getProductName());
        holder.priceTV.setText(mProductsModelList.get(position).getProductPrice());
        holder.currencyTV.setText(mProductsModelList.get(position).getCurrency());

        Picasso.with(mContext).load(mProductsModelList.get(position).getImage1())
                .error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder)
                .into(holder.imageView);

        if (!new ListSharedPreference.Get(mContext).getFav(mProductsModelList.get(holder.getAdapterPosition()).getId()).equals("true"))
            holder.btnFav.setImageResource(R.drawable.ic_favorite_empty_24dp);
        else
            holder.btnFav.setImageResource(R.drawable.ic_favorite_full_24dp);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAllListeners.onCardViewClick(mProductsModelList.get(holder.getAdapterPosition()));
            }
        });

        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new ListSharedPreference.Get(mContext).getFav(mProductsModelList.get(holder.getAdapterPosition()).getPid()).equals("true")) {
                    holder.btnFav.setImageResource(R.drawable.ic_favorite_full_24dp);
                    new ListSharedPreference.Set(mContext).setFav(mProductsModelList.get(holder.getAdapterPosition()).getPid(), "false");
                    listAllListeners.onFavButtonClick(view, holder.getAdapterPosition(), false);

                } else {
                    holder.btnFav.setImageResource(R.drawable.ic_favorite_empty_24dp);
                    new ListSharedPreference.Set(mContext).setFav(mProductsModelList.get(holder.getAdapterPosition()).getPid(), "true");
                    listAllListeners.onFavButtonClick(view, holder.getAdapterPosition(), true);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mProductsModelList != null)
            return mProductsModelList.size();
        else
            return 0;
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

    }//end MyViewHolder
}//end class ProdutsRecyclerAdpapter