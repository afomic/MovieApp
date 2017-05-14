package com.example.afomic.movieapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;

public class MovieProvider extends ContentProvider {
    DatabaseOpenHelper mOpenHelper;
    //match value for directory uri
    public static final int MOVIES=100;
    //match value for single movie uri
    public static final int MOVIE_BY_ID=101;

    public static final UriMatcher sUriMatcher=buildUriMatcher();

    public MovieProvider() {
    }

    public static UriMatcher buildUriMatcher(){
        UriMatcher mMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(MovieContract.AUTHORITY,MovieContract.PATH_MOVIE,MOVIES);
        mMatcher.addURI(MovieContract.AUTHORITY,MovieContract.PATH_MOVIE+"/#",MOVIE_BY_ID);
        return mMatcher;
    }
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        final  SQLiteDatabase db=mOpenHelper.getWritableDatabase();
        int match =sUriMatcher.match(uri);
        int mNumberOfRowDeleted;

        switch (match){
            case MOVIES:
                mNumberOfRowDeleted=db.delete(MovieContract.MovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case MOVIE_BY_ID:
                //get the id of the movie been queried
                String id =uri.getLastPathSegment();
                String mSelection= MovieContract.MovieEntry.COLUMN_MOVIE_ID+"=?";
                String[] mSelectionArgs={id};
                mNumberOfRowDeleted=db.delete(MovieContract.MovieEntry.TABLE_NAME,
                        mSelection,
                        mSelectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return mNumberOfRowDeleted;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        int match=sUriMatcher.match(uri);
        final SQLiteDatabase db=mOpenHelper.getWritableDatabase();
        Uri mReturnedUri;
        switch (match){
            case MOVIES:
                long id=db.insert(MovieContract.MovieEntry.TABLE_NAME,null,values);
                if(id>0){ // checks if the insertion is successful
                    mReturnedUri= ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI,id);
                }else {
                    throw  new SQLException("failed to insert "+uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return mReturnedUri;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper=new DatabaseOpenHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        final  SQLiteDatabase db=mOpenHelper.getReadableDatabase();
        int match =sUriMatcher.match(uri);
        Cursor mReturnedCursor;

        switch (match){
            case MOVIES:
                mReturnedCursor=db.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE_BY_ID:
                //get the id of the movie been queried
                String id =uri.getLastPathSegment();
                String mSelection= MovieContract.MovieEntry.COLUMN_MOVIE_ID+"=?";
                String[] mSelectionArgs={id};
                mReturnedCursor=db.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri"+uri);
        }
        return mReturnedCursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
