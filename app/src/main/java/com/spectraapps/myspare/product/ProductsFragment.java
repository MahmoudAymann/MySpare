package com.spectraapps.myspare.product;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.michael.easydialog.EasyDialog;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.bottomtabscreens.profile.Profile;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.helper.IOnBackPressed;
import com.spectraapps.myspare.product.productdetail.ProductDetail;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;

public class ProductsFragment extends Fragment{
    FloatingActionButton fabButton;
    EditText editText;
    Spinner spinner1, spinner2, spinner3, spinner5;

    RecyclerView recyclerView;
    ProductsRecyclerAdapter mProductsRecyclerAdapter;
    ArrayList<ProductData> mProductDataArrayList;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()) {
            @Override
            public void onBackPressed() {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new Home())
                        .commit();
            }
        });

        View rootView = inflater.inflate(R.layout.fragment_products, container, false);

        PullRefreshLayout pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutProducts);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // startRefresh
                mProductsRecyclerAdapter.notifyDataSetChanged();
            }
        });
        // refresh complete
        pullRefreshLayout.setRefreshing(false);

        fabButton = rootView.findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();
            }
        });
        YoYo.with(Techniques.BounceInUp)
                .duration(700)
                .repeat(1)
                .playOn(fabButton);


        recyclerView = rootView.findViewById(R.id.products_recycler);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }

        mProductDataArrayList = new ArrayList<>();
        mProductDataArrayList.add(new ProductData(R.drawable.car_thumb, "product 7", "500 $"));
        mProductDataArrayList.add(new ProductData(R.drawable.car_thumb, "product 5", "700 $"));
        mProductDataArrayList.add(new ProductData(R.drawable.car_thumb, "product 1", "700 $"));
        mProductDataArrayList.add(new ProductData(R.drawable.car_thumb, "product 2", "609 $"));
        mProductDataArrayList.add(new ProductData(R.drawable.car_thumb, "product 3", "150 $"));
        mProductDataArrayList.add(new ProductData(R.drawable.car_thumb, "product 9", "10 $"));

        mProductsRecyclerAdapter = new ProductsRecyclerAdapter(mProductDataArrayList, new ProductsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductData productData) {

                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new ProductDetail()).commit();
            }
        });

        recyclerView.setAdapter(mProductsRecyclerAdapter);
        mProductsRecyclerAdapter.notifyDataSetChanged();

        return rootView;
    }

    private void showPopUp() {

        @SuppressLint("InflateParams")
        View popupView = this.getLayoutInflater().inflate(R.layout.popup_filter_layout, null);
        new EasyDialog(getActivity())
                //.setLayoutResourceId(R.layout.popup_filter_layout)//layout resource id
                .setLayout(popupView)
                .setBackgroundColor(ProductsFragment.this.getResources().getColor(R.color.white_gray))
                //.setLocation()//point in screen
                .setLocationByAttachedView(fabButton)
                .setGravity(EasyDialog.GRAVITY_TOP)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(500, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(300, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(true)
                .setMarginLeftAndRight(30, 30)
                .show();

        spinner1 = popupView.findViewById(R.id.spinner1);
        spinner2 = popupView.findViewById(R.id.spinner2);
        spinner3 = popupView.findViewById(R.id.spinner3);
        spinner5 = popupView.findViewById(R.id.spinner5);
        FButton fButton = popupView.findViewById(R.id.flatButton);
        fButton.setButtonColor(getResources().getColor(R.color.dark_yellow));
        fButton.setShadowColor(getResources().getColor(R.color.white_gray));
        fButton.setCornerRadius(10);
        fButton.setShadowEnabled(true);
        fButton.setShadowHeight(7);

        String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.my_custom_dropdown_hint_item_layout, ITEMS);
        adapter.setDropDownViewResource(R.layout.my_custom_hint_item_layout);


        String[] ITEMS2 = {"Egypt", "Qatar", "Seria", "US", "KSA", "GCM"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), R.layout.my_custom_dropdown_hint_item_layout, ITEMS2);
        adapter.setDropDownViewResource(R.layout.my_custom_hint_item_layout);


        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner5.setAdapter(adapter2);
        editText = popupView.findViewById(R.id.editText1_pop);
    }

}