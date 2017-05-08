package com.example.afomic.movieapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.afomic.movieapp.R;
import com.example.afomic.movieapp.data.Constant;
import com.example.afomic.movieapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by afomic on 14-Apr-17.
 *
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private Context mContext;
    private List<Movie> mMovies;
    private MovieItemClickedListener mListener;
    public interface MovieItemClickedListener{
        public void onMovieItemClick(Movie item);
    }
    public MovieAdapter(Context context, List<Movie> movies,MovieItemClickedListener listener){
        mMovies=movies;
        mContext=context;
        mListener=listener;
    }
    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.movie_item,parent,false);
        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Movie item=getItem(position);
        Picasso.with(mContext)
                .load(item.getCompleteImageURL())
                .placeholder(R.drawable.placeholder)
                .into(holder.mMovieImage);
        holder.mMovieName.setText(item.getTitle());

    }
    public Movie getItem(int position){
        return mMovies.get(position);
    }

    @Override
    public int getItemCount() {
        if(mMovies==null){
            return 0;
        }

        return mMovies.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mMovieImage;
        TextView mMovieName;
        public MovieHolder(View itemView) {
            super(itemView);
            mMovieImage=(ImageView) itemView.findViewById(R.id.im_movie_image);
            mMovieName=(TextView) itemView.findViewById(R.id.movie_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemClickedPosition=getAdapterPosition();
            mListener.onMovieItemClick(getItem(itemClickedPosition));
        }
    }
}