package com.example.afomic.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.afomic.movieapp.data.Constant;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by afomic on 14-Apr-17.
 */

public class Movie implements Parcelable{
    @SerializedName("original_title")
    private String mTitle;
    @SerializedName("overview")
    private String mPlot;
    @SerializedName("poster_path")
    private String mImageURl;
    @SerializedName("release_date")
    private String mReleasedDate;
    @SerializedName("vote_average")
    private String mRating;
    @SerializedName("id")
    private int mID;

    public Movie(String mTitle, String mPlot, String mImageURl, String mReleasedDate, String mRating, int mID) {
        this.mTitle = mTitle;
        this.mPlot = mPlot;
        this.mImageURl = mImageURl;
        this.mReleasedDate = mReleasedDate;
        this.mRating = mRating;
        this.mID = mID;
    }

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mPlot = in.readString();
        mImageURl = in.readString();
        mReleasedDate = in.readString();
        mRating = in.readString();
        mID = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPlot);
        dest.writeString(mImageURl);
        dest.writeString(mReleasedDate);
        dest.writeString(mRating);
        dest.writeInt(mID);
    }


    public String getTitle(){
        return mTitle;
    }
    public String getCompleteImageURL(){
        return Constant.IMAGE_BASE_URL+mImageURl;
    }
    public String getImageURL(){ return mImageURl;}


    public int getID() {
        return mID;
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



}
