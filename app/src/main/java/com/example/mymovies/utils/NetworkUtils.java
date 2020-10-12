///class who will help us to work with network(take films from www)
package com.example.mymovies.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class NetworkUtils
{
    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String BASE_VIDEO_URL = "https://api.themoviedb.org/3/movie/%s/videos";
    private static final String BASE_REVIEW_URL = "https://api.themoviedb.org/3/movie/%s/reviews";

    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String PARAMS_SORT_BY = "sort_by";
    private static final String PARAMS_PAGE = "page";

    private static final String API_KEY = "0936d8c770393bd195267285dea65d1d";
    private static final String LANGUAGE_VALUE = "ru-RU";

    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_TOP_RATED = "vote_average.desc";


    public static final int POPULARITY = 0;
    public static final int TOP_RATED = 1;

    ///метод,который формирует запрос
    private static URL buildURL(int sortBy,int page)
    {
        URL result = null;
        String methodOfSort;
        if(sortBy == POPULARITY) {
            methodOfSort = SORT_BY_POPULARITY;
        }
        else {
            methodOfSort = SORT_BY_TOP_RATED;
        }
        ///получили строку ввиде адреса ,к которой можем прикреплять запросы
        ///got a string in the form of an address to which we can attach requests
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                    .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE)
                    .appendQueryParameter(PARAMS_SORT_BY,methodOfSort)
                    .appendQueryParameter(PARAMS_PAGE,Integer.toString(page))
                    .build();

        try {
            ///получем нашу строку
            ///get our url
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    ///метод для создания запроса(url) для видео
    private static URL buildVideoURL(int filmId)
    {
        URL result = null;

        Uri uri = Uri.parse(String.format(BASE_VIDEO_URL,filmId)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .build();

        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return result;

    }

    ///метод для получения JSON информации для видео
    public  static JSONObject getJSONForVideo(int filmId)
    {
        JSONObject result = null;
        URL url = buildVideoURL(filmId);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return  result;
    }

    ///метод для создания запроса(url) для отзывов
    private static URL buildReviewURL(int filmId)
    {
        URL result = null;

        Uri uri = Uri.parse(String.format(BASE_REVIEW_URL,filmId)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .build();

        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return result;
    }

    ///метод для получения JSON информации для отзывов
    public  static JSONObject getJSONReviewForVideo(int filmId)
    {
        JSONObject result = null;
        URL url = buildReviewURL(filmId);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return  result;
    }


    ///метод для получения JSON из сети
    ///method for getting JSON from network
    public  static JSONObject getJSONFromNetwork(int sortBy,int page)
    {
        JSONObject result = null;
        URL url = buildURL(sortBy,page);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return  result;
    }

    private static class JSONLoadTask extends AsyncTask<URL,Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(URL... urls)
        {
            JSONObject result = null;
            if(urls == null || urls.length == 0)
            {
                return result;
            }

            ///открываем соединение
            ///open connection
            HttpURLConnection connection = null;
            try
            {
                connection = (HttpURLConnection) urls[0].openConnection();

                ///создаем поток ввода
                ///create an input stream
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                ///читаем строками
                ///read in lines
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                ///для хранения всего результата
                ///to store the entire result
                StringBuilder builder = new StringBuilder();

                ///читаем данные
                ///read data
                String line = bufferedReader.readLine();
                while (line != null)
                {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }

                ///возврашаем нашу строчку
                ///return our result
                result = new JSONObject(builder.toString());

                Log.i("NetworkUtils",result.toString());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                {
                    connection.disconnect();
                }
            }

            return result;
        }
    }



}
