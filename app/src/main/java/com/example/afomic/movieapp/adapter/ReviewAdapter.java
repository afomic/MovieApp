package com.example.afomic.movieapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.afomic.movieapp.R;
import com.example.afomic.movieapp.model.MovieReview;

import java.util.ArrayList;

/**
 * Created by afomic on 07-May-17.
 *
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder>{
    private Context mContext;
    private ArrayList<MovieReview> mReviews;
    public ReviewAdapter(Context mContext,ArrayList<MovieReview> mMovieReviews){
        this.mContext=mContext;
        this.mReviews=mMovieReviews;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.review_item_layout,parent,false);
        return new ReviewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        MovieReview mReview=mReviews.get(position);
        holder.mContent.setText(mReview.getContent());
        holder.mAuthor.setText(mReview.getAuthor().concat(" said:"));

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        TextView mAuthor,mContent;
        public ReviewHolder(View itemView) {
            super(itemView);
            mAuthor=(TextView) itemView.findViewById(R.id.tv_review_author);
            mContent=(TextView) itemView.findViewById(R.id.tv_review_content);
        }

    }
}
