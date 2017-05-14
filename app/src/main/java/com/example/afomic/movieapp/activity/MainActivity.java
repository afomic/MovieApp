package com.example.afomic.movieapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.afomic.movieapp.R;
import com.example.afomic.movieapp.adapter.MovieAdapter;
import com.example.afomic.movieapp.data.Constant;
import com.example.afomic.movieapp.data.MovieContract;
import com.example.afomic.movieapp.model.Movie;
import com.example.afomic.movieapp.model.MovieResponse;
import com.example.afomic.movieapp.util.MovieAPI;
import com.example.afomic.movieapp.util.MovieAPIFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickedListener,
        Callback<MovieResponse>,LoaderManager.LoaderCallbacks<Cursor>{
    RecyclerView mMovieList;
    MovieAdapter mAdapter;
    ProgressBar mBar;
    MovieAPI mMovieAPI;
    List<Movie> mMovies;
    BottomNavigationView mNavigationView;
    TextView mErrorMsg;
    private final int MOVIE_LOADER_ID=205;
    private final String BUNDLE_PARAM_URI="uri";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mErrorMsg=(TextView) findViewById(R.id.tv_error_message);
        mMovieList=(RecyclerView) findViewById(R.id.movie_list);

        MovieAPIFactory mController=MovieAPIFactory.getInstance();
        mMovieAPI=mController.getMovieApi();


        mBar=(ProgressBar) findViewById(R.id.progressBar);
        mNavigationView=(BottomNavigationView) findViewById(R.id.navigation);

        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_nav_top:
                        setPath(Constant.TOP_RATED_MOVIE_PATH);
                        break;
                    case R.id.menu_nav_fav:
                        readMovieFromDB();
                        break;
                    case R.id.menu_nav_popular:
                        setPath(Constant.POPULAR_MOVIE_PATH);
                        break;
                }
                return true;
            }
        });

        mNavigationView.setSelectedItemId(R.id.menu_nav_top);
        int screenWidth= getResources().getConfiguration().screenWidthDp;
        int numberOfRows=screenWidth/150;

        RecyclerView.LayoutManager manager=new GridLayoutManager(MainActivity.this,numberOfRows);
        mMovieList.setLayoutManager(manager);


    }

    public void readMovieFromDB(){
        Bundle uriBundle=new Bundle();
       LoaderManager mLoaderManager=getSupportLoaderManager();
       Loader<Cursor> mFavoriteMovieLoader = mLoaderManager.getLoader(MOVIE_LOADER_ID);
        if(mFavoriteMovieLoader==null){
            mLoaderManager.initLoader(MOVIE_LOADER_ID,uriBundle,this);
        }else {
            mLoaderManager.restartLoader(MOVIE_LOADER_ID,uriBundle,this);
        }
    }

    @Override
    public void onMovieItemClick(Movie item) {
        Intent mIntent=new Intent(MainActivity.this,DetailActivity.class);
        mIntent.putExtra(Constant.BUNDLE_MOVIE,item);
        startActivity(mIntent);

    }

    @Override
    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
        if(response.isSuccessful()){
            mBar.setVisibility(View.GONE);
            mMovieList.setVisibility(View.VISIBLE);
            MovieResponse mResponse=response.body();
            mMovies=mResponse.getMovies();
            mAdapter=new MovieAdapter(MainActivity.this,mMovies,this);
            mMovieList.setAdapter(mAdapter);
        }

    }

    @Override
    public void onFailure(Call<MovieResponse> call, Throwable t) {
        t.printStackTrace();
        mBar.setVisibility(View.GONE);
        mErrorMsg.setVisibility(View.VISIBLE);
    }
    public void setPath(String urlPath){
        mMovieList.setVisibility(View.GONE);
        mErrorMsg.setVisibility(View.GONE);
        mBar.setVisibility(View.VISIBLE);
        Call<MovieResponse> mCall=mMovieAPI.getMoviesByPath(urlPath,Constant.API_KEY);
        mCall.enqueue(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mMovieList.setVisibility(View.GONE);
                mErrorMsg.setVisibility(View.GONE);
                mBar.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public Cursor loadInBackground() {
                return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        MovieContract.MovieEntry._ID);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.e(Constant.TAG, "onLoadFinished: am called");
        mBar.setVisibility(View.GONE);
        mMovieList.setVisibility(View.VISIBLE);
        mMovies=getMovies(data);
        mAdapter=new MovieAdapter(MainActivity.this,mMovies,this);
        mMovieList.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.e(Constant.TAG, "onLoaderReset: am called" );

    }

    public ArrayList<Movie> getMovies(Cursor cursor){
        ArrayList<Movie> movies = new ArrayList<>();
        while (cursor.moveToNext()) {
            int mID =cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
            String mRating=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING));
            String mRelease=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASED_DATE));
            String mTitle=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
            String mPlot=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_PLOT));
            String mImage=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE_URL));
            Movie entry = new Movie(mTitle,mPlot,mImage,mRelease,mRating,mID);
            movies.add(entry);
        }
        cursor.close();
        return movies;
    }
}
