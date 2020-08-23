package com.mvpgrid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Akshay on 22/08/2020.
 */

public class GridData implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("images")
    private ArrayList<ImageData> images;

    public ArrayList<ImageData> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageData> images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
