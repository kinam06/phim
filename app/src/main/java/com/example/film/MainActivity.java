package com.example.film;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.film.database.DatabaseHelper;
import com.example.film.database.ItemClickListener;
import com.example.film.database.Movie;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText search = findViewById(R.id.search);
        SliderView sliderViewRandom = findViewById(R.id.imageSliderRandomMovies);
        SliderView sliderViewTop = findViewById(R.id.imageSliderTopMovies);
        SliderView sliderViewUpcoming = findViewById(R.id.imageSliderUpcoming);

        SliderMovieAdapter adapterRandom = new SliderMovieAdapter(this, itemClickListener);
        SliderMovieAdapter adapterTop = new SliderMovieAdapter(this, itemClickListener);
        SliderMovieAdapter adapterUpcoming = new SliderMovieAdapter(this, itemClickListener);
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        adapterRandom.renewItems(databaseHelper.getRandomMovies());
        adapterTop.renewItems(databaseHelper.getTopMovie());
        adapterUpcoming.renewItems(databaseHelper.getRandomMovies());

        sliderViewRandom.setSliderAdapter(adapterRandom);
        sliderViewTop.setSliderAdapter(adapterTop);
        sliderViewUpcoming.setSliderAdapter(adapterUpcoming);
        setupSliderView(sliderViewRandom);
        setupSliderView(sliderViewTop);
        setupSliderView(sliderViewUpcoming);


    }

    private void setupSliderView(SliderView sliderView) {
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }

    private final ItemClickListener itemClickListener = new ItemClickListener() {
        @Override
        public void onMovieClick(Movie movie) {

        }
    };
}
