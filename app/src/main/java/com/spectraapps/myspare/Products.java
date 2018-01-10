package com.spectraapps.myspare;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.michael.easydialog.EasyDialog;

import fr.ganfra.materialspinner.MaterialSpinner;

public class Products extends AppCompatActivity {
    Button button;
    MaterialSpinner spinner1,spinner2;
    TextView textView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();
            }
        });

    }

    private void showPopUp() {

        View popupView = this.getLayoutInflater().inflate(R.layout.popup_filter_layout, null);
        new EasyDialog(Products.this)
                .setLayoutResourceId(R.layout.popup_filter_layout)//layout resource id
                .setLayout(popupView)
                .setBackgroundColor(Products.this.getResources().getColor(R.color.white_gray))
                //.setLocation(new location[])//point in screen
                .setLocationByAttachedView(button)
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(true)
                .setMarginLeftAndRight(24, 24)
                .show();

        spinner1 = popupView.findViewById(R.id.spinner1);
        //spinner2 = popupView.findViewById(R.id.spinner2);
        String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_custom_dropdown_hint_item_layout, ITEMS);
        adapter.setDropDownViewResource(R.layout.my_custom_hint_item_layout);
        spinner1.setAdapter(adapter);
        //spinner2.setAdapter(adapter);

        //editText = popupView.findViewById(R.id.editText1_pop);

    }
}
