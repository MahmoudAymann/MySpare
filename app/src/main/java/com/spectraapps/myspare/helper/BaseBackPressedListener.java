package com.spectraapps.myspare.helper;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;

/**
 * Created by MahmoudAyman on 10/02/2018.
 */

public class BaseBackPressedListener implements IOnBackPressed {
    private final FragmentActivity activity;

    protected BaseBackPressedListener(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onBackPressed() {
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}