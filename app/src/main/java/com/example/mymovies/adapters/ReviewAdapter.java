package com.example.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;
import com.example.mymovies.data.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>
{
    private ArrayList<Review> reviews;

    public ReviewAdapter()
    {
        this.reviews = reviews;
    }

    public void setReviews(ArrayList<Review> reviews)
    {
        this.reviews = reviews;

        ///сообщаем адаптеру ,что данные изменились
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position)
    {
        ///получаем наш отзыв
        Review review = reviews.get(position);

        ///устанавливаем полученные данные
        holder.textViewContent.setText(review.getContent());
        holder.textViewAuthor.setText(review.getAuthor());
    }

    @Override
    public int getItemCount()
    {
        return reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewContent;
        private TextView textViewAuthor;

        public ReviewViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);

        }
    }
}
