package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mymovies.adapters.MovieAdapter;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject>
{
    private RecyclerView recyclerViewPosters;
    private MovieAdapter adapter;
    private Switch switchSort;
    private TextView textViewPopularity;
    private TextView textViewTopRated;

    private MainViewModel viewModel;

    private static final int LOADER_ID = 123;
    private LoaderManager loaderManager;

    private int pageNumber = 1;
    private static boolean isLoading = false;

    private static  int methodOfSort;

    ///метод для создания меню
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
        setContentView(R.layout.activity_main);

        ///ипользуется паттерн SINGLETON
        ///отвечает за все загрузки, которые происходят в приложении
        loaderManager = LoaderManager.getInstance(this);

        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        switchSort = findViewById(R.id.switchSort);
        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewTopRated = findViewById(R.id.textViewTopRated);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        adapter = new MovieAdapter();

        ///чтобы фильмы загрузились сразу
        switchSort.setChecked(true);

        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this,2));

        ///устанавливаем адаптер в recyclerViewPosters
        recyclerViewPosters.setAdapter(adapter);


        ///устанавливаем слушатель
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {
                pageNumber = 1;
                setMethodOfSort(isChecked);
            }
        });
        switchSort.setChecked(false);

        ///обработка нажатия на фильм
        adapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position)
            {
                ///получаем фильм на который нажали
                Movie movie = adapter.getMovies().get(position);

                ///создаем Intent
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("id",movie.getId());
                startActivity(intent);
            }


        });

        adapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener()
        {
            @Override
            public void onReachEnd()
            {
                if(!isLoading)
                {
                    downloadData(methodOfSort,pageNumber);
                }
            }
        });

        LiveData<List<Movie>> moviesFromLifeData = viewModel.getMovies();
        ///добавляем observer
        moviesFromLifeData.observe(this, new Observer<List<Movie>>()
        {
            ///Теперь ,когда каждый раз данные будут меняться в базе.Мы будем их устанавливать у adapter
            @Override
            public void onChanged(List<Movie> movies)
            {

            }
        });

    }

    public void onClickSetPopularity(View view)
    {
        setMethodOfSort(false);
        switchSort.setChecked(false);
    }


    public void onClickSetTopRated(View view)
    {
        setMethodOfSort(true);
        switchSort.setChecked(true);
    }

    private void setMethodOfSort(boolean isTopRated)
    {

        if(isTopRated)
        {
            methodOfSort = NetworkUtils.TOP_RATED;
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewPopularity.setTextColor(getResources().getColor(R.color.white_color));
        }
        else
        {
            methodOfSort = NetworkUtils.POPULARITY;
            textViewPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewTopRated.setTextColor(getResources().getColor(R.color.white_color));
        }
        downloadData(methodOfSort,pageNumber);

    }

    private void downloadData(int methodOfSort, int page)
    {
        ///формируем URL
        URL url = NetworkUtils.buildURL(methodOfSort,page);

        ///создаем bundle
        Bundle bundle = new Bundle();

        ///вставляем данные
        bundle.putString("url",url.toString());

        ///запускаем загрузчик
        ///метод проверит:
        ///-существует ли уже загрузчик , создаст если не существует
        ///- если существует, то он его просто перезапустит
        loaderManager.restartLoader(LOADER_ID,bundle,this);
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle bundle)
    {
        ///создаем наш загрузчик
        NetworkUtils.JSONLoader jsonLoader = new NetworkUtils.JSONLoader(this,bundle);

        ///добавляем слушатель к загрузчику
        jsonLoader.setOnStartLoadingListener(new NetworkUtils.JSONLoader.OnStartLoadingListener() {
            @Override
            public void onStartLoading()
            {
                isLoading = true;
            }
        });
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject jsonObject)
    {
        ///получем список фильмом
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);

        ///если пришли новые данные, то мы их загрузим
        if(movies != null && !movies.isEmpty())
        {
            ///отчистим предыдушие данные
            viewModel.deleteAllMovies();
            for(Movie movie: movies)
            {
                ///и вставим новые данные
                viewModel.insertMovie(movie);
            }

            adapter.addMovies(movies);
            pageNumber++;
        }

        ///после загрузки данных,нам необходимо удалить загрузчик
        loaderManager.destroyLoader(LOADER_ID);

        ///когда загрузка данных завершена
        isLoading = false;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}