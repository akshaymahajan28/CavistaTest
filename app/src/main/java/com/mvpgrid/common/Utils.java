package com.mvpgrid.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.google.gson.Gson;
import com.mvpgrid.R;
import com.mvpgrid.listener.CallbackListener;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class Utils implements CallbackListener {
    private Activity _activity;
    private CallbackListener _callBaclListner;
    private static final String STATUS_CODE = "statusCode";

    public Utils(Activity activity) {
        this._activity = activity;
    }

    /**
     * @param url  in string format to get information
     * @param callbackprocessor in json format
     */
    public void callGetApiFromPresenter(String url, CallbackListener callbackprocessor) {
        Log.e("Imgur_request", url);
        _callBaclListner = callbackprocessor;
        if (isConnectingToInternet()) {
            RestApiConsumer restApi = new RestApiConsumer(_activity, this);
            restApi.get(url);
        } else {
            callbackprocessor.showAlert();
        }
    }

    /*
     * Check whether Internet connection is available.
     */
    private boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) _activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (NetworkInfo anInfo : info)
                if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
        }
        showToast(_activity, "No Internet Connection");
        return false;
    }

    /**
     * Hide keyboard
     */
    public static void hideKeyBoard(Activity context) {
        try {
            View view = context.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (NullPointerException ignored) {

        }
    }

    @Override
    public void processResponse(JSONObject json_response, String finalUrl) {
        try {
            if (json_response.has(STATUS_CODE)) {
                if (json_response.has(STATUS_CODE) && json_response.getInt(STATUS_CODE) == 401) {
                    _callBaclListner.processResponse(json_response, finalUrl);
                } else {
                    _callBaclListner.processResponse(json_response, finalUrl);
                }
            } else {
                _callBaclListner.processResponse(json_response, finalUrl);
            }
        } catch (Exception e) {
            _callBaclListner.processResponse(json_response, finalUrl);
        }
    }


    @Override
    public void showAlert() {

    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static <T> Object getStringToModelData(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, (Type) clazz);
    }


}