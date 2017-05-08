package com.example.afomic.movieapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by afomic on 07-May-17.
 */

public class MovieReview {
    @SerializedName("content")
    private String mContent;
    @SerializedName("author")
    private String mAuthor;

    public String getContent() {
        return mContent;
    }

    public String getAuthor() {
        return mAuthor;
    }
}
