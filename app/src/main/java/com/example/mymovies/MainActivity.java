package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.example.mymovies.data.Movie;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(NetworkUtils.POPULARITY,1);
        ArrayList<Movie> arrayList = new ArrayList<>();
        arrayList = JSONUtils.getMoviesFromJSON(jsonObject);
        StringBuilder builder = new StringBuilder();

        for (Movie movie :arrayList)
        {
            builder.append(movie.getTitle()).append("\n");
        }

        Log.i("MyMovie",builder.toString());

    }
}