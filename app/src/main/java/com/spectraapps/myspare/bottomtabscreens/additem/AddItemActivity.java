package com.spectraapps.myspare.bottomtabscreens.additem;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.SplashScreen;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.login.LoginActivity;
import com.spectraapps.myspare.model.AddModel;
import com.spectraapps.myspare.model.BrandsModel;
import com.spectraapps.myspare.model.CategoriesModel;
import com.spectraapps.myspare.model.CountriesModel;
import com.spectraapps.myspare.model.CurrencyModel;
import com.spectraapps.myspare.model.ManufacturerCountriesModel;
import com.spectraapps.myspare.model.ModelsModel;
import com.spectraapps.myspare.network.MyRetrofitClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    private static final int IMG_CODE = 777;
    Toolbar mToolbar;
    TextView mToolbarTilte;
    Button mToolbarButton, mAddButton;
    Spinner manufactureCountry_spinner, brand_spinner, countries_spinner, category_spinner, currency_spinner, year_spinner, models_spinner;
    ArrayList<String> category_array, categoryId_array, manufactureCountry_array, manufactureCountryId_array,
            brand_array, brandId_array, countries_array, countriesId_array, models_array, modelsId_array, currency_array, currencyId_array;
    ArrayList<Integer> year_array;
    Calendar mCalendar;
    EditText nameET, serialNumberET, priceET;
    RoundKornerLinearLayout roundKornerTV, roundKornerSpinner;
    String mUserID, mItemName, mSerialNumber, mManfactureCountry_Id, mDate,
            mBrand_Id, mModel_Id, mCategory_Id, mCountry_Id, mCurrency, mCurrencyId, mPrice, mImage1, mImage2, mImage3;
    ProgressDialog progressDialog;
    ImageView imageView;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        ConstraintLayout view = findViewById(R.id.add_wholeView);
        YoYo.with(Techniques.Bounce).duration(500).playOn(view);

        initToolbar();
        initUI();

        initClickListener();

        serverManufacturerCountries();
        serverCountries();
        serverBrands();
        serverCurrency();
        serverCategories();

        getUserInfo();
        addYears();
    }//end onCreate()

    private void initClickListener() {

        manufactureCountry_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getManufacturerCountryId(manufactureCountry_spinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        brand_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                progressDialog.show();
                getBrandId(brand_spinner.getSelectedItemPosition());
                roundKornerTV.setVisibility(View.VISIBLE);
                roundKornerSpinner.setVisibility(View.VISIBLE);
                serverModels();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddItemActivity.this, "nothing", Toast.LENGTH_SHORT).show();
            }
        });

        models_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getModelsId(models_spinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        currency_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getCurrencyId(currency_spinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getCategoryId(category_spinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        countries_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getCountryId(countries_spinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAddItem();
            }
        });
    }


    private void submitAddItem() {
        progressDialog.show();
        serverAddItem();
    }

    private void serverAddItem() {
        mItemName = nameET.getText().toString();
        mSerialNumber = serialNumberET.getText().toString();
        mDate = year_spinner.getSelectedItem().toString();
        mCurrency = currency_spinner.getSelectedItem().toString();
        mPrice = priceET.getText().toString();
        mImage1 = getStringImage();

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);
        Call<AddModel> call = retrofit.add(mUserID, mItemName, mSerialNumber, mManfactureCountry_Id, mDate, mBrand_Id,
                mModel_Id, mCategory_Id, mCountry_Id, mCurrency, mPrice, mImage1, "", "");

        call.enqueue(new Callback<AddModel>() {
            @Override
            public void onResponse(Call<AddModel> call, Response<AddModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddItemActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(AddItemActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddModel> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }//end serverAddItem

    private String getStringImage() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    ///////////////#get IDS//////////////////////
    private void getBrandId(Integer pos) {
        for (int i = 0; i < brandId_array.size(); i++) {
            if (pos == i) {
                mBrand_Id = brandId_array.get(i);
            }
        }
    }

    private void getCategoryId(Integer pos) {
        for (int i = 0; i < categoryId_array.size(); i++) {
            if (pos == i) {
                mCategory_Id = categoryId_array.get(i);
            }
        }
    }

    private void getCountryId(Integer pos) {
        for (int i = 0; i < countriesId_array.size(); i++) {
            if (pos == i) {
                mCountry_Id = countriesId_array.get(i);
            }
        }
    }

    private void getCurrencyId(Integer pos) {
        for (int i = 0; i < currencyId_array.size(); i++) {
            if (pos == i) {
                mCurrencyId = currencyId_array.get(i);
            }
        }
    }

    private void getModelsId(Integer spinnerPos) {
        for (int i = 0; i < modelsId_array.size(); i++) {
            if (spinnerPos == i) {
                mModel_Id = modelsId_array.get(i);
            }
        }
    }//end

    private void getManufacturerCountryId(Integer spinnerPos) {
        for (int i = 0; i < manufactureCountryId_array.size(); i++) {
            if (spinnerPos == i) {
                mManfactureCountry_Id = manufactureCountryId_array.get(i);
            }
        }
    }//end
////////////////////////////////////////////////

    private void addYears() {
        int current_year = mCalendar.get(Calendar.YEAR);
        //Toast.makeText(this, ""+current_year, Toast.LENGTH_SHORT).show();
        year_array = new ArrayList<>();
        for (int i = current_year; i >= 1990; i--) {
            year_array.add(i);
        }

        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                AddItemActivity.this, android.R.layout.simple_spinner_item,
                year_array);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        year_spinner.setAdapter(spinnerArrayAdapter);

    }//end addYears();

    ///////////////////////////////////////////
    private String getLangkey() {
        String lang_key = "";
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

    ////////////////////////////////////////////////////////////
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

    ///////////////////////#SERVER///////////////////////////////
    private void serverManufacturerCountries() {

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<ManufacturerCountriesModel> manufacturerCountriesCall = retrofit.manufacturerCountries(getLangkey());

        manufacturerCountriesCall.enqueue(new Callback<ManufacturerCountriesModel>() {
            @Override
            public void onResponse(Call<ManufacturerCountriesModel> call, Response<ManufacturerCountriesModel> response) {
                if (response.isSuccessful()) {
                    getManufacturerCountries(response.body().getData());
                    getManufacturerCountriesId(response.body().getData());
                } else
                    Toast.makeText(AddItemActivity.this, response.body().getStatus().getTitle(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ManufacturerCountriesModel> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }//end serverManufacturerCountries()

    private void serverCountries() {
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<CountriesModel> countriesCall = retrofit.countries("ar");

        countriesCall.enqueue(new Callback<CountriesModel>() {
            @Override
            public void onResponse(Call<CountriesModel> call, Response<CountriesModel> response) {
                if (response.isSuccessful()) {

                    getCountries(response.body().getData());
                    getCountriesId(response.body().getData());
                    Log.v("res", response.body().getData() + "");
                } else
                    // Toast.makeText(AddItemActivity.this, response.body().getStatus().getTitle(),
                    // Toast.LENGTH_SHORT).show();
                    Log.v("res", response.body().getData() + "");
            }

            @Override
            public void onFailure(Call<CountriesModel> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }//end serverCountries()

    private void serverBrands() {
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<BrandsModel> brandsCall = retrofit.brands(getLangkey());

        brandsCall.enqueue(new Callback<BrandsModel>() {
            @Override
            public void onResponse(Call<BrandsModel> call, Response<BrandsModel> response) {
                if (response.isSuccessful()) {

                    getBrands(response.body().getData());
                    getBrandsId(response.body().getData());

                } else
                    Toast.makeText(AddItemActivity.this, response.body().getStatus().getTitle(),
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BrandsModel> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }//end serverBrands()

    private void serverCurrency() {
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<CurrencyModel> currencyCall = retrofit.currency(getLangkey());

        currencyCall.enqueue(new Callback<CurrencyModel>() {
            @Override
            public void onResponse(Call<CurrencyModel> call, Response<CurrencyModel> response) {
                if (response.isSuccessful()) {
                    getCurrency(response.body().getData());
                    getCurrencyId(response.body().getData());
                } else
                    Toast.makeText(AddItemActivity.this, response.body().getStatus().getTitle(),
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CurrencyModel> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }//end serverCurrency()

    private void serverModels() {
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<ModelsModel> modelsCall = retrofit.models(mBrand_Id);

        modelsCall.enqueue(new Callback<ModelsModel>() {
            @Override
            public void onResponse(Call<ModelsModel> call, Response<ModelsModel> response) {
                if (response.isSuccessful()) {
                    getModels(response.body().getData());
                    getModelsId(response.body().getData());
                    progressDialog.dismiss();
                } else
                    Toast.makeText(AddItemActivity.this, response.body().getStatus().getTitle(),
                            Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ModelsModel> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }//end serverModels()

    private void serverCategories() {
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<CategoriesModel> categoriesCall = retrofit.categories(getLangkey());

        categoriesCall.enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {
                if (response.isSuccessful()) {
                    getCategory(response.body().getData());
                    getCategoryId(response.body().getData());

                } else
                    Toast.makeText(AddItemActivity.this, response.body().getStatus().getTitle(),
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CategoriesModel> call, Throwable t) {
                Toast.makeText(AddItemActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }//end serverModels()


    private void getCategory(List<CategoriesModel.DataBean> data) {
        category_array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            category_array.add(data.get(i).getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        category_array);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        category_spinner.setAdapter(spinnerArrayAdapter);
    }

    private void getCategoryId(List<CategoriesModel.DataBean> data) {
        categoryId_array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            categoryId_array.add(data.get(i).getId());
        }
    }

    ///////////////////////////////////
    private void getCountries(List<CountriesModel.DataBean> data) {
        countries_array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            countries_array.add(data.get(i).getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        countries_array);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        countries_spinner.setAdapter(spinnerArrayAdapter);
    }

    private void getCountriesId(List<CountriesModel.DataBean> data) {
        countriesId_array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            countriesId_array.add(data.get(i).getId());
        }
    }

    private void getBrands(List<BrandsModel.DataBean> data) {

        brand_array = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            brand_array.add(data.get(i).getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        brand_array);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        brand_spinner.setAdapter(spinnerArrayAdapter);

    }

    private void getBrandsId(List<BrandsModel.DataBean> data) {
        brandId_array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            brandId_array.add(data.get(i).getId());
        }
    }

    private void getCurrency(List<CurrencyModel.DataBean> data) {
        currency_array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            currency_array.add(data.get(i).getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        currency_array);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        currency_spinner.setAdapter(spinnerArrayAdapter);
    }

    private void getCurrencyId(List<CurrencyModel.DataBean> data) {
        currencyId_array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            currencyId_array.add(data.get(i).getId());
        }

    }

    private void getManufacturerCountries(List<ManufacturerCountriesModel.DataBean> data) {
        manufactureCountry_array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            manufactureCountry_array.add(data.get(i).getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        manufactureCountry_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        manufactureCountry_spinner.setAdapter(spinnerArrayAdapter);
    }

    private void getManufacturerCountriesId(List<ManufacturerCountriesModel.DataBean> data) {
        manufactureCountryId_array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            manufactureCountryId_array.add(data.get(i).getId());
        }
    }

    private void getModels(List<ModelsModel.DataBean> data) {
        models_array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            models_array.add(data.get(i).getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        models_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        models_spinner.setAdapter(spinnerArrayAdapter);
    }

    private void getModelsId(List<ModelsModel.DataBean> data) {
        modelsId_array = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            modelsId_array.add(data.get(i).getId());
        }
    }

    private void initUI() {
        mCalendar = Calendar.getInstance();
        //////////////////////////////////////
        roundKornerTV = findViewById(R.id.model_tv_roundedCorner);
        roundKornerSpinner = findViewById(R.id.model_spinner_roundedCorner);
        roundKornerTV.setVisibility(View.INVISIBLE);
        roundKornerSpinner.setVisibility(View.INVISIBLE);
        ////////////////////////
        manufactureCountry_spinner = findViewById(R.id.madeIn_spinner);
        countries_spinner = findViewById(R.id.country_spinner);
        brand_spinner = findViewById(R.id.brand_spinner);
        currency_spinner = findViewById(R.id.currency_spinner);
        year_spinner = findViewById(R.id.year_spinner);
        models_spinner = findViewById(R.id.model_spinner);
        category_spinner = findViewById(R.id.category_spinner);
        ///////////////////////
        mAddButton = findViewById(R.id.add_button);
        //////////////////////////
        nameET = findViewById(R.id.name_editText_addItem);
        serialNumberET = findViewById(R.id.serialNum_ET_addItem);
        priceET = findViewById(R.id.price_editText_addItem);

        progressDialog = new ProgressDialog(AddItemActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        imageView = findViewById(R.id.addImg1);
        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pickImage();
                    }
                }
        );
    }

    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_CODE && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getUserInfo() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mUserID = prefs.getString("id", "0");
    }

}
