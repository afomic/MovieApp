package com.example.afomic.movieapp.model;

import com.example.afomic.movieapp.data.Constant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by afomic on 14-Apr-17.
 */

public class Movie {
    private String mTitle,mPlot,mImageURl,mReleasedDate,mRating;
    public Movie(JSONObject movie)throws JSONException {
        mTitle=movie.optString(Constant.TITLE);
        mPlot=movie.optString(Constant.MOVIE_PLOT);
        mImageURl=movie.optString(Constant.MOVIE_IMAGE_URL);
        mReleasedDate=movie.optString(Constant.DATE_RELEASED);
        mRating=movie.optString(Constant.MOVIE_RATING);
    }
    public String getTitle(){
        return mTitle;
    }


    public String getImageURL(){
        return Constant.IMAGE_BASE_URL+mImageURl;
    }
    public String getImage(){
        return mImageURl;
    }


    public String getMoviePlot(){
        return mPlot;
    }
    public String getReleasedDate(){
        return mReleasedDate;
    }
    public String getRatings(){
        return mRating;
    }


    public JSONObject toJson() throws JSONException{
        JSONObject movie=new JSONObject();
        movie.put(Constant.TITLE,getTitle());
        movie.put(Constant.MOVIE_PLOT,getMoviePlot());
        movie.put(Constant.MOVIE_IMAGE_URL,getImageURL());
        movie.put(Constant.DATE_RELEASED,getReleasedDate());
        movie.put(Constant.MOVIE_RATING,getRatings());
        return  movie;
    }

}
