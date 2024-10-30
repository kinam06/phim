package com.example.film;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film.database.Movie;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SeatAdapterVH> {

    private Context context;
    private MovieClickListener listener;
    private List<Movie> list = new ArrayList<>();

    public SearchAdapter(Context context, MovieClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void renewItems(List<Movie> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeatAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, null);
        return new SeatAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatAdapterVH holder, int position) {
        int pos = holder.getAdapterPosition();
        Movie movie = list.get(pos);
        holder.image.setImageBitmap(BitmapFactory.decodeFile(movie.getImage()));
        holder.name.setText(movie.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SeatAdapterVH extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        View itemView;

        public SeatAdapterVH(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            this.itemView = itemView;
        }
    }

}