///convert JSON in object
package com.example.mymovies.utils;


import android.util.Log;

import com.example.mymovies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils
{

    ///по этому ключу получим все JSON обтекты в массив
    ///информация о фильме
    private static  final String KEY_RESULTS = "results";
    private static  final String KEY_VOTE_COUNT = "vote_count";
    private static  final String KEY_TITLE = "title";
    private static  final String KEY_ID = "id";
    private static  final String KEY_ORIGINAL_TITLE = "original_title";
    private static  final String KEY_OVERVIEW = "overview";
    private static  final String KEY_POSTER_PATH = "poster_path";
    private static  final String KEY_BACKDROP_PATH = "backdrop_path";
    private static  final String KEY_VOTE_AVERAGE = "vote_average";
    private static  final String KEY_RELEASE_DATE = "release_date";


    ///для отзывов
    private static final String KEY_CONTENT = "content";
    private static final String KEY_AUTHOR = "author";

    ///для видео
    private static final String KEY_VIDEO = "key";
    private static final String KEY_NAME = "name";

    ///переменные,которые отвечают за размер картинки и ее ссылки
    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_POSTER_SIZE = "w185";
    public static final String BIG_POSTER_SIZE = "w780";

    ///сделав запрос к базе(JSON сайта) получаем массив с фильмами
    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject)
    {
        ArrayList<Movie> arrayList = new ArrayList<>();
        if(jsonObject == null)
        {
            return arrayList;
        }
        try {
            ///создаем jsonArray и получаем его по ключу
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            ///проходимя по всем фильмам
            for(int i = 0;i<jsonArray.length();i++)
            {
                ///получаем наши данные
                ///get our data
                JSONObject objectMovie = jsonArray.getJSONObject(i);
                int id = objectMovie.getInt(KEY_ID);
                int voteCount = objectMovie.getInt(KEY_VOTE_COUNT);
                String tittle = objectMovie.getString(KEY_TITLE);
                String originalTitle = objectMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = objectMovie.getString(KEY_OVERVIEW);
                String posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String backDropPath = objectMovie.getString(KEY_BACKDROP_PATH);
                double voteAverage = objectMovie.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = objectMovie.getString(KEY_RELEASE_DATE);


                ///создаем обьект Movie
                ///created Movie object
                Movie movie = new Movie(id,voteCount,tittle,originalTitle,overview,posterPath,bigPosterPath,backDropPath,voteAverage,releaseDate);

                ///добавляем обьекс в наш массив
                ///add object in our array
                arrayList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }



    ///сделав запрос к базе(JSON сайта) получаем массив с отзывами
    public static ArrayList<Movie> getReviewInfoFromJSON(JSONObject jsonObject)
    {
        ArrayList<Movie> arrayList = new ArrayList<>();
        if(jsonObject == null)
        {
            return arrayList;
        }
        try {
            ///создаем jsonArray и получаем его по ключу
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            ///проходимя по всем фильмам
            for(int i = 0;i<jsonArray.length();i++)
            {
                ///получаем наши данные
                ///get our data
                JSONObject objectMovie = jsonArray.getJSONObject(i);
                int id = objectMovie.getInt(KEY_ID);
                String author = objectMovie.getString(KEY_AUTHOR);
                String content = objectMovie.getString(KEY_CONTENT);



                ///создаем обьект Movie
                ///created Movie object
                Movie movie = new Movie(id,voteCount,tittle,originalTitle,overview,posterPath,bigPosterPath,backDropPath,voteAverage,releaseDate);

                ///добавляем обьекс в наш массив
                ///add object in our array
                arrayList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
