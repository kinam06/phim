package com.example.film.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film.R;
import com.example.film.database.DatabaseHelper;
import com.example.film.database.Movie;
import com.example.film.database.User;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.SeatAdapterVH> {

    private Context context;
    private List<Movie> list = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<Movie> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeatAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, null);
        return new SeatAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatAdapterVH holder, @SuppressLint("RecyclerView") int position) {
        int pos = holder.getAdapterPosition();
        Movie movie = list.get(pos);
        holder.name.setText(movie.getName());
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(movie.getImage()));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.deleteMovie(movie.getId());
                list.remove(movie);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SeatAdapterVH extends RecyclerView.ViewHolder {

        TextView name, createDate;
        ImageView imageView, delete;
        View itemView;

        public SeatAdapterVH(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);
            delete = itemView.findViewById(R.id.delete);
            this.itemView = itemView;
        }
    }

}