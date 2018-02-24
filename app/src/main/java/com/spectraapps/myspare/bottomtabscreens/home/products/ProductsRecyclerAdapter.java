package com.spectraapps.myspare.bottomtabscreens.home.products;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.model.ProductsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 13/01/2018.
 */

public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.MyViewHolder>{

    private ArrayList<ProductsModel.DataBean> mProductsModelArrayList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ProductsModel.DataBean productsModel);
    }

    ProductsRecyclerAdapter(ArrayList<ProductsModel.DataBean> productsModelArrayList, OnItemClickListener listener) {
        this.mProductsModelArrayList = productsModelArrayList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_products_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mProductsModelArrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        if ( mProductsModelArrayList != null)
        return mProductsModelArrayList.size();
        else
            return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV,priceTV;
        SelectableRoundedImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.textName);
            priceTV = itemView.findViewById(R.id.textPrice);
            imageView = itemView.findViewById(R.id.image);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setCornerRadiiDP(4,4,0,0);
        }

        private void bind(final ProductsModel.DataBean productsModel, final OnItemClickListener listener) {

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
        }
    }
}