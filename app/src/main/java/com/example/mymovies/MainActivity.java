package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;


import com.example.mymovies.data.Movie;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerViewPosters;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new MovieAdapter();
        ///получем список фильмов
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(NetworkUtils.POPULARITY,2);

        ///получем список фильмом
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);

        ///устанавливаем фильмы у адаптера
        adapter.setMovies(movies);

        ///устанавливаем адаптер в recyclerViewPosters
        recyclerViewPosters.setAdapter(adapter);

    }
}