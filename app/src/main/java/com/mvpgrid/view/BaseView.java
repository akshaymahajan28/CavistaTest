package com.mvpgrid.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;


/**
 * Base view
 */
public interface BaseView extends MvpView {

    // Shows a loading animation while checking auth credentials
    void showLoader();

    // Shows a loading animation while checking auth credentials
    void hideLoader();

}
