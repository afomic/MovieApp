package com.example.afomic.movieapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by afomic on 06-May-17.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="movie_table";
    private static final int VERSION=1;
    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MovieContract.onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MovieContract.onUpgrade(db,oldVersion,newVersion);

    }
}
