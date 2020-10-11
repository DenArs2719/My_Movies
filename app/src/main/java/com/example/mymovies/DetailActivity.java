///activity who will show us all popular films
package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity
{
    private ImageView imageViewBigPoster;
    private TextView textViewLabelTitle;
    private TextView textViewTitle;
    private TextView textViewLabelOriginalTitle;
    private TextView textViewLabelRating;
    private TextView textViewRating;
    private TextView textViewLabelReleaseDate;
    private TextView textViewReleaseDate;
    private TextView textViewLabelDescription;
    private TextView textViewOverview;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewLabelOriginalTitle = findViewById(R.id.textViewLabelOriginalTitle);
        textViewLabelTitle = findViewById(R.id.textViewLabelTitle);
        textViewLabelRating = findViewById(R.id.textViewLabelRating);
        textViewRating = findViewById(R.id.textViewRating);
        textViewLabelReleaseDate = findViewById(R.id.textViewLabelReleaseDate);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewLabelDescription = findViewById(R.id.textViewLabelDescription);
        textViewOverview = findViewById(R.id.textViewOverview);




    }
}