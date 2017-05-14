package com.example.afomic.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by afomic on 14-May-17.
 * this is the model which hold he state of the mainActivity view
 */

public class MainActivityState implements Parcelable{
    private boolean isErrorMessageOn,isProgressBarOn,isMovieListOn;

    public MainActivityState() {
        isErrorMessageOn = false;
        isProgressBarOn =false;
        isMovieListOn = true;
    }

    protected MainActivityState(Parcel in) {
        isErrorMessageOn = in.readByte() != 0;
        isProgressBarOn = in.readByte() != 0;
        isMovieListOn = in.readByte() != 0;
    }

    public static final Creator<MainActivityState> CREATOR = new Creator<MainActivityState>() {
        @Override
        public MainActivityState createFromParcel(Parcel in) {
            return new MainActivityState(in);
        }

        @Override
        public MainActivityState[] newArray(int size) {
            return new MainActivityState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isErrorMessageOn ? 1 : 0));
        dest.writeByte((byte) (isProgressBarOn ? 1 : 0));
        dest.writeByte((byte) (isMovieListOn ? 1 : 0));
    }

    public boolean isErrorMessageOn() {
        return isErrorMessageOn;
    }

    public void setErrorMessageOn(boolean mErrorMessageOn) {
        isErrorMessageOn = mErrorMessageOn;
    }

    public boolean isProgressBarOn() {
        return isProgressBarOn;
    }

    public void setProgressBarOn(boolean mProgressBarOn) {
        isProgressBarOn = mProgressBarOn;
    }

    public boolean isMovieListOn() {
        return isMovieListOn;
    }

    public void setMovieListOn(boolean mMovieListOn) {
        isMovieListOn = mMovieListOn;
    }
}
