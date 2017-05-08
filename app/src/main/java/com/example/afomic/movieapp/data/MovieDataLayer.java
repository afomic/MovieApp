package com.example.afomic.movieapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.afomic.movieapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afomic on 06-May-17.
 */

public class MovieDataLayer {
    private SQLiteDatabase db;
    private DatabaseOpenHelper helper;
    public MovieDataLayer(Context context){
        helper=new DatabaseOpenHelper(context);
    }


    private ContentValues getContentValue(Movie entry){
        ContentValues values=new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,entry.getID());
        values.put(MovieContract.MovieEntry.COLUMN_PLOT,entry.getMoviePlot());
        values.put(MovieContract.MovieEntry.COLUMN_RATING,entry.getRatings());
        values.put(MovieContract.MovieEntry.COLUMN_TITLE,entry.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_RELEASED_DATE,entry.getReleasedDate());
        values.put(MovieContract.MovieEntry.COLUMN_IMAGE_URL,entry.getImageURL());
        return values;
    }

    public void addMovie(Movie entry){
        try {
            db=helper.getWritableDatabase();
            db.insert(MovieContract.MovieEntry.TABLE_NAME,null,getContentValue(entry));
        } catch (Exception e){
            e.printStackTrace();

        }finally {
            if(db!=null){
                db.close();
            }
        }

    }




    public List<Movie> getMovies(){
        Cursor cu;
        try {
            db = helper.getReadableDatabase();
            cu =db.query(MovieContract.MovieEntry.TABLE_NAME,null,null,null,null,null,null);
            List<Movie> movies = new ArrayList<>();
            while (cu.moveToNext()) {
                int mID =cu.getInt(cu.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
                String mRating=cu.getString(cu.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING));
                String mRelease=cu.getString(cu.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASED_DATE));
                String mTitle=cu.getString(cu.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
                String mPlot=cu.getString(cu.getColumnIndex(MovieContract.MovieEntry.COLUMN_PLOT));
                String mImage=cu.getString(cu.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE_URL));
                Movie entry = new Movie(mTitle,mPlot,mImage,mRelease,mRating,mID);
                movies.add(entry);
            }
            cu.close();
            return movies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (db != null) db.close();
        }
    }


    public boolean contain(int id){
        boolean ans;
        try{
            db=helper.getReadableDatabase();
            String selection=MovieContract.MovieEntry.COLUMN_MOVIE_ID+"=?";
            String[] selectionArgs={String.valueOf(id)};
            Cursor cursor=db.query(MovieContract.MovieEntry.TABLE_NAME,null,selection,selectionArgs,null,null,null);
            ans=cursor.moveToFirst();
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
            ans= false;
        }finally {
            if(db!=null){
                db.close();
            }
        }
        return ans;
    }
    public void remove(int id){
        try{
            db=helper.getWritableDatabase();
            String selection=MovieContract.MovieEntry.COLUMN_MOVIE_ID+"=?";
            String[] selectionArgs={String.valueOf(id)};
            db.delete(MovieContract.MovieEntry.TABLE_NAME,selection,selectionArgs);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db!=null){
                db.close();
            }
        }
    }
}
