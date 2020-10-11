package com.example.mymovies.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel
{
    private static MovieDataBase dataBase;
    private LiveData<List<Movie>> movies;
    private LiveData<List<FavouriteMovie>> favouriteMovies;

    public MainViewModel(@NonNull Application application)
    {
        super(application);
        dataBase = MovieDataBase.getInstance(getApplication());

        ///метод автоматически будет выполняться в другом программном потоке
        movies = dataBase.movieDao().getAllMovies();
        favouriteMovies = dataBase.movieDao().getAllMoviesFromFavourites();
    }

    public LiveData<List<Movie>> getMovies()
    {
        return movies;
    }

    public LiveData<List<FavouriteMovie>> getFavouriteMovies()
    {
        return favouriteMovies;
    }

    ///метод для получения фильма по id
    public Movie getMovieById(int movieId)
    {
        try {
            return new GetMovieTask().execute(movieId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    ///метод для удаления фильмов
    public void deleteAllMovies()
    {
       new DeleteMoviesTask().execute();
    }

    ///метод для вставки фильма
    public void insertMovie(Movie movie)
    {
        new InsertMoviesTask().execute(movie);
    }

    ///метод для удаления фильма
    public void deleteMovie(Movie movie)
    {
        new DeleteTask().execute(movie);
    }

    ///класс для потока, получения фильма из базы
    private static class GetMovieTask extends AsyncTask<Integer,Void,Movie>
    {

        @Override
        protected Movie doInBackground(Integer... integers) {
            if(integers != null && integers.length > 0 )
            {
                return dataBase.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    ///класс для потока, удаления фильмов из базы
    private static class DeleteMoviesTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            dataBase.movieDao().deleteAllMovies();
            return null;
        }
    }

    ///класс для потока, вставки фильма в базу
    private static class InsertMoviesTask extends AsyncTask<Movie, Void, Void>
    {

        @Override
        protected Void doInBackground(Movie... movies)
        {
            if(movies != null && movies.length > 0)
            {
                dataBase.movieDao().insertMovie(movies[0]);
            }

            return null;
        }
    }

    ///класс для потока, удаления фильма из базы
    private static class DeleteTask extends AsyncTask<Movie, Void, Void>
    {

        @Override
        protected Void doInBackground(Movie... movies)
        {
            if (movies != null && movies.length > 0) {
                dataBase.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    ///работа с базой favourite_movies
    ///метод для вставки фильма
    public void insertMovieToFavourite(FavouriteMovie movie)
    {
        new InsertIntoFavoriteTask().execute(movie);
    }

    ///метод для удаления фильма
    public void deleteMovieFromFavourite(FavouriteMovie movie)
    {
        new DeleteFromFavouriteTask().execute(movie);
    }

    ///метод для получения фильма по id
    public FavouriteMovie getFavouriteMovieById(int movieId)
    {
        try {
            return new GetFavouriteMovieTask().execute(movieId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    ///класс для потока, получения фильма из базы
    private static class GetFavouriteMovieTask extends AsyncTask<Integer,Void,FavouriteMovie>
    {

        @Override
        protected FavouriteMovie doInBackground(Integer... integers) {
            if(integers != null && integers.length > 0 )
            {
                return dataBase.movieDao().getFavouriteMovieById(integers[0]);
            }
            return null;
        }
    }

    ///класс для потока, вставки фильма в базу
    private static class InsertIntoFavoriteTask extends AsyncTask<FavouriteMovie, Void, Void>
    {

        @Override
        protected Void doInBackground(FavouriteMovie... movies)
        {
            if(movies != null && movies.length > 0)
            {
                dataBase.movieDao().insertMovieToFavourite(movies[0]);
            }

            return null;
        }
    }

    ///класс для потока, удаления фильма из базы
    private static class DeleteFromFavouriteTask extends AsyncTask<FavouriteMovie, Void, Void>
    {

        @Override
        protected Void doInBackground(FavouriteMovie... movies)
        {
            if (movies != null && movies.length > 0) {
                dataBase.movieDao().deleteMovieToFavourite(movies[0]);
            }
            return null;
        }
    }
}
