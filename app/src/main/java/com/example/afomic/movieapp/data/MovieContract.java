package com.example.afomic.movieapp.data;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;


/**
 * Created by afomic on 06-May-17.
 *
 */

public class MovieContract {

    public static final String AUTHORITY="com.example.afomic.movieapp";
    public static final String PATH_MOVIE="movies";
    public static final String SCHEME="content://";
    public static final Uri BASE_CONTENT_URI= Uri.parse(SCHEME+AUTHORITY);

    public static class MovieEntry implements BaseColumns{
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE).build();
        public static final String TABLE_NAME="movie_table";
        public static final String COLUMN_MOVIE_ID="movie_id";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_RATING="rating";
        public static final String COLUMN_PLOT="plot";
        public static final String COLUMN_RELEASED_DATE="date";
        public static final String COLUMN_IMAGE_URL="image_url";
        
    }
    public static void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+MovieEntry.TABLE_NAME+" ( ";
        sql+=MovieEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,";
        sql+=MovieEntry.COLUMN_MOVIE_ID+" INTEGER, ";
        sql+=MovieEntry.COLUMN_TITLE+" VARCHAR(30), ";
        sql+=MovieEntry.COLUMN_RATING+" VARCHAR(5), ";
        sql+=MovieEntry.COLUMN_PLOT+" VARCHAR(100), ";
        sql+=MovieEntry.COLUMN_RELEASED_DATE+" VARCHAR(20), ";
        sql+=MovieEntry.COLUMN_IMAGE_URL+" VARCHAR(100) ) ";
        db.execSQL(sql);

    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable="DROP TABLE"+ MovieEntry.TABLE_NAME;
        db.execSQL(dropTable);
    }

}
