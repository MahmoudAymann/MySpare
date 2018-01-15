package com.spectraapps.myspare.bottomtabscreens.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.spectraapps.myspare.BlankFragment;
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
                //Intent intent1 = new Intent(getActivity(), ProductsActivity.class);
                //intent1.putExtra("card1","Inside Body");
                //startActivity(intent1);

                Fragment fragment = new BlankFragment();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.farmeHome, fragment);
                transaction.commit();
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), ProductsActivity.class);
                intent2.putExtra("card1","Outside Body");
                startActivity(intent2);
            }
        });


        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getActivity(), ProductsActivity.class);
                intent3.putExtra("card1","Electricity");
                startActivity(intent3);
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(getActivity(), ProductsActivity.class);
                intent4.putExtra("card1","Mechanic");
                startActivity(intent4);
            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(getActivity(), ProductsActivity.class);
                intent5.putExtra("card1","Accessories");
                startActivity(intent5);
            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6 = new Intent(getActivity(), ProductsActivity.class);
                intent6.putExtra("card1","Tires");
                startActivity(intent6);
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