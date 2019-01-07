package com.codificador.themovieapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.codificador.themovieapp.databinding.ItemRowBinding;
import com.codificador.themovieapp.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    ArrayList<Movie> movies;
    Context context;
    private static final String BASE_URL = "http://image.tmdb.org/t/p/w185";

    public MovieAdapter(Context context){
        movies = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_row,parent,false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovieList(List<Movie> list){
        movies.addAll(list);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        ItemRowBinding itemRowBinding;
        public MovieViewHolder(ItemRowBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Movie movie){
            itemRowBinding.title.setText(movie.getTitle());
            itemRowBinding.releaseDate.setText(movie.getRelease_date().split("-")[0]);
            itemRowBinding.voteAvg.setText(""+movie.getVote_average());
            Glide.with(context).load(BASE_URL+movie.getPoster_path()).into(itemRowBinding.thumbnail);
        }
    }
}
