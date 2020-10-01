package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mymovies.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = NetworkUtils.buildURL(NetworkUtils.POPULARITY,1).toString();
        Log.i("MyResult",url);
    }
}