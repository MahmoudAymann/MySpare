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
import com.spectraapps.myspare.model.CategoriesModel;
import com.spectraapps.myspare.model.CountriesModel;
import com.spectraapps.myspare.model.LoginModel;
import com.spectraapps.myspare.model.ManufacturerCountriesModel;
import com.spectraapps.myspare.network.MyRetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TextView mToolbarTilte;
    Button mToolbarButton;

    Spinner madeIn_spinner, brand_spinner,countries_spinner;
    ArrayList<String> madein_array, countries_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        ConstraintLayout view = findViewById(R.id.add_wholeView);
        YoYo.with(Techniques.Bounce).duration(500).repeat(0).playOn(view);

        initToolbar();
        initUI();

        serverManufacturerCountries();
        serverCountries();

    }//end onCreate()

  private String getLangkey(){
        String lang_key="";
      switch (SplashScreen.LANG_NUM) {
          case 1:
              lang_key = "en";
              break;
          case 2:
              lang_key = "ar";
              break;
      }
      return lang_key;
  }//end getLangKey()

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

    }//end initToolbar

    private void serverManufacturerCountries() {

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<ManufacturerCountriesModel> manufacturerCountriesCall = retrofit.manufacturerCountries(getLangkey());

        manufacturerCountriesCall.enqueue(new Callback<ManufacturerCountriesModel>() {
            @Override
            public void onResponse(Call<ManufacturerCountriesModel> call, Response<ManufacturerCountriesModel> response) {
                if (response.isSuccessful()) {
                    getManufacturerCountries(response.body().getData());
                } else
                    Toast.makeText(AddItemActivity.this, response.body().getStatus().getTitle(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ManufacturerCountriesModel> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }//end serverManufacturerCountries()
    private void serverCountries(){
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<CountriesModel> countriesCall = retrofit.countries(getLangkey());

        countriesCall.enqueue(new Callback<CountriesModel>() {
            @Override
            public void onResponse(Call<CountriesModel> call, Response<CountriesModel> response) {
                if (response.isSuccessful()) {

                    getCountries(response.body().getData());
                }
                else
                    Toast.makeText(AddItemActivity.this, response.body().getStatus().getTitle(),
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CountriesModel> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }//end serverCountries()

    private void getCountries(List<CountriesModel.DataBean> data) {
        countries_array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            countries_array.add(data.get(i).getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        madein_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        countries_spinner.setAdapter(spinnerArrayAdapter);
    }
    private void getManufacturerCountries(List<ManufacturerCountriesModel.DataBean> data) {
        madein_array = new ArrayList<>();
        for (int i = 0; i < data.size() ; i++) {
            madein_array.add(data.get(i).getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        madein_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        madeIn_spinner.setAdapter(spinnerArrayAdapter);
    }

    private void initUI() {
        madeIn_spinner = findViewById(R.id.madeIn_spinner);
    }
}
