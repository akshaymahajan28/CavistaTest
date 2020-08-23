package com.mvpgrid.common;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.mvpgrid.listener.CallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Akshay on 22/08/2020.
 */
public class RestApiConsumer extends AsyncTask<String, Void, String> implements ApiConsumer {

    private String finalUrl;
    private Request request;
    private int responseCode = 0;
    private String response_string;

    private final CallbackListener callbackprocessor;


    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * @param callbackprocessor
     */
    RestApiConsumer(Activity activity, CallbackListener callbackprocessor) {
        this.callbackprocessor = callbackprocessor;
        Utils global = (Utils) callbackprocessor;
    }

    /**
     * @param url call GET API request
     */
    @Override
    public void get(String url) {
        Log.d("url:", url);
        finalUrl = url;
        System.out.println("url = " + url);
        request = new Request.Builder()
                .url(url)
                .addHeader(Constants.HeaderConstant.AUTHORIZATION, "Client-ID 137cda6b5008a7c")
                .build();
        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    /**
     * @param result in string format
     *               Handle each api request result
     */
    @Override
    protected void onPostExecute(String result) {
        try {
            System.out.println("response = " + result);
            if (!TextUtils.isEmpty(result)) {
                Log.d("response:", result);
                Object json = new JSONTokener(result).nextValue();
                JSONObject obj = new JSONObject();
                if (json instanceof JSONObject) {
                    obj = new JSONObject(result);
                } else if (json instanceof JSONArray) {
                    obj = new JSONObject();
                    obj.put(Constants.DATA, json);
                    obj.put(Constants.STATUS, responseCode);
                }
                obj.put(Constants.STATUS_CODE, responseCode);
                this.callbackprocessor.processResponse(obj, finalUrl);
            } else {
                JSONObject obj = new JSONObject();
                obj.put(Constants.STATUS_CODE, responseCode);
                this.callbackprocessor.processResponse(obj, finalUrl);
            }
        } catch (JSONException | NullPointerException e) {
            Log.e("Exception : ", Log.getStackTraceString(e));
            JSONObject jsonObject = new JSONObject();
            try {
                this.callbackprocessor.processResponse(jsonObject, finalUrl);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Perform operation before every API call
     */
    @Override
    protected void onPreExecute() {
    }

    /**
     * @param strings execute api request either GET or POST
     * @return response in string format
     */
    @Override
    protected String doInBackground(String... strings) {
        try {
            final OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();
            response_string = null;
            Response response = client.newCall(request).execute();
            response_string = response.body().string();
            responseCode = response.code();
        } catch (Exception e) {
            Log.e("Exception : ", Log.getStackTraceString(e));
        }
        return response_string;
    }
}