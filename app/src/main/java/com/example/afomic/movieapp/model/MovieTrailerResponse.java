package com.example.afomic.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by afomic on 07-May-17.
 */

public class MovieTrailerResponse {
    @SerializedName("results")
    private ArrayList<MovieTrailer> mTrailers;
    public ArrayList<MovieTrailer> getTrailers(){
        return mTrailers;
    }
}
