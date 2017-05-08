package com.example.afomic.movieapp.model;

import com.example.afomic.movieapp.data.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by afomic on 07-May-17.
 */

public class MovieTrailer {
    @SerializedName("name")
    private String mName;
    @SerializedName("type")
    private String mType;
    @SerializedName("key")
    private String mKey;

    public String getName() {
        return mName;
    }

    public String getType() {
        return mType;
    }

    public String getURI() {
        String uri= Constant.YOUTUBE_BASE_URL+mKey;
        return uri;
    }
}
