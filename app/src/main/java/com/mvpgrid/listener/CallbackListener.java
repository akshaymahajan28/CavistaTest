package com.mvpgrid.listener;

import org.json.JSONObject;

/**
 * Created by Akshay on 23-08-2020.
 */

public interface CallbackListener {
    /**
     * @param json_response response in json format
     * @param finalUrl      function used for after api call
     */
    void processResponse(JSONObject json_response, String finalUrl);

    /**
     * Show alert like no internet connection
     */
    void showAlert();


}
