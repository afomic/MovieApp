package com.example.afomic.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by afomic on 05-May-17.
 */

public class MovieResponse {
    @SerializedName("results")
    List<Movie> movies;

    public List<Movie> getMovies(){
        return movies;
    }
}
