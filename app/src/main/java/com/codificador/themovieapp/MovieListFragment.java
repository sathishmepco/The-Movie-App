package com.codificador.themovieapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.codificador.themovieapp.api.TheMoviesAPI;
import com.codificador.themovieapp.databinding.FragmentMovieListBinding;
import com.codificador.themovieapp.model.Movie;
import java.util.List;

public class MovieListFragment extends Fragment {

    FragmentMovieListBinding binding;
    MovieAdapter adapter;
    String URL = "";
    int page = 1;

    public static MovieListFragment newInstance(String url){
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putString("url",url);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie_list,container,false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents(){
        URL = getArguments().getString("url");
        adapter = new MovieAdapter(getContext());
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setNestedScrollingEnabled(false);

        fetchMoreItems();
        page++;
        fetchMoreItems();
        page++;
        fetchMoreItems();
    }

    private void fetchMoreItems(){
        TheMoviesAPI.fetchMovies(URL+"&page="+page, getContext(), new TheMoviesAPI.MoviesAPICallBack() {
            @Override
            public void updateMoviesList(List<Movie> movies) {
                adapter.setMovieList(movies);
                adapter.notifyDataSetChanged();
            }
        });
    }
}