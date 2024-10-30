package com.example.film;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.film.database.DatabaseHelper;
import com.example.film.database.Movie;

public class MovieActivity extends AppCompatActivity {

    private ImageView filmImage;
    private TextView description, country, genre, director, actor, duration, release;
    private Button buyTicket;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        filmImage = findViewById(R.id.movie_image);
        description = findViewById(R.id.movie_description);
        country = findViewById(R.id.country);
        genre = findViewById(R.id.genre);
        director = findViewById(R.id.director);
        actor = findViewById(R.id.actor);
        duration = findViewById(R.id.duration);
        release = findViewById(R.id.release_date);
        buyTicket = findViewById(R.id.buy_ticket);
        int id = getIntent().getIntExtra("id", -1);
        if (id == -1) {
            Toast.makeText(getApplicationContext(), "PHIM KHÔNG HỢP LỆ", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        Movie movie = new DatabaseHelper(getApplicationContext()).getMovie(id);
        Bitmap bitmap = BitmapFactory.decodeFile(movie.getImage());
        filmImage.setImageBitmap(bitmap);
        description.setText(movie.getDescription());
        country.setText("\u2022 Quốc gia: " + movie.getCountry());
        genre.setText("\u2022 Thể loại: " + movie.getGenre());
        director.setText("\u2022 Đạo diễn: " + movie.getDirector());
        actor.setText("\u2022 Diễn viên: " + movie.getActor());
        duration.setText("\u2022 Thời lượng: " + movie.getDuration() + " phút");
        release.setText("\u2022 Ngày phát hành: " + movie.getReleaseDate() + "");
        buyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieActivity.this, SeatActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
}