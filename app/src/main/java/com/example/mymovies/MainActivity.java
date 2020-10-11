package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mymovies.data.Movie;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerViewPosters;
    private MovieAdapter adapter;
    private Switch switchSort;
    private TextView textViewPopularity;
    private TextView textViewTopRated;
    private int numberOfPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this,2));
        switchSort = findViewById(R.id.switchSort);
        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewTopRated = findViewById(R.id.textViewTopRated);
        adapter = new MovieAdapter();
        switchSort.setChecked(true);
        ///устанавливаем адаптер в recyclerViewPosters
        recyclerViewPosters.setAdapter(adapter);

        ///устанавливаем слушатель
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {
                setMethodOfSort(isChecked);
            }
        });
        switchSort.setChecked(false);

        adapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position)
            {
                Toast.makeText(MainActivity.this,"cliked"+position,Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener()
        {
            @Override
            public void onReachEnd()
            {
                Toast.makeText(MainActivity.this,"end of list",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClickSetPopularity(View view)
    {
        setMethodOfSort(false);
        switchSort.setChecked(false);
    }


    public void onClickSetTopRated(View view)
    {
        setMethodOfSort(true);
        switchSort.setChecked(true);
    }

    private void setMethodOfSort(boolean isTopRated)
    {
        int methodOfSort = 0;

        if(isTopRated)
        {
            methodOfSort = NetworkUtils.TOP_RATED;
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewPopularity.setTextColor(getResources().getColor(R.color.white_color));
        }
        else
        {
            methodOfSort = NetworkUtils.POPULARITY;
            textViewPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewTopRated.setTextColor(getResources().getColor(R.color.white_color));
        }

        ///получем список фильмов
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(methodOfSort,1);

        ///получем список фильмом
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        ///устанавливаем фильмы у адаптера
        adapter.setMovies(movies);

    }

    private void loadData(boolean isTopRated,int page)
    {
        int methodOfSort = 0;

        if(isTopRated)
        {
            methodOfSort = NetworkUtils.TOP_RATED;
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewPopularity.setTextColor(getResources().getColor(R.color.white_color));
        }
        else
        {
            methodOfSort = NetworkUtils.POPULARITY;
            textViewPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewTopRated.setTextColor(getResources().getColor(R.color.white_color));
        }

        ///получем список фильмов
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(methodOfSort,page);

        ///получем список фильмом
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        ///устанавливаем фильмы у адаптера
        adapter.setMovies(movies);
    }
}