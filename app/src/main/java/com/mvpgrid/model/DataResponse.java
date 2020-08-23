package com.mvpgrid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Akshay on 22/08/2020.
 */

public class DataResponse implements Serializable {

    @SerializedName("status")
    private int status;

    @SerializedName("data")
    private ArrayList<GridData> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<GridData> getData() {
        return data;
    }

    public void setData(ArrayList<GridData> data) {
        this.data = data;
    }
}
