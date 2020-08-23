package com.mvpgrid.presenter;

import android.app.Activity;

import com.mvpgrid.listener.CallbackListener;
import com.mvpgrid.view.GridView;
import com.mvpgrid.view.activity.ImageGridActivity;

import org.json.JSONObject;

/**
 * Created by Akshay on 22/08/2020.
 */

public class GridPresenter extends BasePresenter<GridView> implements CallbackListener {

    public GridPresenter(Activity activity) {
        super(activity);
    }


    @Override
    public void processResponse(JSONObject json_response, String finalUrl) {
        if (finalUrl.contains(ImageGridActivity.GET_IMAGES_GRID)) {
            ifViewAttached(GridView::hideLoader);
            ifViewAttached(view -> view.setGridResponse(json_response));
        }
    }


    @Override
    public void showAlert() {
        hideLoader();
    }

    public void getGridImage(String sKeyWords) {
        ifViewAttached(GridView::showLoader);
        global.callGetApiFromPresenter(ImageGridActivity.GET_IMAGES_GRID + sKeyWords, this);
    }

    private void hideLoader() {
        ifViewAttached(GridView::hideLoader);
    }
}
