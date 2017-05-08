package com.example.afomic.movieapp.util;

import com.example.afomic.movieapp.data.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by afomic on 05-May-17.
 *
 */

public class MovieAPIFactory {
    private  MovieAPI mAPI;
    private static MovieAPIFactory mFactory;
    public static MovieAPIFactory getInstance(){
        if(mFactory==null){
            mFactory=new MovieAPIFactory();
        }
        return mFactory;
    }
    private MovieAPIFactory(){
        Gson mGson= new GsonBuilder().setLenient().create();
        Retrofit mRetrofit=new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(mGson)).build();
        mAPI=mRetrofit.create(MovieAPI.class);

    }

    public MovieAPI getMovieApi(){
        return mAPI;
    }
}
