///activity who will show us all popular films
package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.PrimaryKey;

import android.content.Intent;
import android.os.Bundle;
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

    private int id = 0;

    private MainViewModel viewModel;
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



    }

    public void onClickChangeFavourite(View view)
    {
        FavouriteMovie favouriteMovie = viewModel.getFavouriteMovieById(id);

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
    }
}