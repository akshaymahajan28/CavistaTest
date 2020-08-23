package com.mvpgrid.listener;

/**
 * Created by Akshay on 22-08-2020.
 */

public interface LoaderListener {

    //Show loader dialog on server API call
    void showDialog();

    //Hide loader dialog on success API call
    void hideDialog();
}
