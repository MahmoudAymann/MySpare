package com.spectraapps.myspare.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ForwardingListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by MahmoudAyman on 04/01/2018.
 */

public class MenueHelper {

    private final Context mContext;
    private final MenuBuilder mMenu;
    private final View mAnchor;
    final MenuPopupHelper mPopup;

    MenueHelper.OnMenuItemClickListener mMenuItemClickListener;
    MenueHelper.OnDismissListener mOnDismissListener;
    private View.OnTouchListener mDragListener;

    @SuppressLint("RestrictedApi")
    public MenueHelper(@NonNull Context context, @NonNull View anchor) {
        this(context, anchor, Gravity.LEFT);
        mPopup.setForceShowIcon(true);
    }

    @SuppressLint("RestrictedApi")
    public MenueHelper(@NonNull Context context, @NonNull View anchor, int gravity) {
        this(context, anchor, gravity, android.support.v7.appcompat.R.attr.popupMenuStyle, 0);
        mPopup.setForceShowIcon(true);
    }

    @SuppressLint("RestrictedApi")
    public MenueHelper(@NonNull Context context, @NonNull View anchor, int gravity,
                       @AttrRes int popupStyleAttr, @StyleRes int popupStyleRes) {
        mContext = context;
        mAnchor = anchor;

        mMenu = new MenuBuilder(context);
        mMenu.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                if (mMenuItemClickListener != null) {
                    return mMenuItemClickListener.onMenuItemClick(item);
                }
                return false;
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {
            }
        });

        mPopup = new MenuPopupHelper(context, mMenu, anchor, false, popupStyleAttr, popupStyleRes);
        mPopup.setGravity(gravity);
        mPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mOnDismissListener != null) {
                    mOnDismissListener.onDismiss(MenueHelper.this);
                }
            }
        });

        mPopup.setForceShowIcon(true);

    }

    @SuppressLint("RestrictedApi")
    private void setGravity(int gravity) {
        mPopup.setGravity(gravity);
    }

    /**
     * @return the gravity used to align the popup window to its anchor view
     * @see #setGravity(int)
     */
    @SuppressLint("RestrictedApi")
    public int getGravity() {
        return mPopup.getGravity();
    }

    @NonNull
    public View.OnTouchListener getDragToOpenListener() {
        if (mDragListener == null) {
            mDragListener = new ForwardingListener(mAnchor) {
                @Override
                protected boolean onForwardingStarted() {
                    show();
                    return true;
                }

                @Override
                protected boolean onForwardingStopped() {
                    dismiss();
                    return true;
                }

                @SuppressLint("RestrictedApi")
                @Override
                public ShowableListMenu getPopup() {
                    // This will be null until show() is called.
                    return mPopup.getPopup();
                }
            };
        }

        return mDragListener;
    }

    @NonNull
    public Menu getMenu() {
        return mMenu;
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    public MenuInflater getMenuInflater() {
        return new SupportMenuInflater(mContext);
    }

    public void inflate(@MenuRes int menuRes) {
        getMenuInflater().inflate(menuRes, mMenu);
    }

    @SuppressLint("RestrictedApi")
    public void show() {
        mPopup.show();
    }

    @SuppressLint("RestrictedApi")
    public void dismiss() {
        mPopup.dismiss();
    }

    public void setOnMenuItemClickListener(@Nullable MenueHelper.OnMenuItemClickListener listener) {
        mMenuItemClickListener = listener;
    }

    public void setOnDismissListener(@Nullable MenueHelper.OnDismissListener listener) {
        mOnDismissListener = listener;
    }

    /**
     * Interface responsible for receiving menu item click events if the items
     * themselves do not have individual item click listeners.
     */
    public interface OnMenuItemClickListener {

        boolean onMenuItemClick(MenuItem item);
    }

    /**
     * Callback interface used to notify the application that the menu has closed.
     */
    public interface OnDismissListener {

        void onDismiss(MenueHelper menu);
    }
}