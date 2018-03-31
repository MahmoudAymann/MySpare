package com.spectraapps.myspare.products;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.kimkevin.cachepot.CachePot;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.michael.easydialog.EasyDialog;
import com.spectraapps.myspare.model.AddToFavModel;
import com.spectraapps.myspare.model.BrandsModel;
import com.spectraapps.myspare.model.CountriesModel;
import com.spectraapps.myspare.model.ModelsModel;
import com.spectraapps.myspare.utility.ListSharedPreference;
import com.spectraapps.myspare.MainActivity;
import com.spectraapps.myspare.R;
import com.spectraapps.myspare.adapters.adpProducts.AllProductsAdapter;
import com.spectraapps.myspare.adapters.adpProducts.ProductsRecyclerAdapter;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.helper.BaseBackPressedListener;
import com.spectraapps.myspare.model.inproducts.ProductsAllModel;
import com.spectraapps.myspare.model.inproducts.ProductsModel;
import com.spectraapps.myspare.network.MyRetrofitClient;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsFragment extends Fragment {

    myCall_Back myCall_back;

    FloatingActionButton fabButton;

    EditText editText;

    Spinner spinner_brand, spinner_country, spinner_year, spinner_model;

    ArrayList<String> year_array = new ArrayList<>();

    RecyclerView recyclerView;

    AlertDialog.Builder alertDialogBuilder;

    AllProductsAdapter mAllProductsAdapter;

    ProductsRecyclerAdapter productsAdapter;

    ArrayList<ProductsModel.DataBean> mProductDataList;

    ArrayList<ProductsAllModel.DataBean> mProductAllDataList;

    PullRefreshLayout pullRefreshLayout;

    Calendar mCalendar;

    FButton fButton;

    String mUEmail, mCategory;

    RoundKornerLinearLayout roundKornerModelsText, roundKornerSpinner;
    String mSerialNumber, mManfactureCountry_Id,
            mBrand_Id, mModel_Id, mCountry_Id;
    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;
    private ArrayList<String> countriesId_array, modelsId_array, brandId_array;

    public ProductsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_products, container, false);

        setSharedPreference = new ListSharedPreference.Set(ProductsFragment.this.getContext().getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(ProductsFragment.this.getContext().getApplicationContext());

        MainActivity.mToolbarText.setText(getSharedPreference.getCategoryName());

        getUserInfo();
        setAlertDialog();
        fireBackButtonEvent();
        initUI(rootView);
        initRecyclerView();

        return rootView;
    }//end onCreateView()

    private void setAlertDialog() {
        alertDialogBuilder = new AlertDialog.Builder(ProductsFragment.this.getContext());

        alertDialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
    }//end setAlertDialog

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myCall_back = (myCall_Back) context;
    }

    private void initRecyclerView() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }
    }//end initRecyclerView()

    private void initUI(View rootView) {
        initPullRefreshLayout(rootView);

        fabButton = rootView.findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();
            }
        });

        recyclerView = rootView.findViewById(R.id.products_recycler);

    }//end initUI

    private void initPullRefreshLayout(View rootView) {
        pullRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutProducts);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getSharedPreference.getLoginStatus())
                    turnOnServers(1);
                else if (!getSharedPreference.getLoginStatus()) {
                    turnOnServers(3);
                }
            }
        });
    }

    private void showPopUp() {

        final View popupView = this.getLayoutInflater().inflate(R.layout.popup_filter_layout, null);
       final EasyDialog easyDialog = new EasyDialog(getActivity())
                .setLayout(popupView)
                .setBackgroundColor(ProductsFragment.this.getResources().getColor(R.color.app_background_color))
                .setLocationByAttachedView(fabButton)
                .setGravity(EasyDialog.GRAVITY_TOP)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 800, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(300, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(300, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(true)
                .setMarginLeftAndRight(30, 30)
                .show();
        roundKornerModelsText = popupView.findViewById(R.id.textBack2);
        roundKornerSpinner = popupView.findViewById(R.id.roundKornerLinearLayout2);
        editText = popupView.findViewById(R.id.editText1_pop);

        spinner_brand = popupView.findViewById(R.id.spinner_brand_popup);
        spinner_country = popupView.findViewById(R.id.spinner_country_popup);
        spinner_year = popupView.findViewById(R.id.spinner_year_popup);
        spinner_model = popupView.findViewById(R.id.spinner_model_popup);

////////////////////////////////////////////////////////////////////////////
        spinner_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                getBrandId(spinner_brand.getSelectedItemPosition());
                if (spinner_brand.getSelectedItemPosition() > 0) {
                    roundKornerModelsText.setVisibility(View.VISIBLE);
                    roundKornerSpinner.setVisibility(View.VISIBLE);
                    serverModels(popupView.getContext(), spinner_model);
                } else {
                    roundKornerModelsText.setVisibility(View.INVISIBLE);
                    roundKornerSpinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
/////////////////////////////////////////////////////////////////////////////////////////
        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getManufacturerCountryId(spinner_country.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
/////////////////////////////////////////////////////////////////////////////////////////
        spinner_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getModelsId(spinner_model.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
///////////////////////////////////////////////////////////////////////////////////////

        fButton = popupView.findViewById(R.id.flatButton);
        fButton.setButtonColor(getResources().getColor(R.color.dark_yellow));
        fButton.setShadowColor(getResources().getColor(R.color.app_background_color));
        fButton.setCornerRadius(10);
        fButton.setShadowEnabled(true);
        fButton.setShadowHeight(7);

        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int spinerCountryPos = spinner_country.getSelectedItemPosition();
                int spinerBrandPos = spinner_brand.getSelectedItemPosition();
                int spinerModelPos = spinner_model.getSelectedItemPosition();
                int spinerYearPos = spinner_year.getSelectedItemPosition();
                boolean serialNumET_HasText = !editText.getText().toString().equals("");

                if (spinerCountryPos > 0 && spinerBrandPos < 1 && spinerModelPos < 1 && spinerYearPos < 1 && !serialNumET_HasText) {
                    myCall_back.filter(mManfactureCountry_Id, 1);
                } else if (spinerCountryPos < 1 && spinerBrandPos > 0 && spinerModelPos < 1 && spinerYearPos < 1 && !serialNumET_HasText) {
                    myCall_back.filter(mBrand_Id, 2);
                } else if (spinerCountryPos < 1 && spinerBrandPos < 1 && spinerModelPos > 0 && spinerYearPos < 1 && !serialNumET_HasText) {
                    myCall_back.filter(mModel_Id, 3);
                } else if (spinerCountryPos < 1 && spinerBrandPos < 1 && spinerModelPos < 1 && spinerYearPos > 0 && !serialNumET_HasText) {
                    myCall_back.filter(spinner_year.getSelectedItem().toString(), 4);
                } else if (serialNumET_HasText) {
                    myCall_back.filter(editText.getText().toString(), 5);
                } else if (spinerCountryPos > 0 && spinerBrandPos > 0 && spinerModelPos < 1 && spinerYearPos < 1 && !serialNumET_HasText) {
                    myCall_back.filter(mManfactureCountry_Id, mBrand_Id, 12);
                } else if (spinerCountryPos > 0 && spinerBrandPos > 0 && spinerModelPos > 0 && spinerYearPos < 1 && !serialNumET_HasText) {
                    myCall_back.filter(mCountry_Id, mBrand_Id, mModel_Id, 123);
                } else if (spinerCountryPos > 0 && spinerBrandPos < 1 && spinerModelPos < 1 && spinerYearPos > 0 && !serialNumET_HasText) {
                    myCall_back.filter(mManfactureCountry_Id, spinner_year.getSelectedItem().toString(), 14);
                } else if (spinerCountryPos > 0 && spinerBrandPos < 1 && spinerModelPos < 1 && spinerYearPos < 1 && serialNumET_HasText) {
                    myCall_back.filter(mManfactureCountry_Id, editText.getText().toString(), 15);
                } else if (spinerCountryPos > 0 && spinerBrandPos > 0 && spinerModelPos > 0 && spinerYearPos > 0 && serialNumET_HasText) {
                    myCall_back.filter(mManfactureCountry_Id, mBrand_Id, mModel_Id, spinner_year.getSelectedItem().toString(), editText.getText().toString(), 12345);
                }
               // popupView.setVisibility(View.INVISIBLE);
                easyDialog.dismiss();
            }
        });

        mCalendar = Calendar.getInstance();
        addYears(popupView);
        serverCountries(popupView.getContext(), spinner_country);
        serverBrands(popupView.getContext(), spinner_brand);
    }

    private void serverProductsWithMail() {
        try {
            if (getArguments() != null) {
                Toast.makeText(getContext(), "other", Toast.LENGTH_SHORT).show();
                if (getArguments().containsKey("country")) {
                    serverFilterWithCountry(getArguments().getString("country"));
                } else if (getArguments().containsKey("brand")) {
                    serverFilterWithSerialNum(getArguments().getString("brand"));
                } else if (getArguments().containsKey("model")) {
                    serverFilterWithSerialNum(getArguments().getString("model"));
                } else if (getArguments().containsKey("year")) {
                    serverFilterWithYear(getArguments().getString("year"));
                } else if (getArguments().containsKey("serial")) {
                    serverFilterWithYear(getArguments().getString("serial"));
                } else if (getArguments().containsKey("country123") && getArguments().containsKey("brand123") && getArguments().containsKey("model123")) {
                    serverCountryBrandModel(getArguments().getString("country123"), getArguments().getString("brand123"), getArguments().getString("model123"));
                } else if (getArguments().containsKey("country14") && getArguments().containsKey("year14")) {
                    serverFilterWithCountryYear(getArguments().getString("country14"), getArguments().getString("year14"));
                } else if (getArguments().containsKey("country15") && getArguments().containsKey("serial15")) {
                    serverFilterWithCountrySerial(getArguments().getString("country15"), getArguments().getString("serial15"));
                } else if (getArguments().containsKey("country12345") && getArguments().containsKey("brand12345") && getArguments().containsKey("model12345")
                        && getArguments().containsKey("year12345") && getArguments().containsKey("serial12345")) {
                    serverFilterWithAll(getArguments().getString("country12345"), getArguments().getString("brand12345"),
                            getArguments().getString("model12345"), getArguments().getString("year12345"), getArguments().getString("serial12345"));
                }
            } else {
                serverNormal();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }//end serverProductsWithMail

    private void serverFilterWithAll(String country12345, String brand12345, String model12345, String year12345, String serial12345) {
        try {
            mProductDataList = new ArrayList<>();
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<ProductsModel> productsCall = retrofit.productsWithAll(mUEmail, mCategory, getLang(), brand12345, model12345, serial12345, year12345, country12345);

            productsCall.enqueue(new Callback<ProductsModel>() {
                @Override
                public void onResponse(@NonNull Call<ProductsModel> call, @NonNull Response<ProductsModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            mProductDataList.addAll(response.body().getData());
                            pullRefreshLayout.setRefreshing(false);
                            recyclerView.setAdapter(productsAdapter);
                            productsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception exc) {
                        Toast.makeText(getContext(), "" + exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductsModel> call, @NonNull Throwable t) {
                    Log.v("tagy", t.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                    alertDialogBuilder.setMessage(t.getMessage());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        } catch (Exception exc) {
            Toast.makeText(getContext(), "" + exc, Toast.LENGTH_SHORT).show();
        }
    }

    private void serverFilterWithCountrySerial(String country15, String serial15) {
        try {
            mProductDataList = new ArrayList<>();
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<ProductsModel> productsCall = retrofit.productsWithCountrySerial(mUEmail, mCategory, getLang(), country15, serial15);

            productsCall.enqueue(new Callback<ProductsModel>() {
                @Override
                public void onResponse(@NonNull Call<ProductsModel> call, @NonNull Response<ProductsModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            mProductDataList.addAll(response.body().getData());
                            pullRefreshLayout.setRefreshing(false);
                            recyclerView.setAdapter(productsAdapter);
                            productsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception exc) {
                        Toast.makeText(getContext(), "" + exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductsModel> call, @NonNull Throwable t) {
                    Log.v("tagy", t.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                    alertDialogBuilder.setMessage(t.getMessage());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        } catch (Exception exc) {
            Toast.makeText(getContext(), "" + exc, Toast.LENGTH_SHORT).show();
        }
    }

    private void serverFilterWithCountryYear(String country14, String year14) {
        try {
            mProductDataList = new ArrayList<>();
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<ProductsModel> productsCall = retrofit.productsWithCountryYear(mUEmail, mCategory, getLang(), country14, year14);

            productsCall.enqueue(new Callback<ProductsModel>() {
                @Override
                public void onResponse(@NonNull Call<ProductsModel> call, @NonNull Response<ProductsModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            mProductDataList.addAll(response.body().getData());
                            pullRefreshLayout.setRefreshing(false);
                            recyclerView.setAdapter(productsAdapter);
                            productsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception exc) {
                        Toast.makeText(getContext(), "" + exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductsModel> call, @NonNull Throwable t) {
                    Log.v("tagy", t.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                    alertDialogBuilder.setMessage(t.getMessage());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        } catch (Exception exc) {
            Toast.makeText(getContext(), "" + exc, Toast.LENGTH_SHORT).show();
        }
    }

    private void serverCountries(final Context popup, final Spinner spinner) {
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<CountriesModel> countriesCall = retrofit.countries(getSharedPreference.getLanguage());

        countriesCall.enqueue(new Callback<CountriesModel>() {
            @Override
            public void onResponse(@NonNull Call<CountriesModel> call, @NonNull Response<CountriesModel> response) {
                try {
                    if (response.isSuccessful()) {
                        getCountries(response.body().getData(), popup, spinner);
                        getCountriesId(response.body().getData());
                        Log.v("res", response.body().getData() + "");
                    } else {
                        Log.v("res", response.body().getData() + "");
                    }
                } catch (Exception ignored) {

                }
            }
            @Override
            public void onFailure(@NonNull Call<CountriesModel> call, @NonNull Throwable t) {
            }
        });
    }//end serverCountries()

    private void getCountriesId(List<CountriesModel.DataBean> data) {
        countriesId_array = new ArrayList<>();
        countriesId_array.add(0, "addItem");
        for (int i = 0; i < data.size(); i++) {
            countriesId_array.add(data.get(i).getId());
        }
    }

    private void getCountries(List<CountriesModel.DataBean> data, Context popupView, Spinner spinner) {
        ArrayList<String> countries_array = new ArrayList<>();
        countries_array.add(0, getString(R.string.choose_country));
        for (int i = 0; i < data.size(); i++) {
            countries_array.add(data.get(i).getName());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (popupView, android.R.layout.simple_spinner_item,
                        countries_array);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    private void serverBrands(final Context context, final Spinner spinner) {
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        Call<BrandsModel> brandsCall = retrofit.brands(getLang());

        brandsCall.enqueue(new Callback<BrandsModel>() {
            @Override
            public void onResponse(@NonNull Call<BrandsModel> call, @NonNull Response<BrandsModel> response) {
                if (response.isSuccessful()) {

                    getBrands(response.body().getData(), context, spinner);
                    getBrandsId(response.body().getData());

                } else
                    Toast.makeText(getContext(), response.body().getStatus().getTitle(),
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<BrandsModel> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }//end serverBrands()

    private void getBrands(List<BrandsModel.DataBean> data, Context context, Spinner spinner) {
        ArrayList<String> brand_array = new ArrayList<>();
        brand_array.add(0, getString(R.string.choose_brand));
        for (int i = 0; i < data.size(); i++) {
            brand_array.add(data.get(i).getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (context, android.R.layout.simple_spinner_item,
                        brand_array);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

    }

    private void getBrandsId(List<BrandsModel.DataBean> data) {
        brandId_array = new ArrayList<>();
        brandId_array.add(0, "addItem");
        for (int i = 0; i < data.size(); i++) {
            brandId_array.add(data.get(i).getId());
        }
    }

    private void serverModels(final Context context, final Spinner spinner) {

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);
        Call<ModelsModel> modelsCall = retrofit.models(mBrand_Id);
        modelsCall.enqueue(new Callback<ModelsModel>() {
            @Override
            public void onResponse(@NonNull Call<ModelsModel> call, @NonNull Response<ModelsModel> response) {
                if (response.isSuccessful()) {
                    getModels(response.body().getData(), context, spinner);
                    getModelsId(response.body().getData());
                } else
                    Toast.makeText(getContext(), response.body().getStatus().getTitle(),
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<ModelsModel> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }//end

    private void getModels(List<ModelsModel.DataBean> data, Context context, Spinner spinner) {
        ArrayList<String> models_array = new ArrayList<>();
        models_array.add(0, getString(R.string.choose_model));
        for (int i = 0; i < data.size(); i++) {
            models_array.add(data.get(i).getName());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (context, android.R.layout.simple_spinner_item,
                        models_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    private void getModelsId(List<ModelsModel.DataBean> data) {
        modelsId_array = new ArrayList<>();
        modelsId_array.add(0, "addItem");
        for (int i = 0; i < data.size(); i++) {
            modelsId_array.add(data.get(i).getId());
        }
    }

    private void addYears(View view) {
        int current_year = mCalendar.get(Calendar.YEAR);
        year_array.add(0, getString(R.string.choose_year));
        for (int i = current_year; i >= 1990; i--) {
            year_array.add(String.valueOf(i));
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                view.getContext(), android.R.layout.simple_spinner_item, year_array);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(spinnerArrayAdapter);

    }//end addYears();

    private void serverProductsAll() {
        mProductAllDataList = new ArrayList<>();
        try {
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<ProductsAllModel> productsCall = retrofit.productsAll(getLang(), mCategory);

            productsCall.enqueue(new Callback<ProductsAllModel>() {
                @Override
                public void onResponse(@NonNull Call<ProductsAllModel> call, @NonNull Response<ProductsAllModel> response) {
                    try {

                        if (response.isSuccessful()) {
                            mProductAllDataList.addAll(response.body().getData());
                            recyclerView.setAdapter(mAllProductsAdapter);
                            mAllProductsAdapter.notifyDataSetChanged();
                            Log.v("jkjk", response.body().getData().size() + "");
                            pullRefreshLayout.setRefreshing(false);
                        } else {
                            if (response.body() != null) {
                                pullRefreshLayout.setRefreshing(false);
                                Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }catch (Exception exc){
                        exc.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductsAllModel> call, @NonNull Throwable t) {
                    Log.v("tagy", t.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            pullRefreshLayout.setRefreshing(false);
            alertDialogBuilder.setMessage(e.getMessage());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private String getLang() {
        return getSharedPreference.getLanguage();
    }

    ///////////////#get IDS//////////////////////
    private void getBrandId(Integer pos) {
        for (int i = 0; i < brandId_array.size(); i++) {
            if (pos == i) {
                mBrand_Id = brandId_array.get(i);
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
        for (int i = 0; i < countriesId_array.size(); i++) {
            if (spinnerPos == i) {
                mManfactureCountry_Id = countriesId_array.get(i);
            }
        }
    }//end

    @Override
    public void onStart() {
        super.onStart();
        if (getSharedPreference.getLoginStatus()) {
            turnOnServers(1);
        } else if (!getSharedPreference.getLoginStatus()) {
            turnOnServers(3);
        }
    }

    private void serverCountryBrandModel(String country123, String brand123, String model123) {
        try {
            mProductDataList = new ArrayList<>();
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<ProductsModel> productsCall = retrofit.productsWithCountryBrandModel(mUEmail, mCategory, getLang(), country123, brand123, model123);

            productsCall.enqueue(new Callback<ProductsModel>() {
                @Override
                public void onResponse(@NonNull Call<ProductsModel> call, @NonNull Response<ProductsModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            mProductDataList.addAll(response.body().getData());
                            pullRefreshLayout.setRefreshing(false);
                            recyclerView.setAdapter(productsAdapter);
                            productsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception exc) {
                        Toast.makeText(getContext(), "" + exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductsModel> call, @NonNull Throwable t) {
                    Log.v("tagy", t.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                    alertDialogBuilder.setMessage(t.getMessage());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        } catch (Exception exc) {
            Toast.makeText(getContext(), "" + exc, Toast.LENGTH_SHORT).show();
        }
    }

    private void serverFilterWithCountry(String country) {
        try {
            mProductDataList = new ArrayList<>();
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<ProductsModel> productsCall = retrofit.productsWithCountry(mUEmail, mCategory, getLang(), country);

            productsCall.enqueue(new Callback<ProductsModel>() {
                @Override
                public void onResponse(@NonNull Call<ProductsModel> call, @NonNull Response<ProductsModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            mProductDataList.addAll(response.body().getData());
                            pullRefreshLayout.setRefreshing(false);
                            recyclerView.setAdapter(productsAdapter);
                            productsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception exc) {
                        Toast.makeText(getContext(), "" + exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductsModel> call, @NonNull Throwable t) {
                    Log.v("tagy", t.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                    alertDialogBuilder.setMessage(t.getMessage());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        } catch (Exception exc) {
            Toast.makeText(getContext(), "" + exc, Toast.LENGTH_SHORT).show();
        }
    }

    private void serverFilterWithSerialNum(String serial) {
        try {
            mProductDataList = new ArrayList<>();
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<ProductsModel> productsCall = retrofit.productsWithSerialNum(mUEmail, mCategory, getLang(), serial);

            productsCall.enqueue(new Callback<ProductsModel>() {
                @Override
                public void onResponse(@NonNull Call<ProductsModel> call, @NonNull Response<ProductsModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            mProductDataList.addAll(response.body().getData());
                            pullRefreshLayout.setRefreshing(false);
                            recyclerView.setAdapter(productsAdapter);
                            productsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception exc) {
                        Toast.makeText(getContext(), "" + exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductsModel> call, @NonNull Throwable t) {
                    Log.v("tagy", t.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                    alertDialogBuilder.setMessage(t.getMessage());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        } catch (Exception exc) {
            Toast.makeText(getContext(), "" + exc, Toast.LENGTH_SHORT).show();
        }
    }

    private void serverFilterWithYear(String year) {
        try {
            mProductDataList = new ArrayList<>();
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<ProductsModel> productsCall = retrofit.productsWithYear(mUEmail, mCategory, getLang(), year);

            productsCall.enqueue(new Callback<ProductsModel>() {
                @Override
                public void onResponse(@NonNull Call<ProductsModel> call, @NonNull Response<ProductsModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            mProductDataList.addAll(response.body().getData());
                            pullRefreshLayout.setRefreshing(false);
                            recyclerView.setAdapter(productsAdapter);
                            productsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception exc) {
                        Toast.makeText(getContext(), "" + exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductsModel> call, @NonNull Throwable t) {
                    Log.v("tagy", t.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                    alertDialogBuilder.setMessage(t.getMessage());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        } catch (Exception exc) {
            Toast.makeText(getContext(), "" + exc, Toast.LENGTH_SHORT).show();
        }
    }

    private void serverNormal() {
        try {
            mProductDataList = new ArrayList<>();
            Api retrofit = MyRetrofitClient.getBase().create(Api.class);

            final Call<ProductsModel> productsCall = retrofit.productsWithMail(mUEmail, getLang(), mCategory);

            productsCall.enqueue(new Callback<ProductsModel>() {
                @Override
                public void onResponse(@NonNull Call<ProductsModel> call, @NonNull Response<ProductsModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            mProductDataList.addAll(response.body().getData());
                            pullRefreshLayout.setRefreshing(false);
                            recyclerView.setAdapter(productsAdapter);
                            productsAdapter.notifyDataSetChanged();
                        } else {
                            pullRefreshLayout.setRefreshing(false);
                            Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception exc) {
                        pullRefreshLayout.setRefreshing(false);
                        // Toast.makeText(getContext(), "" + exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductsModel> call, @NonNull Throwable t) {
                    Log.v("tagy", t.getMessage());
                    pullRefreshLayout.setRefreshing(false);
                    alertDialogBuilder.setMessage(t.getMessage());
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        } catch (Exception exc) {
            Toast.makeText(getContext(), getString(R.string.no_products_found), Toast.LENGTH_SHORT).show();
        }
    }

    private void turnOnServers(Integer key) {
        switch (key) {
            case 1:
                serverProductsWithMail();
                initAdapterWith();
                break;
            case 3:
                serverProductsAll();
                initAdapterAllProducts();
                break;
            default:
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void initAdapterWith() {
        productsAdapter = new ProductsRecyclerAdapter(getContext(), mProductDataList,
                new ProductsRecyclerAdapter.ListAllListeners() {
                    @Override
                    public void onCardViewClick(ProductsModel.DataBean produtsModel) {

                        Log.e("plz", produtsModel.getProductName());

                        CachePot.getInstance().push("pName", produtsModel.getProductName());
                        CachePot.getInstance().push("pId", produtsModel.getProductNumber());
                        CachePot.getInstance().push("pPrice", produtsModel.getProductPrice());
                        CachePot.getInstance().push("pNumber", produtsModel.getProductNumber());
                        CachePot.getInstance().push("pCurrency", produtsModel.getCurrency());

                        setSharedPreference.setimg1(produtsModel.getImage1());
                        setSharedPreference.setimg2(produtsModel.getImage2());

                        CachePot.getInstance().push("pDate", produtsModel.getDate());
                        CachePot.getInstance().push("pCountry", produtsModel.getCountry());
                        CachePot.getInstance().push("pBrand", produtsModel.getBrand());
                        CachePot.getInstance().push("pModel", produtsModel.getModel());

                        CachePot.getInstance().push("uId", produtsModel.getId());
                        CachePot.getInstance().push("uMobile", produtsModel.getMobile());
                        CachePot.getInstance().push("uName", produtsModel.getName());
                        CachePot.getInstance().push("uImage", produtsModel.getImage());
                        CachePot.getInstance().push("langy", getLang());

                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_frameLayout, new ProductDetail()).commit();
                    }

                    @Override
                    public void onFavButtonClick(View v, int position, boolean isFav) {
                        if (getSharedPreference.getLoginStatus()) {
                            if (isFav) {
                                serverAddToFav(position);
                            } else {
                                serverRemoveFromFav(position);
                            }
                        }else {
                            Toast.makeText(getContext(), getString(R.string.signin_first), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void serverRemoveFromFav(int position) {

        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        final Call<AddToFavModel> productsCall = retrofit.addToFavourite(mUEmail, mProductDataList.get(position).getPid(), false);

        productsCall.enqueue(new Callback<AddToFavModel>() {
            @Override
            public void onResponse(@NonNull Call<AddToFavModel> call, @NonNull Response<AddToFavModel> response) {
                try {

                    if (response.isSuccessful()) {

                        Toast.makeText(getContext(), "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                        if (getSharedPreference.getLoginStatus()) {
                            turnOnServers(1);
                        } else if (!getSharedPreference.getLoginStatus()) {
                            turnOnServers(3);
                        }

                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ignored) {
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddToFavModel> call, @NonNull Throwable t) {
                pullRefreshLayout.setRefreshing(false);
                alertDialogBuilder.setMessage(t.getMessage());
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void serverAddToFav(int position) {
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        final Call<AddToFavModel> productsCall = retrofit.addToFavourite(mUEmail, mProductDataList.get(position).getPid(), true);

        productsCall.enqueue(new Callback<AddToFavModel>() {
            @Override
            public void onResponse(@NonNull Call<AddToFavModel> call, @NonNull Response<AddToFavModel> response) {
                try {


                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "" + response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                        if (getSharedPreference.getLoginStatus()) {
                            turnOnServers(1);
                        } else if (!getSharedPreference.getLoginStatus()) {
                            turnOnServers(3);
                        }

                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ignored) {
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddToFavModel> call, @NonNull Throwable t) {
                pullRefreshLayout.setRefreshing(false);
                alertDialogBuilder.setMessage(t.getMessage());
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void initAdapterAllProducts() {
        mAllProductsAdapter = new AllProductsAdapter(getContext(), mProductAllDataList,
                new AllProductsAdapter.ListAllListeners() {
                    @Override
                    public void onCardViewClick(ProductsAllModel.DataBean produtsAllModel) {

                        Log.e("plz", produtsAllModel.getProductName());

                        CachePot.getInstance().push("pName", produtsAllModel.getProductName());
                        CachePot.getInstance().push("pId", produtsAllModel.getProductNumber());
                        CachePot.getInstance().push("pPrice", produtsAllModel.getProductPrice());
                        CachePot.getInstance().push("pNumber", produtsAllModel.getProductNumber());
                        CachePot.getInstance().push("pCurrency", produtsAllModel.getCurrency());
                        CachePot.getInstance().push("pImage1", produtsAllModel.getImage1());
                        CachePot.getInstance().push("pImage2", produtsAllModel.getImage2());
                        CachePot.getInstance().push("pDate", produtsAllModel.getDate());
                        CachePot.getInstance().push("pCountry", produtsAllModel.getCountry());
                        CachePot.getInstance().push("pBrand", produtsAllModel.getBrand());
                        CachePot.getInstance().push("pModel", produtsAllModel.getModel());

                        CachePot.getInstance().push("uId", produtsAllModel.getId());
                        CachePot.getInstance().push("uMobile", produtsAllModel.getMobile());
                        CachePot.getInstance().push("uName", produtsAllModel.getName());
                        CachePot.getInstance().push("langy", getLang());

                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_frameLayout, new ProductDetail()).commit();
                    }
                });
    }

    private void fireBackButtonEvent() {
        ((MainActivity) getActivity()).setOnBackPressedListener(new BaseBackPressedListener(getActivity()) {
            @Override
            public void onBackPressed() {
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new Home())
                        .commit();
            }
        });
    }//end back pressed

    private void getUserInfo() {
        mUEmail = getSharedPreference.getEmail();
        mCategory = getSharedPreference.getCategory();
    }

    public interface myCall_Back {
        void filter(String one, String two, String three, String four, String five, int num);

        void filter(String one, int num);

        void filter(String one, String two, int num);

        void filter(String one, String two, String three, int num);
    }


}