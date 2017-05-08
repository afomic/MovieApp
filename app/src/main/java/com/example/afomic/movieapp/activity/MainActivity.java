package com.example.afomic.movieapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afomic.movieapp.R;
import com.example.afomic.movieapp.adapter.MovieAdapter;
import com.example.afomic.movieapp.data.Constant;
import com.example.afomic.movieapp.data.MovieDataLayer;
import com.example.afomic.movieapp.model.Movie;
import com.example.afomic.movieapp.model.MovieResponse;
import com.example.afomic.movieapp.util.MovieAPI;
import com.example.afomic.movieapp.util.MovieAPIFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickedListener,Callback<MovieResponse>{
    RecyclerView mMovieList;
    MovieAdapter mAdapter;
    ProgressBar mBar;
    MovieAPI mMovieAPI;
    List<Movie> mMovies;
    BottomNavigationView mNavigationView;
    MovieDataLayer dbData;
    TextView mErrorMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mErrorMsg=(TextView) findViewById(R.id.tv_error_message);
        mMovieList=(RecyclerView) findViewById(R.id.movie_list);

        MovieAPIFactory mController=MovieAPIFactory.getInstance();
        mMovieAPI=mController.getMovieApi();

        dbData=new MovieDataLayer(this);

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
        mMovies=dbData.getMovies();
        mAdapter=new MovieAdapter(MainActivity.this,mMovies,this);
        mMovieList.setAdapter(mAdapter);
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

}
