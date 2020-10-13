package com.example.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;
import com.example.mymovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>
{
    private List<Movie> movies;


    private OnPosterClickListener onPosterClickListener;
    private OnReachEndListener onReachEndListener;

    public MovieAdapter()
    {
        movies = new ArrayList<>();
    }

    ///интерфейс для обработки нажатия на фильм(постер)
    public interface OnPosterClickListener
    {
        void onPosterClick(int position);
    }

    ///интерфейс для подгрузки фильмом,когда пользователь долистал до конца первую старницу и т.д
    public interface OnReachEndListener
    {
        void onReachEnd();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ///создаем наш View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position)
    {
        ///подгружаем данные, когда пользователь практически дошел до конца списка
        if(position > movies.size() - 4 && onReachEndListener != null)
        {
            onReachEndListener.onReachEnd();
        }
        ///получаем фильм
        Movie movie = movies.get(position);
        ///получем картинку
        ImageView imageView = holder.imageViewSmallPoster;

        ///работаем с Picasso , и загружаем изображение
        Picasso.get().load(movie.getPosterPath()).into(imageView);
    }

    @Override
    public int getItemCount()
    {
        return movies.size();
    }

    public void addMovies(List<Movie> movies)
    {
        this.movies.addAll(movies);

        ///когда добавили новые элементы,оповешаем об этом наш адаптер
        notifyDataSetChanged();
    }


    public List<Movie> getMovies()
    {
        return movies;
    }

    public void setMovies(List<Movie> movies)
    {
        this.movies = movies;

        ///когда добавили новые элементы,оповешаем об этом наш адаптер
        notifyDataSetChanged();
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener)
    {
        this.onPosterClickListener = onPosterClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener)
    {
        this.onReachEndListener = onReachEndListener;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageViewSmallPoster;

        public MovieViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(onPosterClickListener != null)
                    {
                        onPosterClickListener.onPosterClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
