package com.spectraapps.myspare.bottomtabscreens.additem;


import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.SplashScreen;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.login.LoginActivity;
import com.spectraapps.myspare.model.LoginModel;
import com.spectraapps.myspare.model.ManufacturerCountriesModel;
import com.spectraapps.myspare.network.MyRetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TextView mToolbarTilte;
    Button mToolbarButton;

    Spinner madeIn_spinner, brand_spinner;
    ArrayList<String> madein_array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        ConstraintLayout view = findViewById(R.id.add_wholeView);
        YoYo.with(Techniques.Bounce).duration(500).repeat(0).playOn(view);

        initToolbar();
       // initEmptyArray();
        initUI();

        serverLogin();

    }//end onCreate()

    private void initEmptyArray() {

        madein_array = new ArrayList<>();
        madein_array.add("loading");
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.additem_toolbar);
        mToolbarTilte = findViewById(R.id.toolbar_title);
        mToolbarButton = findViewById(R.id.toolbar_button);
        mToolbarTilte.setText(getString(R.string.add_title));
        mToolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void serverLogin() {

        Api retrofit= MyRetrofitClient.getBase().create(Api.class);

        String lang_key = "";
        switch (SplashScreen.LANG_NUM) {
            case 1:
                lang_key = "en";
                break;
            case 2:
                lang_key = "ar";
                break;
        }

        Call<ManufacturerCountriesModel> manufacturerCountriesCall = retrofit.manufacturerCountries(lang_key);

        manufacturerCountriesCall.enqueue(new Callback<ManufacturerCountriesModel>()
        {
            @Override
            public void onResponse(Call<ManufacturerCountriesModel> call, Response<ManufacturerCountriesModel> response)
            {
                if (response.isSuccessful()) {
                    madein_array = new ArrayList<>();
                    for (int i = 0; i < response.body().getData().size();i++){
                        madein_array.add(response.body().getData().get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ManufacturerCountriesModel> call, Throwable t)
            {
                Toast.makeText(AddItemActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    private void initUI() {
        madeIn_spinner = findViewById(R.id.madeIn_spinner);
    }

}
