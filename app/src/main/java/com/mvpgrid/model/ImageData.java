package com.mvpgrid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Akshay on 22/08/2020.
 */

public class ImageData implements Serializable {

    @SerializedName("link")
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
