package com.example.afomic.movieapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.afomic.movieapp.R;
import com.example.afomic.movieapp.adapter.MovieAdapter;
import com.example.afomic.movieapp.data.Constant;
import com.example.afomic.movieapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickedListener{
    Spinner mSortOrderSpinner;
    RecyclerView mMovieList;
    MovieAdapter mAdapter;
    ArrayList<Movie> mMovies=new ArrayList<>();
    RelativeLayout progressBarContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSortOrderSpinner=(Spinner) findViewById(R.id.sp_sort_order);
        progressBarContainer=(RelativeLayout)  findViewById(R.id.progress_bar_container) ;
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sorting_option, android.R.layout.simple_selectable_list_item);
        mSortOrderSpinner.setAdapter(spinnerAdapter);
        mSortOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        getData(Constant.POPULAR_MOVIE_URL);
                        break;
                    case 1:
                        getData(Constant.TOP_RATED_MOVIE_URL);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mMovieList=(RecyclerView) findViewById(R.id.movie_list);

        RecyclerView.LayoutManager manager=new GridLayoutManager(MainActivity.this,2);
        mMovieList.setLayoutManager(manager);

        mAdapter=new MovieAdapter(MainActivity.this,mMovies,this);
        mMovieList.setAdapter(mAdapter);

        getData(Constant.POPULAR_MOVIE_URL);

        }

    public class fetchMovies extends AsyncTask<URL,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarContainer.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(URL... params) {
            URL mUrl=params[0];
            HttpURLConnection con=null;
            try{
                con=(HttpURLConnection) mUrl.openConnection();
                InputStream in=con.getInputStream();
                Scanner scanner=new Scanner(in);
                scanner.useDelimiter("\\A");
                if(scanner.hasNext()){
                    return scanner.next();
                }
                return null;
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }finally {
                if(con!=null){
                    con.disconnect();
                }

            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e(Constant.TAG,s);
            if(s!=null){
                getMovies(s);
                progressBarContainer.setVisibility(View.GONE);
            }

        }
    }
    private URL buildURL(String sortType) {
        Uri mApiURI= Uri.parse(sortType).buildUpon()
                .appendQueryParameter(Constant.PARAM_API_KEY,Constant.API_KEY).build();
        try{
            return new URL(mApiURI.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    private void getMovies(String jsonResponse){
        mMovies.clear();
        try{
            JSONObject response=new JSONObject(jsonResponse);
            JSONArray array=response.getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Movie movie=new Movie(object);
                mMovies.add(movie);
            }
        }catch (JSONException e){
            Log.e(Constant.TAG, "Json parsing error: " + e.getMessage());
            return;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMovieItemClick(Movie item) {
        try {
            String movieJson=item.toJson().toString();
            Intent intent=new Intent(MainActivity.this,DetailActivity.class);
            intent.putExtra(Constant.JSON_STRING,movieJson);
            startActivity(intent);
        }catch (JSONException e){
            e.printStackTrace();
        }


    }
    public void getData(String sortBy){
        URL movieURL=buildURL(sortBy);
        if(isOnline()){
            new fetchMovies().execute(movieURL);
        }
    }
}
