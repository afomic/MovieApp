package com.example.afomic.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by afomic on 07-May-17.
 */

public class MovieReviewResponse {
    @SerializedName("results")
    private ArrayList<MovieReview> mReviews;
    public ArrayList<MovieReview> getReviews(){
        return mReviews;
    }
}
