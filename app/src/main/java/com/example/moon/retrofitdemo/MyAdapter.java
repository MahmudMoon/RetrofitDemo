package com.example.moon.retrofitdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<ImageObject> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
            genre = (TextView) view.findViewById(R.id.tv_albumId);
            year = (TextView) view.findViewById(R.id.tv_imageid);
            imageView = (ImageView)view.findViewById(R.id.iv_image);
        }
    }


    public MyAdapter(List<ImageObject> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageObject movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(String.valueOf(movie.getAlbumId()));
        holder.year.setText(String.valueOf(movie.getId()));
        Picasso.get().load(movie.getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}