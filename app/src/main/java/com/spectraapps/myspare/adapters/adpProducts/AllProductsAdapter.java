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
import com.spectraapps.myspare.model.inproducts.ProductsAllModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 13/01/2018.
 */

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.MyViewHolder> {

    private ListAllListeners listAllListeners;
    private ArrayList<ProductsAllModel.DataBean> mProductsAllModelList;
    private Context mContext;

    public AllProductsAdapter(Context mContext, ArrayList<ProductsAllModel.DataBean> productsAllModelList,
                              ListAllListeners listAllListeners) {
        this.mContext = mContext;
        this.mProductsAllModelList = productsAllModelList;
        this.listAllListeners = listAllListeners;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_products_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (mProductsAllModelList != null)
            return mProductsAllModelList.size();
        else
            return 0;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.nameTV.setText(mProductsAllModelList.get(position).getProductName());
        holder.priceTV.setText(mProductsAllModelList.get(position).getProductPrice());
        holder.currencyTV.setText(mProductsAllModelList.get(position).getCurrency());

        Picasso.with(holder.itemView.getContext())
                .load("http://myspare.net/public/upload/" + mProductsAllModelList.get(holder.getAdapterPosition()).getImage1())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAllListeners.onCardViewClick(mProductsAllModelList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });

    }//end onBindViewHolder()

    public interface ListAllListeners {
        void onCardViewClick(ProductsAllModel.DataBean produtsAllModel, int adapterPosition);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, priceTV, currencyTV;
        SelectableRoundedImageView imageView;


        MyViewHolder(View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.textName);
            priceTV = itemView.findViewById(R.id.textPrice);
            currencyTV = itemView.findViewById(R.id.textCurrency);

            imageView = itemView.findViewById(R.id.image);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setCornerRadiiDP(4, 4, 0, 0);
        }
    }//end class MyViewHolder
}//end class