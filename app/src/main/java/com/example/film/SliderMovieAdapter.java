package com.example.film;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.film.database.ItemClickListener;
import com.example.film.database.Movie;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SliderMovieAdapter extends SliderViewAdapter<SliderMovieAdapter.SliderAdapterVH> {

    private Context context;
    private ItemClickListener listener;
    private List<Movie> movies = new ArrayList<>();

    public SliderMovieAdapter(Context context, ItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void renewItems(List<Movie> Movies) {
        this.movies = Movies;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.movies.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Movie Movie) {
        this.movies.add(Movie);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        Movie Movie = movies.get(position);
        Bitmap bitmap = BitmapFactory.decodeFile(Movie.getImage());
//        Log.e("convert", "Bitmap: " + convert(bitmap));
        viewHolder.imageViewBackground.setImageBitmap(bitmap);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieClick(Movie);
            }
        });
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return movies.size();
    }

    public static class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}