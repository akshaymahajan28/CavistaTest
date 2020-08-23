package com.mvpgrid.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.mvpgrid.presenter.Processor;

/*
 * @param <V> view
 * @param <P> presenter
 */
public abstract class BaseActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpActivity<V, P> {

    public final Processor processor = new Processor(this);
    public Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = BaseActivity.this;
    }

    /*
     * set flag for starting activity
     */
    public void startActivityWithIntent(Intent intent) {
        startActivity(intent);
        finish();
    }

    /*
     * Show process dialog
     */
    protected void showProcessor() {
        processor.showDialog();
    }

    /*
     * hide processor
     */
    public void hideProcessor() {
        processor.hideDialog();
    }
}
