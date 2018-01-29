package com.spectraapps.myspare.bottomtabscreens.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.product.ProductsActivity;
import com.spectraapps.myspare.R;
import com.squareup.picasso.Picasso;

public class Home extends Fragment {

    ImageView image1, image2, image3, image4, image5, image6;
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        cardView1 = rootView.findViewById(R.id.card_1);
        cardView2 = rootView.findViewById(R.id.card_2);
        cardView3 = rootView.findViewById(R.id.card_3);
        cardView4 = rootView.findViewById(R.id.card_4);
        cardView5 = rootView.findViewById(R.id.card_5);
        cardView6 = rootView.findViewById(R.id.card_6);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new ProductsActivity()).commit();
                MainActivity.mToolbarText.setText(getString(R.string.main_inside));
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new ProductsActivity()).commit();
                MainActivity.mToolbarText.setText(getString(R.string.main_outside));
            }
        });


        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new ProductsActivity()).commit();
                MainActivity.mToolbarText.setText(getString(R.string.main_electricity));
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new ProductsActivity()).commit();
                MainActivity.mToolbarText.setText(getString(R.string.main_mechanic));
            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new ProductsActivity()).commit();
                MainActivity.mToolbarText.setText(getString(R.string.main_accessories));
            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new ProductsActivity()).commit();
                MainActivity.mToolbarText.setText(getString(R.string.main_tires));
            }
        });

        image1 = rootView.findViewById(R.id.image_1);
        Picasso.with(getContext()).load(R.drawable.car_inside).into(image1);
        image2 = rootView.findViewById(R.id.image_2);
        Picasso.with(getContext()).load(R.drawable.car_outside).into(image2);
        image3 = rootView.findViewById(R.id.image_3);
        Picasso.with(getContext()).load(R.drawable.car_battery).into(image3);
        image4 = rootView.findViewById(R.id.image_4);
        Picasso.with(getContext()).load(R.drawable.car_mechanic).into(image4);
        image5 = rootView.findViewById(R.id.image_5);
        Picasso.with(getContext()).load(R.drawable.car_accessories).into(image5);
        image6 = rootView.findViewById(R.id.image_6);
        Picasso.with(getContext()).load(R.drawable.car_tires).into(image6);

        return rootView;
    }

}//end Home