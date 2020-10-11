package com.example.mymovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>
{
    private ArrayList<Movie> movies;
    private OnPosterClickListener onPosterClickListener;

    public MovieAdapter()
    {
        movies = new ArrayList<>();
    }

    interface  OnPosterClickListener
    {
        void onPosterClick(int position);
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

    public void addMovies(ArrayList<Movie> movies)
    {
        this.movies.addAll(movies);

        ///когда добавили новые элементы,оповешаем об этом наш адаптер
        notifyDataSetChanged();
    }


    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;

        ///когда добавили новые элементы,оповешаем об этом наш адаптер
        notifyDataSetChanged();
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageViewSmallPoster;

        public MovieViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onPosterClickListener != null)
                    {
                        onPosterClickListener.onPosterClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
