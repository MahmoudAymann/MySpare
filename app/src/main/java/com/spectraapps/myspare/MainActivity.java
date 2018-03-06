package com.spectraapps.myspare;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kimkevin.cachepot.CachePot;
import com.soundcloud.android.crop.Crop;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.bottomtabscreens.additem.AddItemActivity;
import com.spectraapps.myspare.bottomtabscreens.favourite.Favourite;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.bottomtabscreens.notification.Notification;
import com.spectraapps.myspare.bottomtabscreens.profile.Profile;

import com.spectraapps.myspare.helper.IOnBackPressed;
import com.spectraapps.myspare.login.LoginActivity;
import com.spectraapps.myspare.model.CategoriesModel;
import com.spectraapps.myspare.model.LoginModel;
import com.spectraapps.myspare.model.UpdateProfileImageModel;
import com.spectraapps.myspare.model.inproducts.ProductsAllModel;
import com.spectraapps.myspare.navdrawer.AboutActivity;
import com.spectraapps.myspare.navdrawer.ProfileActivity;
import com.spectraapps.myspare.navdrawer.ResetPassword;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.products.ProductsFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import devlight.io.library.ntb.NavigationTabBar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProductsFragment.myCall_Back, Home.HomeCallBack {

    @SuppressLint("StaticFieldLeak")
    public static TextView mToolbarText;
    public static String image_path1;
    protected IOnBackPressed onBackPressedListener;
    protected DrawerLayout mDrawer;
    protected NavigationView navigationView;
    Locale locale;
    Toolbar mToolBar;
    CircleImageView mNavCircleImageView;
    TextView mNavNameTextView, mNavEmailTextView;
    String mId, mName, mEmail, mToken, mMobile, mImage;
    ListSharedPreference listSharedPreference = new ListSharedPreference();

    boolean mIsLogged;
    AlertDialog.Builder alertDialogBuilder;
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.GET_ACCOUNTS
    };
    private String langhere;

    public static void restartActivity(Activity activity) {
        activity.recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLAyoutLanguage();

        mToolBar = findViewById(R.id.main_toolbar);
        mToolbarText = findViewById(R.id.toolbar_title);
        mToolbarText.setText(R.string.home_title);

        initNavigationDrawer();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frameLayout, new Home()).commit();

        initBottomTabBar();

        setAlertDialog();

        getUserInfo();

        langhere = listSharedPreference.getLanguage(getApplicationContext());

    }//end onCreate()

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("plzlog", "" + mIsLogged);

            mNavNameTextView.setText(getIntent().getStringExtra("name"));
            mNavEmailTextView.setText(getIntent().getStringExtra("email"));
            Picasso.with(MainActivity.this)
                    .load(getIntent().getStringExtra("image"))
                    .error(R.drawable.profile_placeholder)
                    .placeholder(R.drawable.profile_placeholder)
                    .into(mNavCircleImageView);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setLAyoutLanguage() {
        String langStr = listSharedPreference.getLanguage(getApplicationContext());
        if (langStr.equals("en")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            this.getApplicationContext().getResources().updateConfiguration(config, null);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            this.getApplicationContext().getResources().updateConfiguration(config, null);
        }
    }

    private void initNavigationDrawer() {
        mDrawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);

        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        mNavCircleImageView = header.findViewById(R.id.nav_header_imageView);
        mNavNameTextView = header.findViewById(R.id.nav_header_name);
        mNavEmailTextView = header.findViewById(R.id.nav_header_email);
        mNavCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!checkPermissions()){
                   if (mIsLogged)
                   Crop.pickImage(MainActivity.this);
                   else
                       Toast.makeText(MainActivity.this, "Login First", Toast.LENGTH_SHORT).show();
               }

            }
        });
    }

    private void serverUpdateProfileImage() {
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        File file1 = new File(image_path1);
        RequestBody mFile1 = RequestBody.create(MediaType.parse("image/*"), file1);
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), "");

        MultipartBody.Part image1 = MultipartBody.Part.createFormData("image", file1.getName(), mFile1);

        Call<UpdateProfileImageModel> updateProfileImageCall = retrofit.uploadProfileImage(id, image1);

        updateProfileImageCall.enqueue(new Callback<UpdateProfileImageModel>() {
            @Override
            public void onResponse(Call<UpdateProfileImageModel> call, Response<UpdateProfileImageModel> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, ""+response.body().getStatus().getTitle(), Toast.LENGTH_SHORT).show();
                    updateNavigationBarImage(response.body().getData().getImage());
                } else {
                    Toast.makeText(MainActivity.this, "" + response.body().getStatus().getTitle() + " ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileImageModel> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateNavigationBarImage(String image) {
        Picasso.with(MainActivity.this)
                .load(image)
                .error(R.drawable.profile_placeholder)
                .placeholder(R.drawable.profile_placeholder)
                .into(mNavCircleImageView);

        listSharedPreference.setImage(getApplicationContext(),image);
    }

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

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
                //Toast.makeText(this, "done perm", Toast.LENGTH_SHORT).show();
                Crop.pickImage(MainActivity.this);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
            image_path1 = getRealPathFromURIPath(result.getData(), MainActivity.this);
            Log.d("plzx", "" + image_path1);
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            mNavCircleImageView.setImageURI(Crop.getOutput(result));
            serverUpdateProfileImage();
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(MainActivity.this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(
                contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the locale has changed
        if (!locale.equals(newConfig.locale)) {
            locale = newConfig.locale;

            this.setContentView(R.layout.activity_main);
            NavigationView navigationView = this.findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(MainActivity.this);
        }

    }

    private void initBottomTabBar() {

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home_black_24dp),
                        Color.parseColor(colors[0]))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_favourite_black_24dp),
                        Color.parseColor(colors[0]))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_add_black_24dp),
                        Color.parseColor(colors[0]))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_notifications_black_24dp),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_notify_selected_black_24dp))
                        .badgeTitle("5")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_person_black_24dp),
                        Color.parseColor(colors[0]))
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                beginFragmentTransactions(index);
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
                addToolbarTitleAndIcons(index);
                navigationTabBar.getModels().get(index).hideBadge();
            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                final NavigationTabBar.Model model = navigationTabBar.getModels().get(3);
                navigationTabBar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        model.showBadge();
                    }
                }, 100);
            }
        }, 500);

        //background Color
        navigationTabBar.setBgColor(Color.parseColor(colors[1]));
        navigationTabBar.setBackgroundColor(Color.parseColor(colors[2]));
        //badgetColor
        navigationTabBar.setBadgeBgColor(Color.RED);
        navigationTabBar.setBadgeSize(20);
    }//end initUi

    private void beginFragmentTransactions(int index) {
        switch (index) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new Home()).commit();
                break;
            case 1:
                if (mIsLogged)
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_frameLayout, new Favourite()).commit();
                else
                    Toast.makeText(MainActivity.this, "Login First", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                if (mIsLogged)
                startActivity(new Intent(MainActivity.this, AddItemActivity.class));
                else
                    Toast.makeText(MainActivity.this, "Login First", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                if (mIsLogged)
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new Notification()).commit();
                else
                Toast.makeText(MainActivity.this, "Login First", Toast.LENGTH_SHORT).show();
                break;
            case 4:
               // if (mIsLogged)
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new Profile()).commit();
              //  else
                  //  Toast.makeText(MainActivity.this, "Login First", Toast.LENGTH_SHORT).show();
        }//end switch
    }

    private void addToolbarTitleAndIcons(int index) {
        switch (index) {
            case 4:
                mToolbarText.setText(getString(R.string.profile_title));
                break;
            case 3:
                mToolbarText.setText(getString(R.string.notifications_title));
                break;
            case 1:
                mToolbarText.setText(getString(R.string.favourite));
                break;
            case 0:
                mToolbarText.setText(getString(R.string.home_title));
        }//end switch
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.onBackPressed();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.updatePass_nav) {
            startActivity(new Intent(MainActivity.this, ResetPassword.class));
        }else if (id == R.id.updateAccount_nav) {
            if (mIsLogged) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                i.putExtra("name", mName);
                i.putExtra("email", mEmail);
                i.putExtra("mobile", mMobile);
                i.putExtra("image", mImage);
                i.putExtra("id", mId);
                startActivity(i);
            }
            else
                Toast.makeText(this, "please sign in first", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.language_nav) {
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else if (id == R.id.logout_nav) {
            setLogout();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else if (id == R.id.nav_privacy) {
            Uri uriUrl = Uri.parse("http://myspare.net");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        } else if (id == R.id.nav_contactus) {
            Uri uriUrl = Uri.parse("http://myspare.net/contact-us");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setAlertDialog() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, you will change the language Now!");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // Toast.makeText(MainActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                if (listSharedPreference.getLanguage(getApplicationContext()).equals("en")) {
                    listSharedPreference.setLanguage(getApplicationContext(), "ar");
                    restartActivity(MainActivity.this);
                } else {
                    listSharedPreference.setLanguage(getApplicationContext(), "en");
                    restartActivity(MainActivity.this);
                }
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }//end setAlertDialog

    public void setOnBackPressedListener(IOnBackPressed onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    private void setLogout() {

        listSharedPreference.setLoginStatus(getApplicationContext(), false);
        listSharedPreference.setUName(getApplicationContext()," ");
        listSharedPreference.setEmail(getApplicationContext()," ");
        listSharedPreference.setUId(getApplicationContext()," ");
        listSharedPreference.setMobile(getApplicationContext()," ");
        listSharedPreference.setImage(getApplicationContext()," ");

        CachePot.getInstance().push("islogged", false);

    }

    private void getUserInfo() {
        if (mIsLogged) {

            Context context = getApplicationContext();
            mId = listSharedPreference.getUId(context);
            mName = listSharedPreference.getUName(context);
            mEmail = listSharedPreference.getEmail(context);
            mToken = listSharedPreference.getToken(context);
            mMobile = listSharedPreference.getToken(context);
            mImage = listSharedPreference.getImage(context);

            mNavNameTextView.setText(mName);
            mNavEmailTextView.setText(mEmail);
            Picasso.with(MainActivity.this)
                    .load(mImage)
                    .error(R.drawable.profile_placeholder)
                    .placeholder(R.drawable.profile_placeholder)
                    .into(mNavCircleImageView);
        }//end if

        Log.d("userinfo",mId+" "+mName+" "+" "+mEmail+" "+mToken+" "+mMobile+" "+mImage+" "+mIsLogged);

    }//end getUserInfo


    @Override
    public void ProudctSFrag(String year) {
        Bundle bundle = new Bundle();
        bundle.putString("yearpop", year);
        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(bundle);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_frameLayout, fragment).commit();
    }

    @Override
    public void HomeFrag(String categ) {
        Bundle bundle = new Bundle();
        bundle.putString("home", categ);
     bundle.putString("lang",langhere);
        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(bundle);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_frameLayout, fragment).commit();
    }
}//end class main
