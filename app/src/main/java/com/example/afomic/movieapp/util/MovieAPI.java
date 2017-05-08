package com.example.afomic.movieapp.util;

import com.example.afomic.movieapp.model.MovieResponse;
import com.example.afomic.movieapp.model.MovieReviewResponse;
import com.example.afomic.movieapp.model.MovieTrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by afomic on 05-May-17.
 */

public interface MovieAPI {
    @GET("/3/movie/{path}")
    Call<MovieResponse> getMoviesByPath(@Path("path") String name, @Query("api_key") String key);

    @GET("/3/movie/{id}/reviews")
    Call<MovieReviewResponse> getReviews(@Path("id") int id, @Query("api_key") String key);

    @GET("/3/movie/{id}/videos")
    Call<MovieTrailerResponse> getTrailers(@Path("id") int id, @Query("api_key") String key);
}
