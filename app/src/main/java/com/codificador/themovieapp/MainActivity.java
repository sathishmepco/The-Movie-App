package com.codificador.themovieapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.codificador.themovieapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActionBar toolBar;
    final String API_KEY = "YOUR_API_KEY";

    final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY;
    final String POPULAR_MOVES_URL = "https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY;
    final String UPCOMING_MOVIES_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY;
//    final String UPCOMING_MOVIES_URL = "https://api.themoviedb.org/3/discover/movie?primary_release_year=2019&api_key="+API_KEY;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_now_playing:
                    toolBar.setTitle("Now Playing Movies");
                    loadFragment(MovieListFragment.newInstance(NOW_PLAYING_URL));
                    return true;
                case R.id.navigation_popular:
                    toolBar.setTitle("Popular Movies");
                    loadFragment(MovieListFragment.newInstance(POPULAR_MOVES_URL));
                    return true;
                case R.id.navigation_upcoming:
                    toolBar.setTitle("Upcoming Movies");
                    loadFragment(MovieListFragment.newInstance(UPCOMING_MOVIES_URL));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolBar = getSupportActionBar();

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) binding.navigation.getLayoutParams();
        layoutParams.setBehavior(new MovieNavigationBehavior());

        toolBar.setTitle("Now Playing Movies");
        loadFragment(MovieListFragment.newInstance(NOW_PLAYING_URL));
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}