package com.example.afomic.movieapp.activity;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.afomic.movieapp.R;
import com.example.afomic.movieapp.adapter.ReviewAdapter;
import com.example.afomic.movieapp.adapter.TrailerAdapter;
import com.example.afomic.movieapp.data.Constant;
import com.example.afomic.movieapp.data.MovieDataLayer;
import com.example.afomic.movieapp.model.Movie;
import com.example.afomic.movieapp.model.MovieReviewResponse;
import com.example.afomic.movieapp.model.MovieTrailer;
import com.example.afomic.movieapp.model.MovieTrailerResponse;
import com.example.afomic.movieapp.util.MovieAPI;
import com.example.afomic.movieapp.util.MovieAPIFactory;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    ImageView mPosterImage;
    TextView mTitle,mRating,mOverview,mDateReleased;
    boolean isAFavorite=false;
    MovieDataLayer dbData;
    FloatingActionButton fab;
    RecyclerView mMovieReviewList,mMovieTrailerList;
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    Movie mItem;
    int mItemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDateReleased=(TextView) findViewById(R.id.movie_date);
        mTitle=(TextView) findViewById(R.id.movie_title);
        mPosterImage=(ImageView) findViewById(R.id.detail_movie_poster);
        mRating=(TextView) findViewById(R.id.movie_rating);
        mOverview=(TextView) findViewById(R.id.overview);
        mCollapsingToolbarLayout =(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.TRANSPARENT);

        mMovieReviewList=(RecyclerView) findViewById(R.id.movie_review_list);
        mMovieTrailerList=(RecyclerView) findViewById(R.id.movie_trailer_list);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(this);

        mMovieTrailerList.setLayoutManager(mLayoutManager);
        mMovieReviewList.setLayoutManager(new LinearLayoutManager(this));

        fab=(FloatingActionButton) findViewById(R.id.fab);
        Toolbar mToolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbData=new MovieDataLayer(this);


        Bundle mBundle=getIntent().getExtras();
        mItem=mBundle.getParcelable(Constant.BUNDLE_MOVIE);
       if(mItem==null){
           return;
       }
        mItemID=mItem.getID();
        MovieAPIFactory mFactory=MovieAPIFactory.getInstance();
        MovieAPI mMovieAPI=mFactory.getMovieApi();

        Call<MovieReviewResponse> mReviewCall=mMovieAPI.getReviews(mItemID,Constant.API_KEY);
        mReviewCall.enqueue(mReviewResponseCallback);


        Call<MovieTrailerResponse> mTrailerCall=mMovieAPI.getTrailers(mItemID,Constant.API_KEY);
        mTrailerCall.enqueue(mTrailerResponseCallback);
        String mString =mTrailerCall.request().url().toString();
        Log.e(Constant.TAG,mString);

        mDateReleased.setText(mItem.getReleasedDate());
        mRating.setText(mItem.getRatings()+"/10");
        mTitle.setText(mItem.getTitle());
        mOverview.setText(mItem.getMoviePlot());
        Picasso.with(DetailActivity.this)
                .load(mItem.getCompleteImageURL())
                .placeholder(R.drawable.placeholder)
                .into(mPosterImage);

        isAFavorite=dbData.contain(mItemID);
        //if movie is not a favorite movie, set the fab icon as exmpty star
        if(!isAFavorite){
            fab.setImageResource(R.drawable.ic_favorite);
        }

    }
    public void addFavorite(View v){
        if(isAFavorite){
            fab.setImageResource(R.drawable.ic_favorite);
            dbData.remove(mItemID);
            isAFavorite=false;
        }else {
            dbData.addMovie(mItem);
            fab.setImageResource(R.drawable.ic_icon);
            isAFavorite=true;
        }

    }
    Callback<MovieReviewResponse> mReviewResponseCallback =new Callback<MovieReviewResponse>() {
        @Override
        public void onResponse(Call<MovieReviewResponse> call, Response<MovieReviewResponse> response) {
            if(response.isSuccessful()){
                MovieReviewResponse mResponse=response.body();
                ReviewAdapter mAdapter=new ReviewAdapter(DetailActivity.this,mResponse.getReviews());
                mMovieReviewList.setAdapter(mAdapter);
            }
        }

        @Override
        public void onFailure(Call<MovieReviewResponse> call, Throwable t) {

        }
    };
    Callback<MovieTrailerResponse> mTrailerResponseCallback =new Callback<MovieTrailerResponse>() {
        @Override
        public void onResponse(Call<MovieTrailerResponse> call, Response<MovieTrailerResponse> response) {

            if (response.isSuccessful()){
                MovieTrailerResponse mTrailerResponse=response.body();
                TrailerAdapter mAdapter=new TrailerAdapter(DetailActivity.this,mTrailerResponse.getTrailers());
                mMovieTrailerList.setAdapter(mAdapter);
            }

        }

        @Override
        public void onFailure(Call<MovieTrailerResponse> call, Throwable t) {

        }
    };
}
