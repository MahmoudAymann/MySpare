package com.spectraapps.myspare.bottomtabscreens.additem;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.login.LoginActivity;
import com.spectraapps.myspare.utility.ListSharedPreference;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.model.AddModel;
import com.spectraapps.myspare.model.BrandsModel;
import com.spectraapps.myspare.model.CategoriesModel;
import com.spectraapps.myspare.model.CountriesModel;
import com.spectraapps.myspare.model.CurrencyModel;
import com.spectraapps.myspare.model.ManufacturerCountriesModel;
import com.spectraapps.myspare.model.ModelsModel;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    private static final int IMG_CODE1 = 10001;
    private static final int IMG_CODE2 = 10002;

    public static String image_path1, image_path2;

    // Storage Permissions
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,

    };

    Toolbar mToolbar;
    TextView mToolbarTilte;
    Button mToolbarButton, mAddButton;
    Spinner manufactureCountry_spinner, brand_spinner, countries_spinner, category_spinner, currency_spinner, year_spinner, models_spinner;
    ArrayList<String> category_array, categoryId_array, manufactureCountry_array, manufactureCountryId_array,
            brand_array, brandId_array, countries_array, countriesId_array, models_array, modelsId_array, currency_array, currencyId_array;
    ArrayList<String> year_array;
    Calendar mCalendar;
    EditText nameET, serialNumberET, priceET;
    RoundKornerLinearLayout roundKornerTV, roundKornerSpinner;
    String mUserID, mItemName, mSerialNumber, mManfactureCountry_Id, mDate,
            mBrand_Id, mModel_Id, mCategory_Id, mCountry_Id, mCurrency, mCurrencyId, mPrice;
    ProgressDialog progressDialog;
    ImageView imageView1, imageView2;

    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        ConstraintLayout view = findViewById(R.id.add_wholeView);
        YoYo.with(Techniques.Bounce).duration(500).playOn(view);

        setSharedPreference = new ListSharedPreference.Set(AddItemActivity.this.getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(AddItemActivity.this.getApplicationContext());

        initToolbar();

        initUI();
        try {
            initClickListener();
        } catch (Exception exc) {
            Toast.makeText(AddItemActivity.this, "" + exc, Toast.LENGTH_SHORT).show();
        }
        try {
            serverManufacturerCountries();
            serverCountries();
            serverBrands();
            serverCurrency();
            serverCategories();

            checkPermissions();
        } catch (Exception exc) {
            Toast.makeText(AddItemActivity.this, "" + exc, Toast.LENGTH_SHORT).show();
        }

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
                if (brand_spinner.getSelectedItemPosition() > 0) {
                    roundKornerTV.setVisibility(View.VISIBLE);
                    roundKornerSpinner.setVisibility(View.VISIBLE);
                    serverModels();
                } else {
                    roundKornerTV.setVisibility(View.INVISIBLE);
                    roundKornerSpinner.setVisibility(View.INVISIBLE);
                    progressDialog.dismiss();

                }
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
        if (image_path1 != null && image_path2 != null)
            serverAddItemBoth();
        else if (image_path1 != null)
            serverAddItemOne();
        else
            serverAddItemTwo();
    }

    private void serverAddItemTwo() {

        mItemName = nameET.getText().toString();
        mSerialNumber = serialNumberET.getText().toString();
        mDate = year_spinner.getSelectedItem().toString();
        mCurrency = currency_spinner.getSelectedItem().toString();
        mPrice = priceET.getText().toString();

        File file2;
        file2 = new File(image_path2);

        RequestBody mFile2 = RequestBody.create(MediaType.parse("image/*"), file2);

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), mUserID);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), mItemName);
        RequestBody number = RequestBody.create(MediaType.parse("text/plain"), mSerialNumber);
        RequestBody manufacturingCountry = RequestBody.create(MediaType.parse("text/plain"), mManfactureCountry_Id);
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), mDate);
        RequestBody brand = RequestBody.create(MediaType.parse("text/plain"), mBrand_Id);
        RequestBody model = RequestBody.create(MediaType.parse("text/plain"), mModel_Id);
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), mCategory_Id);
        RequestBody country = RequestBody.create(MediaType.parse("text/plain"), mCountry_Id);
        RequestBody currency = RequestBody.create(MediaType.parse("text/plain"), mCurrencyId);
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), mPrice);
        RequestBody image3 = RequestBody.create(MediaType.parse("text/plain"), "NULL");

        MultipartBody.Part image2 = MultipartBody.Part.createFormData("image1", file2.getName(), mFile2);

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<AddModel> call = retrofit.uploadFileTwo(id, name, number, manufacturingCountry, date, brand,
                model, category, country, currency, price, image2, image3);

        call.enqueue(new Callback<AddModel>() {
            @Override
            public void onResponse(@NonNull Call<AddModel> call, @NonNull Response<AddModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddItemActivity.this,getString(R.string.itemAddedSuccefully), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddItemActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AddItemActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void serverAddItemOne()
    {

        mItemName = nameET.getText().toString();
        mSerialNumber = serialNumberET.getText().toString();
        mDate = year_spinner.getSelectedItem().toString();
        mCurrency = currency_spinner.getSelectedItem().toString();
        mPrice = priceET.getText().toString();

        File file1;
        file1 = new File(image_path1);

        RequestBody mFile1 = RequestBody.create(MediaType.parse("image/*"), file1);

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), mUserID);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), mItemName);
        RequestBody number = RequestBody.create(MediaType.parse("text/plain"), mSerialNumber);
        RequestBody manufacturingCountry = RequestBody.create(MediaType.parse("text/plain"), mManfactureCountry_Id);
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), mDate);
        RequestBody brand = RequestBody.create(MediaType.parse("text/plain"), mBrand_Id);
        RequestBody model = RequestBody.create(MediaType.parse("text/plain"), mModel_Id);
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), mCategory_Id);
        RequestBody country = RequestBody.create(MediaType.parse("text/plain"), mCountry_Id);
        RequestBody currency = RequestBody.create(MediaType.parse("text/plain"), mCurrencyId);
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), mPrice);
        RequestBody image3 = RequestBody.create(MediaType.parse("text/plain"), "NULL");

        MultipartBody.Part image1 = MultipartBody.Part.createFormData("image1", file1.getName(), mFile1);

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<AddModel> call = retrofit.uploadFileOne(id, name, number, manufacturingCountry, date, brand,
                model, category, country, currency, price, image1, image3);

        call.enqueue(new Callback<AddModel>() {
            @Override
            public void onResponse(@NonNull Call<AddModel> call, @NonNull Response<AddModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddItemActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddItemActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(AddItemActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                //serverAddItemBoth();
            }
        });
    }//end serverAddItemOne

    private void serverAddItemBoth() {

        mItemName = nameET.getText().toString();
        mSerialNumber = serialNumberET.getText().toString();
        mDate = year_spinner.getSelectedItem().toString();
        mCurrency = currency_spinner.getSelectedItem().toString();
        mPrice = priceET.getText().toString();

        File file1, file2;
        file1 = new File(image_path1);
        file2 = new File(image_path2);

        RequestBody mFile1 = RequestBody.create(MediaType.parse("image/*"), file1);
        RequestBody mFile2 = RequestBody.create(MediaType.parse("image/*"), file2);

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), mUserID);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), mItemName);
        RequestBody number = RequestBody.create(MediaType.parse("text/plain"), mSerialNumber);
        RequestBody manufacturingCountry = RequestBody.create(MediaType.parse("text/plain"), mManfactureCountry_Id);
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), mDate);
        RequestBody brand = RequestBody.create(MediaType.parse("text/plain"), mBrand_Id);
        RequestBody model = RequestBody.create(MediaType.parse("text/plain"), mModel_Id);
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), mCategory_Id);
        RequestBody country = RequestBody.create(MediaType.parse("text/plain"), mCountry_Id);
        RequestBody currency = RequestBody.create(MediaType.parse("text/plain"), mCurrencyId);
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), mPrice);
        RequestBody image3 = RequestBody.create(MediaType.parse("text/plain"), "NULL");


        MultipartBody.Part image1 = MultipartBody.Part.createFormData("image1", file1.getName(), mFile1);
        MultipartBody.Part image2 = MultipartBody.Part.createFormData("image2", file2.getName(), mFile2);

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<AddModel> call = retrofit.uploadFileBoth(id, name, number, manufacturingCountry, date, brand,
                model, category, country, currency, price, image1, image2, image3);

        call.enqueue(new Callback<AddModel>() {
            @Override
            public void onResponse(@NonNull Call<AddModel> call, @NonNull Response<AddModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddItemActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddItemActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Log.v("esddd", t.getMessage());
                Toast.makeText(AddItemActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                //serverAddItemBoth();
            }
        });

    }//end serverAddItemBoth

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


    private void addYears() {
        int current_year = mCalendar.get(Calendar.YEAR);
        //Toast.makeText(this, ""+current_year, Toast.LENGTH_SHORT).show();
        year_array = new ArrayList<>();
        year_array.add(getString(R.string.choose_year));
        for (int i = current_year; i >= 1990; i--) {
            year_array.add(String.valueOf(i));
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                AddItemActivity.this, android.R.layout.simple_spinner_item,
                year_array);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        year_spinner.setAdapter(spinnerArrayAdapter);

    }//end addYears();

    private String getLangkey() {
        return getSharedPreference.getLanguage();
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

        Call<CountriesModel> countriesCall = retrofit.countries(getLangkey());

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
        category_array.add(0, getString(R.string.choose_category));

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
        categoryId_array.add(0, "addItem");
        for (int i = 0; i < data.size(); i++) {
            categoryId_array.add(data.get(i).getId());
        }
    }

    private void getCountries(List<CountriesModel.DataBean> data) {
        countries_array = new ArrayList<>();
        countries_array.add(0, getString(R.string.choose_country));
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
        countriesId_array.add(0, "addItem");
        for (int i = 0; i < data.size(); i++) {
            countriesId_array.add(data.get(i).getId());
        }
    }

    private void getBrands(List<BrandsModel.DataBean> data) {
        brand_array = new ArrayList<>();
        brand_array.add(0, getString(R.string.choose_brand));
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
        brandId_array.add(0, "addItem");
        for (int i = 0; i < data.size(); i++) {
            brandId_array.add(data.get(i).getId());
        }
    }

    private void getCurrency(List<CurrencyModel.DataBean> data) {
        currency_array = new ArrayList<>();
        currency_array.add(0, getString(R.string.choose_currency));
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
        currencyId_array.add(0, "addItem");
        for (int i = 0; i < data.size(); i++) {
            currencyId_array.add(data.get(i).getId());
        }

    }

    private void getManufacturerCountries(List<ManufacturerCountriesModel.DataBean> data) {
        manufactureCountry_array = new ArrayList<>();
        manufactureCountry_array.add(0, getString(R.string.choose_country));
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
        manufactureCountryId_array.add(0, "choose countryid");

        for (int i = 0; i < data.size(); i++) {
            manufactureCountryId_array.add(data.get(i).getId());
        }
    }

    private void getModels(List<ModelsModel.DataBean> data) {
        models_array = new ArrayList<>();
        models_array.add(0, getString(R.string.choose_model));
        for (int i = 0; i < data.size(); i++) {
            models_array.add(data.get(i).getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        models_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        models_spinner.setAdapter(spinnerArrayAdapter);
    }

    private void getModelsId(List<ModelsModel.DataBean> data) {
        modelsId_array = new ArrayList<>();
        modelsId_array.add(0, "addItem");
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

        imageView1 = findViewById(R.id.addImg1);
        imageView1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkPermissions();
                        pickImage(IMG_CODE1);
                    }
                }
        );

        imageView2 = findViewById(R.id.addImg2);
        imageView2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pickImage(IMG_CODE2);
                        checkPermissions();
                    }
                }
        );
    }

    private void pickImage(int keyImg) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, keyImg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_CODE1 && resultCode == RESULT_OK && data != null) {
            try {

                Uri uri = data.getData();
                image_path1 = getRealPathFromUri(uri, AddItemActivity.this);
                showImageInView(uri, IMG_CODE1);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(AddItemActivity.this, getString(R.string.fail_load_image), Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == IMG_CODE2 && resultCode == RESULT_OK && data != null) {
            try {
                Uri uri = data.getData();
                image_path2 = getRealPathFromUri(uri, AddItemActivity.this);
                showImageInView(uri, IMG_CODE2);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(AddItemActivity.this, getString(R.string.fail_load_image), Toast.LENGTH_LONG).show();
            }

        }
    }//end onActivityResult

    private void showImageInView(Uri image_path, int imgCode) {
          if (imgCode == IMG_CODE1)
              Picasso.with(AddItemActivity.this).load(image_path).resize(100,100).into(imageView1);
     else if (imgCode == IMG_CODE2)
              Picasso.with(AddItemActivity.this).load(image_path).into(imageView2);
    }

    public static String getRealPathFromUri(Uri contentURI, Context context) {
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(contentURI, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        } catch (Exception ignored){
            return null;
        }
    }

    private void getUserInfo() {
        mUserID = getSharedPreference.getUId();
    }

}
