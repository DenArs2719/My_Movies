package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


import com.example.mymovies.adapters.MovieAdapter;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerViewPosters;
    private MovieAdapter adapter;
    private Switch switchSort;
    private TextView textViewPopularity;
    private TextView textViewTopRated;


    private MainViewModel viewModel;

    ///петод для создания меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    ///метод для возможности нажатия на элементы меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        ///получаем id элемента на который нажали
        int itemId = item.getItemId();

        ///выбираем ,на что нажали
        switch (itemId)
        {
            case R.id.itemMain:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                Intent intentToFavourite = new Intent(this,FavouriteActivity.class);
                startActivity(intentToFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

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
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
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

        ///обработка нажатия на фильм
        adapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position)
            {
                ///получаем фильм на который нажали
                Movie movie = adapter.getMovies().get(position);

                ///создаем Intent
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("id",movie.getId());
                startActivity(intent);
            }
        });

        adapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener()
        {
            @Override
            public void onReachEnd()
            {

            }
        });

        LiveData<List<Movie>> moviesFromLifeData = viewModel.getMovies();
        ///добавляем observer
        moviesFromLifeData.observe(this, new Observer<List<Movie>>()
        {
            ///Теперь ,когда каждый раз данные будут меняться в базе.Мы будем их устанавливать у adapter
            @Override
            public void onChanged(List<Movie> movies)
            {
                adapter.setMovies(movies);
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
        downloadData(methodOfSort,1);

    }

    private void downloadData(int methodOfSort, int page)
    {
        ///получем список фильмов
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(methodOfSort,page);

        ///получем список фильмом
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);

        ///если пришли новые данные, то мы их загрузим
        if(movies != null && !movies.isEmpty())
        {
            ///отчистим предыдушие данные
            viewModel.deleteAllMovies();
            for(Movie movie: movies)
            {
                ///и вставим новые данные
                viewModel.insertMovie(movie);
            }
        }
    }
}