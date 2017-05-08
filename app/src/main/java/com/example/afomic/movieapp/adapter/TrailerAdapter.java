package com.example.afomic.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.afomic.movieapp.R;
import com.example.afomic.movieapp.model.MovieTrailer;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by afomic on 07-May-17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder>{
    private Context mContext;
    private ArrayList<MovieTrailer> mTrailers;
    public TrailerAdapter(Context mContext, ArrayList<MovieTrailer> mTrailers){
        this.mContext=mContext;
        this.mTrailers=mTrailers;

    }
    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.trailer_item_layout,parent,false);
        return new TrailerHolder(v);
    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, int position) {
        MovieTrailer mTrailer=mTrailers.get(position);
        holder.mType.setText(mTrailer.getType());
        holder.mName.setText(mTrailer.getName());
        Log.e(TAG, "onBindViewHolder: name "+mTrailer.getName()+" type "+mTrailer.getType());

    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mName,mType;
        public TrailerHolder(View itemView) {
            super(itemView);
            mName=(TextView) itemView.findViewById(R.id.tv_trailer_name);
            mType=(TextView) itemView.findViewById(R.id.tv_trailer_type);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemClickedPosition=getAdapterPosition();
            MovieTrailer trailer=mTrailers.get(itemClickedPosition);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getURI()  ));
            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(Intent.createChooser(intent,"Watch Trailer..."));
            }
        }
    }

}
