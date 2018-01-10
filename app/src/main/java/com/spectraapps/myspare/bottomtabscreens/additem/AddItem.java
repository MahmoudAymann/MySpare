package com.spectraapps.myspare.bottomtabscreens.additem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spectraapps.myspare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddItem extends Fragment {

    public AddItem() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_item, container, false);

        



        return rootView;
    }

}
