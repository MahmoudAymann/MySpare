package com.spectraapps.myspare;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.spectraapps.myspare.mainscreen.additem.AddItem;
import com.spectraapps.myspare.mainscreen.favourite.Favourite;
import com.spectraapps.myspare.mainscreen.home.Home;
import com.spectraapps.myspare.mainscreen.notification.Notification;
import com.spectraapps.myspare.mainscreen.profile.Profile;

/**
 * Created by MahmoudAyman on 02/01/2018.
 */

public class AdapterMainViewPager extends FragmentPagerAdapter {


    AdapterMainViewPager(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 4:
                return new Home();
            case 3:
                return new Favourite();
            case 2:
                return new AddItem();
            case 1:
                return new Notification();
            case 0:
                return new Profile();
            default:
                return null;
        }
    }//end getItem

    @Override
    public int getCount() {
        return 5;
    }

}//end class

