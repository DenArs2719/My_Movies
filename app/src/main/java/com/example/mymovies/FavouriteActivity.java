///activity who will show to us info about our favourite films
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
import android.widget.ListView;

import com.example.mymovies.data.FavouriteMovie;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity
{
    private RecyclerView recyclerViewFavouriteMovies;
    private MovieAdapter adapter;
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
        setContentView(R.layout.activity_favourite);
        recyclerViewFavouriteMovies = findViewById(R.id.recyclerViewFavouriteMovies);
        adapter = new MovieAdapter();
        recyclerViewFavouriteMovies.setLayoutManager(new GridLayoutManager(this,2));
        ///устанавливаем адаптер у recyclerView
        recyclerViewFavouriteMovies.setAdapter(adapter);

        ///получаем сслыку на наш viewModel
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        ///получаем список любимых фильмов
        LiveData<List<FavouriteMovie>> favouriteMovies = viewModel.getFavouriteMovies();

        ///добавляем observer
        favouriteMovies.observe(this, new Observer<List<FavouriteMovie>>()
        {
            @Override
            public void onChanged(List<FavouriteMovie> favouriteMovie)
            {
                List<Movie> movies = new ArrayList<>();
                if(favouriteMovie != null)
                {
                    movies.addAll(favouriteMovie);
                    adapter.setMovies(movies);
                }
            }
        });

    }
}