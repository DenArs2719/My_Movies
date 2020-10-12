///activity who will show us all popular films
package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.PrimaryKey;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.data.FavouriteMovie;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity
{
    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;
    private Movie movie;
    private ImageView imageViewAddToFavourite;

    private int id = 0;

    private MainViewModel viewModel;
    private FavouriteMovie favouriteMovie;


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
        setContentView(R.layout.activity_detail);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewRating = findViewById(R.id.textViewRating);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewOverview = findViewById(R.id.textViewOverview);
        imageViewAddToFavourite = findViewById(R.id.imageViewAddToFavourite);

        ///получаем наши данные
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("id"))
        {
            id = intent.getIntExtra("id",-1);
        }
        else
        {
            ///закрываем активность,если ошибка
            finish();
        }

        ///получаем наш  viewModel
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        /// получаем наш фильм
        movie = viewModel.getMovieById(id);

        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewOverview.setText(movie.getOverview());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewRating.setText(Double.toString(movie.getVoteAverage()));

        setFavourite();


    }

    public void onClickChangeFavourite(View view)
    {

        ///значит,что фильма в базе данных нет
        if(favouriteMovie == null)
        {
            ///вставляем нужный обьект
            viewModel.insertMovieToFavourite(new FavouriteMovie(movie));
            Toast.makeText(DetailActivity.this, R.string.added_to_favourite,Toast.LENGTH_SHORT).show();
        }
        else
        {
            viewModel.deleteMovieFromFavourite(favouriteMovie);
            Toast.makeText(DetailActivity.this, R.string.deleted_from_favourite,Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    private void setFavourite()
    {
        favouriteMovie = viewModel.getFavouriteMovieById(id);
        if(favouriteMovie == null)
        {
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_add_to);
        }
        else
        {
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_remove);
        }
    }
}