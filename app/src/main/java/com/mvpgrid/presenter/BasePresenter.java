package com.mvpgrid.presenter;

import android.app.Activity;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.mvpgrid.common.Utils;

/**
 * Created by Akshay on 22/08/2020.
 */

abstract class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {

    final Utils global;

    BasePresenter(Activity _activity) {
        global = new Utils(_activity);
    }
}
