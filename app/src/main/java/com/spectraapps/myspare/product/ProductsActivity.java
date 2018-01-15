package com.spectraapps.myspare.product;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.michael.easydialog.EasyDialog;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.product.productdetail.ProductDetail;

import java.util.ArrayList;

public class ProductsActivity extends FragmentActivity {
    Button image_button;
    EditText editText;
    Spinner spinner1,spinner2,spinner3;

    RecyclerView recyclerView;
    ProductsRecyclerAdapter mProductsRecyclerAdapter;
    ArrayList<ProductData> mProductDataArrayList;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

       // LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        //View contentView = inflater.inflate(R.layout.activity_products, null, false);
        //mDrawer.addView(contentView, 0);

        mToolbar = findViewById(R.id.products_toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getIntent().getStringExtra("card1"));


        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        image_button = findViewById(R.id.button);
        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showPopUp();
            }
        });

        recyclerView = findViewById(R.id.products_recycler);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

        mProductDataArrayList = new ArrayList<>();
        mProductDataArrayList.add(new ProductData(R.drawable.car_thumb,"منتج 1","500 $"));
        mProductDataArrayList.add(new ProductData(R.drawable.car_mechanic,"منتج 5","700 $"));
        mProductDataArrayList.add(new ProductData(R.drawable.car_tires,"منتج 1","700 $"));
        mProductDataArrayList.add(new ProductData(R.drawable.car_thumb,"منتج 2","609 $"));
        mProductDataArrayList.add(new ProductData(R.drawable.car_thumb,"منتج 1","150 $"));
        mProductDataArrayList.add(new ProductData(R.drawable.car_accessories,"منتج 9","10 $"));

        mProductsRecyclerAdapter = new ProductsRecyclerAdapter(mProductDataArrayList, new ProductsRecyclerAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(ProductData productData) {
                startActivity(new Intent(ProductsActivity.this, ProductDetail.class));
            }
        });

        recyclerView.setAdapter(mProductsRecyclerAdapter);
        mProductsRecyclerAdapter.notifyDataSetChanged();

    }

    private void showPopUp() {

        View popupView = this.getLayoutInflater().inflate(R.layout.popup_filter_layout, null);
        new EasyDialog(ProductsActivity.this)
                //.setLayoutResourceId(R.layout.popup_filter_layout)//layout resource id
                .setLayout(popupView)
                .setBackgroundColor(ProductsActivity.this.getResources().getColor(R.color.white_gray))
                //.setLocation()//point in screen
                .setLocationByAttachedView(image_button)
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(true)
                .setMarginLeftAndRight(30, 30)
                .show();

        spinner1 = popupView.findViewById(R.id.spinner1);
        spinner2 = popupView.findViewById(R.id.spinner2);
        spinner3 = popupView.findViewById(R.id.spinner3);
        String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_custom_dropdown_hint_item_layout, ITEMS);
        adapter.setDropDownViewResource(R.layout.my_custom_hint_item_layout);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);

       editText = popupView.findViewById(R.id.editText1_pop);

    }

}
