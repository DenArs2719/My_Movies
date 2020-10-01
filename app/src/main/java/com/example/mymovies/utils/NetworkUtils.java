///class who will help us to work with network(take films from www)
package com.example.mymovies.utils;

import android.net.Uri;


import java.net.MalformedURLException;
import java.net.URL;

import static android.provider.CalendarContract.CalendarCache.URI;

public class NetworkUtils
{
    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";

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

    public static URL buildURL(int sortBy,int page)
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
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }



}
