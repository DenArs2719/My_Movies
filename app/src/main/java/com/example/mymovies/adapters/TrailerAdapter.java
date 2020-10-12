package com.example.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;
import com.example.mymovies.data.Trailer;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private ArrayList<Trailer> trailers;

    ///добавляем слушателя
    private OnTrailerClickListener onTrailerClickListener;

    public interface OnTrailerClickListener
    {
        void onTrailerClick(String url);
    }

    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener)
    {
        this.onTrailerClickListener = onTrailerClickListener;
    }

    public TrailerAdapter()
    {
        this.trailers = trailers;
    }

    public void setTrailers(ArrayList<Trailer> trailers)
    {
        this.trailers = trailers;

        ///говорим адаптеру,что данные изменились
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,parent,false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position)
    {
        ///получаем наш trailer
        Trailer trailer = trailers.get(position);

        holder.textViewNameOfVideo.setText(trailer.getName());



    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageViewPlay;
        private TextView textViewNameOfVideo;
        public TrailerViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewNameOfVideo = itemView.findViewById(R.id.textViewNameOfVideo);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(onTrailerClickListener != null)
                    {
                      onTrailerClickListener.onTrailerClick(trailers.get(getAdapterPosition()).getVideo() );
                    }
                }
            });
        }
    }
}
