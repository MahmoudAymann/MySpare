package com.spectraapps.myspare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.spectraapps.myspare.bottomtabscreens.additem.AddItemActivity;
import com.spectraapps.myspare.bottomtabscreens.favourite.Favourite;
import com.spectraapps.myspare.bottomtabscreens.home.Home;
import com.spectraapps.myspare.bottomtabscreens.notification.Notification;
import com.spectraapps.myspare.bottomtabscreens.profile.Profile;
import com.spectraapps.myspare.navdrawer.AboutActivity;
import com.spectraapps.myspare.navdrawer.UpdatePassword;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar mToolBar;
    @SuppressLint("StaticFieldLeak")
    public static TextView mToolbarText;
    protected DrawerLayout mDrawer;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = findViewById(R.id.main_toolbar);
        mToolbarText = findViewById(R.id.toolbar_title);
        mToolbarText.setText(R.string.home_title);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frameLayout, new Home()).commit();

        initBottomTabBar();
        initNavigationDrawer();



    }

    private void initNavigationDrawer() {
        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new Favourite()).commit();
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, AddItemActivity.class));
                break;
            case 3:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new Notification()).commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frameLayout, new Profile()).commit();
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.updatePass_nav) {
               startActivity(new Intent(MainActivity.this, UpdatePassword.class));
        } else if (id == R.id.logout_nav) {

        } else if (id == R.id.nav_privacy) {

        } else if (id == R.id.nav_callus) {

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}//end class main
