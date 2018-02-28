package com.spectraapps.myspare.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joooonho.SelectableRoundedImageView;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.model.inproducts.ProductsAllModel;
import com.spectraapps.myspare.model.inproducts.ProductsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 13/01/2018.
 */

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.MyViewHolder> {

    private final OnItemClickListener listener;
    private final OnFavClickListener favListener;
    private ArrayList<ProductsAllModel.DataBean> mProductsModelList;

    private Context mContext;

    public AllProductsAdapter(Context mContext, ArrayList<ProductsAllModel.DataBean> productsModelArrayList, OnItemClickListener listener, OnFavClickListener favListener) {
        this.mProductsModelList = productsModelArrayList;
        this.listener = listener;
        this.mContext = mContext;
        this.favListener = favListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_products_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.bind(mProductsModelList.get(position), listener, favListener);
    }

    @Override
    public int getItemCount() {
        if (mProductsModelList != null)
            return mProductsModelList.size();
        else
            return 0;
    }

    //Onitemclickli
    public interface OnItemClickListener {
        void onItemClick(ProductsAllModel.DataBean productsModel);
    }

    public interface OnFavClickListener {
        void onFavClick(View view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV, priceTV;
        SelectableRoundedImageView imageView;
        ImageButton btnFav;
        boolean isFav;
        MyViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.textName);
            priceTV = itemView.findViewById(R.id.textPrice);
            btnFav = itemView.findViewById(R.id.imageButtonFav);
            imageView = itemView.findViewById(R.id.image);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setCornerRadiiDP(4, 4, 0, 0);
        }

        private void bind(final ProductsAllModel.DataBean productsModel, final OnItemClickListener listener, final OnFavClickListener onFavClickListener) {

            nameTV.setText(productsModel.getName());
            priceTV.setText(productsModel.getProductPrice());

            Picasso.with(itemView.getContext())
                    .load(productsModel.getImage1())
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder)
                    .into(imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(productsModel);
                }
            });

            btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onFavClickListener.onFavClick(view);
                }
            });

        }
    }
}