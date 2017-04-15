package com.example.afomic.movieapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.afomic.movieapp.R;
import com.example.afomic.movieapp.data.Constant;
import com.example.afomic.movieapp.model.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    ImageView mPosterImage;
    TextView mTitle,mRating,mOverview,mDateReleased;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDateReleased=(TextView) findViewById(R.id.movie_date);
        mTitle=(TextView) findViewById(R.id.movie_title);
        mPosterImage=(ImageView) findViewById(R.id.detail_movie_poster);
        mRating=(TextView) findViewById(R.id.movie_rating);
        mOverview=(TextView) findViewById(R.id.overview);
        String mMovieJson=getIntent().getStringExtra(Constant.JSON_STRING);
        try {
            JSONObject object=new JSONObject(mMovieJson);
            Movie item=new Movie(object);
            mDateReleased.setText(item.getReleasedDate());
            mRating.setText(item.getRatings()+"/10");
            mTitle.setText(item.getTitle());
            mOverview.setText(item.getMoviePlot());

            Picasso.with(DetailActivity.this)
                    .load(item.getImage())
                    .placeholder(R.drawable.placeholder)
                    .into(mPosterImage);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
