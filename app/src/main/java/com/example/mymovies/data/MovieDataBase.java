package com.example.mymovies.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class,FavouriteMovie.class},version = 3,exportSchema = false)
public abstract class MovieDataBase extends RoomDatabase
{
    private static MovieDataBase dataBase;
    private static final String DB_NAME = "movies.db";
    private static final Object LOCK = new Object();

    public static MovieDataBase getInstance(Context context)
    {
        ///для многопоточности
        synchronized (LOCK) {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(context, MovieDataBase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return dataBase;
    }

    public abstract MovieDao movieDao();
}
