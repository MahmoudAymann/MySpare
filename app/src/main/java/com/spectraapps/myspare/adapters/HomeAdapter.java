package com.spectraapps.myspare.adapters;

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
import com.spectraapps.myspare.model.CategoriesModel;
import com.spectraapps.myspare.model.inproducts.ProductsAllModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MahmoudAyman on 13/01/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private ListAllListeners listAllListeners;
    private ArrayList<CategoriesModel.DataBean> mCategoriesAllModelList;

    public HomeAdapter(ArrayList<CategoriesModel.DataBean> mCategoriesAllModelList,
                       ListAllListeners listAllListeners) {
        this.mCategoriesAllModelList = mCategoriesAllModelList;
        this.listAllListeners = listAllListeners;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_category_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (mCategoriesAllModelList != null)
            return mCategoriesAllModelList.size();
        else
            return 0;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.nameTV.setText(mCategoriesAllModelList.get(position).getName());

        Picasso.with(holder.itemView.getContext())
                .load(mCategoriesAllModelList.get(position).getImage())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAllListeners.onCardViewClick(mCategoriesAllModelList.get(holder.getAdapterPosition()));
            }
        });


    }//end onBindViewHolder()

    public interface ListAllListeners {
        void onCardViewClick(CategoriesModel.DataBean categoriesModel);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;
        SelectableRoundedImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.textView_home_category);

            imageView = itemView.findViewById(R.id.imageView_home_category);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setCornerRadiiDP(4, 4, 0, 0);
        }
    }//end class MyViewHolder

}//end class