package com.spectraapps.myspare;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kimkevin.cachepot.CachePot;
import com.spectraapps.myspare.api.Api;
import com.spectraapps.myspare.bottomtabscreens.additem.AddItemActivity;
import com.spectraapps.myspare.bottomtabscreens.favourite.Favourite;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.bottomtabscreens.notification.Notification;
import com.spectraapps.myspare.bottomtabscreens.profile.Profile;

import com.spectraapps.myspare.helper.IOnBackPressed;
import com.spectraapps.myspare.login.LoginActivity;
import com.spectraapps.myspare.model.UpdateProfileImageModel;
import com.spectraapps.myspare.navdrawer.AboutActivity;
import com.spectraapps.myspare.navdrawer.ProfileActivity;
import com.spectraapps.myspare.navdrawer.UpdatePasswordApproval;
import com.spectraapps.myspare.network.MyRetrofitClient;
import com.spectraapps.myspare.products.ProductsFragment;
import com.spectraapps.myspare.utility.ListSharedPreference;
import com.squareup.picasso.Picasso;

import java.io.File;
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
        implements NavigationView.OnNavigationItemSelectedListener, ProductsFragment.myCall_Back {

    private static final int REQUEST_GALLERY_CODE = 200;
    @SuppressLint("StaticFieldLeak")
    public static TextView mToolbarText;
    protected IOnBackPressed onBackPressedListener;
    protected DrawerLayout mDrawer;
    protected NavigationView navigationView;

    ListSharedPreference.Set setSharedPreference;
    ListSharedPreference.Get getSharedPreference;

    Locale locale;
    Toolbar mToolBar;
    CircleImageView mNavCircleImageView;
    TextView mNavNameTextView, mNavEmailTextView;
    String mId, mName, mEmail, mToken, mMobile, mImage;

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
    private ProgressDialog progressDialog;

    public static void restartActivity(Activity activity) {
        activity.recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSharedPreference = new ListSharedPreference.Set(MainActivity.this.getApplicationContext());
        getSharedPreference = new ListSharedPreference.Get(MainActivity.this.getApplicationContext());
        mIsLogged = getSharedPreference.getLoginStatus();

        setLAyoutLanguage();

        mToolBar = findViewById(R.id.main_toolbar);
        mToolbarText = findViewById(R.id.toolbar_title);
        mToolbarText.setText(R.string.home_title);

        initNavigationDrawer();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frameLayout, new Home()).commit();

        initBottomTabBar();

        langhere = getSharedPreference.getLanguage();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);

    }//end onCreate()

    @Override
    protected void onStart() {
        super.onStart();
        getUserInfo();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setLAyoutLanguage() {
        String langStr = getSharedPreference.getLanguage();
        //Toast.makeText(MainActivity.this, ""+langStr, Toast.LENGTH_SHORT).show();
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
        this.setContentView(R.layout.activity_main);
        NavigationView navigationView = this.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
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
                if (mIsLogged)
                    if (!checkPermissions()) {
                        pickImage();
                    } else {
                        Toast.makeText(MainActivity.this, "permission failed", Toast.LENGTH_SHORT).show();
                    }
                else
                    Toast.makeText(MainActivity.this, "Login First", Toast.LENGTH_SHORT).show();
            }
        });

        if (!mIsLogged) {
            Menu menu = navigationView.getMenu();
            MenuItem nav_logout = menu.findItem(R.id.logout_nav);
            nav_logout.setTitle(getString(R.string.action_sign_in));
            nav_logout.setIcon(R.drawable.ic_account_nav_24dp);
        } else {
            Menu menu = navigationView.getMenu();
            MenuItem nav_logout = menu.findItem(R.id.logout_nav);
            nav_logout.setTitle(getString(R.string.logout));
            nav_logout.setIcon(R.drawable.ic_power_nav_24dp);
        }
    }

    private void serverUpdateProfileImage(String image_path) {
        progressDialog.show();
        Api retrofit = MyRetrofitClient.getBase().create(Api.class);

        File file1 = new File(image_path);
        RequestBody mFile1 = RequestBody.create(MediaType.parse("image/*"), file1);
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), getSharedPreference.getUId());

        MultipartBody.Part image1 = MultipartBody.Part.createFormData("image", file1.getName(), mFile1);

        Call<UpdateProfileImageModel> updateProfileImageCall = retrofit.uploadProfileImage(id, image1);

        updateProfileImageCall.enqueue(new Callback<UpdateProfileImageModel>() {
            @Override
            public void onResponse(@NonNull Call<UpdateProfileImageModel> call, @NonNull Response<UpdateProfileImageModel> response) {
                try {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        updateNavigationBarImage(response.body().getData().getImage());
                        setSharedPreference.setImage(response.body().getData().getImage());
                    } else {
                        progressDialog.dismiss();
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(getString(R.string.error))
                                .setMessage(response.body().getStatus().getTitle())
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.done), null)
                                .create().show();
                    }
                } catch (Exception ignored) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateProfileImageModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void updateNavigationBarImage(String image) {
        Picasso.with(MainActivity.this)
                .load(image)
                .error(R.drawable.profile_placeholder)
                .placeholder(R.drawable.profile_placeholder)
                .into(mNavCircleImageView);
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

    private void pickImage() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String image_path = getRealPathFromUri(uri, MainActivity.this);
            serverUpdateProfileImage(image_path);
        }
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
        } catch (Exception ignored) {
            return null;
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

        final String[] colors = getResources().getStringArray(R.array.tab_bar_colors);

        final NavigationTabBar navigationTabBar = findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home_black_36dp),
                        Color.parseColor(colors[0]))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_favorite_black_36dp),
                        Color.parseColor(colors[0]))
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_add_black_36dp),
                        Color.parseColor(colors[0]))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_notifications_black_36),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_notification_on_black_36dp))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_person_black_36dp),
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

        /*
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
        */

        //background Color
        navigationTabBar.setBgColor(Color.parseColor(colors[1]));
        navigationTabBar.setBackgroundColor(Color.parseColor(colors[2]));
        //badgetColor
        //navigationTabBar.setBadgeBgColor(Color.RED);
        //navigationTabBar.setBadgeSize(15);
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
                else {
                    setAlertDialog(2);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                break;
            case 2:
                if (mIsLogged)
                    startActivity(new Intent(MainActivity.this, AddItemActivity.class));
                else {
                    setAlertDialog(2);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                break;
            case 3:
                if (mIsLogged)
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_frameLayout, new Notification()).commit();
                else {
                    setAlertDialog(2);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                break;
            case 4:
                if (mIsLogged) {
                    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.main_frameLayout, new Profile()).commit();
                }else {
                    setAlertDialog(2);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
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
            if (mIsLogged) {
                startActivity(new Intent(MainActivity.this, UpdatePasswordApproval.class));
            } else {
                setAlertDialog(2);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        } else if (id == R.id.updateAccount_nav) {
            if (mIsLogged) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                i.putExtra("name", mName);
                i.putExtra("email", mEmail);
                i.putExtra("mobile", mMobile);
                i.putExtra("image", mImage);
                i.putExtra("id", mId);
                startActivity(i);
            } else {
                setAlertDialog(2);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        } else if (id == R.id.language_nav) {
            setAlertDialog(1);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else if (id == R.id.logout_nav) {
            if (mIsLogged) {
                setLogout();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
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

    private void setAlertDialog(int key) {
        switch (key) {
            case 1:
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(getString(R.string.change_language_prompt));

                alertDialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (getSharedPreference.getLanguage().equals("en")) {
                            setSharedPreference.setLanguage("ar");
                            restartActivity(MainActivity.this);
                        } else {
                            setSharedPreference.setLanguage("en");
                            restartActivity(MainActivity.this);
                        }
                    }
                });

                alertDialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                break;
            case 2:
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(getString(R.string.signin_first));

                alertDialogBuilder.setPositiveButton(getString(R.string.action_sign_in), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        MainActivity.this.finish();
                    }
                });

                alertDialogBuilder.setNegativeButton(getString(R.string.prompt_continue), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }//end setAlertDialog

    public void setOnBackPressedListener(IOnBackPressed onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    private void setLogout() {
        setSharedPreference.setLoginStatus(false);
        setSharedPreference.setUName("agent");
        setSharedPreference.setEmail("example@domain.com");
        setSharedPreference.setUId("id");
        setSharedPreference.setMobile("0123456789");
        setSharedPreference.setImage("http://myspare.net/public/upload/default.jpg");
    }

    private void getUserInfo() {
        if (mIsLogged) {
            mId = getSharedPreference.getUId();
            mName = getSharedPreference.getUName();
            mEmail = getSharedPreference.getEmail();
            mToken = getSharedPreference.getToken();
            mMobile = getSharedPreference.getMobile();
            mImage = getSharedPreference.getImage();

            mNavNameTextView.setText(mName);
            mNavEmailTextView.setText(mEmail);
            Picasso.with(MainActivity.this)
                    .load(mImage)
                    .error(R.drawable.profile_placeholder)
                    .placeholder(R.drawable.profile_placeholder)
                    .into(mNavCircleImageView);
        }//end if

        Log.d("userinfo", mId + " " + mName + " " + " " + mEmail + " " + mToken + " " + mMobile + " " + mImage + " " + mIsLogged);

    }//end getUserInfo

    @Override
    public void filter(String one, String two, String three, String four, String five, int num) {
        ProductsFragment fragment = new ProductsFragment();
        switch (num) {
            case 12345:
                Bundle bundle = new Bundle();
                bundle.putString("country12345", one);
                bundle.putString("brand12345", two);
                bundle.putString("model12345", three);
                bundle.putString("year12345", four);
                bundle.putString("serial12345", five);
                fragment.setArguments(bundle);
                break;
        }
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_frameLayout, fragment).commit();
    }

    @Override
    public void filter(String one, int num) {
        ProductsFragment fragment = new ProductsFragment();
        switch (num) {
            case 1:
                Bundle bundle1 = new Bundle();
                bundle1.putString("country", one);
                fragment.setArguments(bundle1);
                break;
            case 2:
                Bundle bundle2 = new Bundle();
                bundle2.putString("brand", one);
                fragment.setArguments(bundle2);
                break;
            case 3:
                Bundle bundle3 = new Bundle();
                bundle3.putString("model", one);
                fragment.setArguments(bundle3);
                break;
            case 4:
                Bundle bundle4 = new Bundle();
                bundle4.putString("year", one);
                fragment.setArguments(bundle4);
                break;
            case 5:
                Bundle bundle5 = new Bundle();
                bundle5.putString("serial", one);
                fragment.setArguments(bundle5);
                break;
        }

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_frameLayout, fragment).commit();
    }

    @Override
    public void filter(String one, String two, int num) {
        ProductsFragment fragment = new ProductsFragment();
        switch (num) {
            case 12:
                Bundle bundle12 = new Bundle();
                bundle12.putString("country12", one);
                bundle12.putString("brand12", two);
                fragment.setArguments(bundle12);
                break;
            case 14:
                Bundle bundle14 = new Bundle();
                bundle14.putString("country14", one);
                bundle14.putString("year14", two);
                fragment.setArguments(bundle14);
                break;
            case 15:
                Bundle bundle15 = new Bundle();
                bundle15.putString("country15", one);
                bundle15.putString("serial15", two);
                fragment.setArguments(bundle15);
                break;
        }//end switch

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_frameLayout, fragment).commit();
    }

    @Override
    public void filter(String one, String two, String three, int num) {
        ProductsFragment fragment = new ProductsFragment();
        switch (num) {
            case 123:
                Bundle bundle = new Bundle();
                bundle.putString("country123", one);
                bundle.putString("brand123", two);
                bundle.putString("model123", three);
                fragment.setArguments(bundle);
                break;
        }

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_frameLayout, fragment).commit();
    }

}//end class main
