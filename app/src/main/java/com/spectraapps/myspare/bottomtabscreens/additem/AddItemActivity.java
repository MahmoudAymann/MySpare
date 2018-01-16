package com.spectraapps.myspare.bottomtabscreens.additem;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.spectraapps.myspare.R;

public class AddItemActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TextView mToolbarTilte;
    Button mToolbarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mToolbar = findViewById(R.id.additem_toolbar);
        mToolbarTilte = findViewById(R.id.toolbar_title);
        mToolbarButton = findViewById(R.id.toolbar_button);

        mToolbarTilte.setText(getString(R.string.add_title));

        ConstraintLayout view = findViewById(R.id.add_wholeView);
        YoYo.with(Techniques.Bounce).duration(500).repeat(0).playOn(view);


        mToolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
