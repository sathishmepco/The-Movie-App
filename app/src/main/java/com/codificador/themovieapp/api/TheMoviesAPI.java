package com.codificador.themovieapp.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.codificador.themovieapp.MyApplication;
import com.codificador.themovieapp.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TheMoviesAPI {
    public static void fetchMovies(String URL, final Context context, final MoviesAPICallBack callBack){
        JsonObjectRequest request = new JsonObjectRequest(URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response == null) {
                            Toast.makeText(context, "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            List<Movie> items = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Movie>>() {}.getType());
                            callBack.updateMoviesList(items);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Failed in calling the web service - "+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
        });
        MyApplication.getInstance().addToRequestQueue(request);
    }

    public interface MoviesAPICallBack{
        void updateMoviesList(List<Movie> movies);
    }
}