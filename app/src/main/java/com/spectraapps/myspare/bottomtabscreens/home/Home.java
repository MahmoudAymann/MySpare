package com.spectraapps.myspare.bottomtabscreens.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.spectraapps.myspare.ProductDetail;
import com.spectraapps.myspare.Products;
import com.spectraapps.myspare.R;
import com.squareup.picasso.Picasso;

public class Home extends Fragment {

    ImageView image1,image2,image3,image4,image5,image6;
    CardView cardView1,cardView2;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        image1 = rootView.findViewById(R.id.image_1);
        cardView1 = rootView.findViewById(R.id.card_1);
        Picasso.with(getContext()).load(R.drawable.car_inside).into(image1);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Products.class));
            }
        });

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