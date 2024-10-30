package com.example.film.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film.R;
import com.example.film.database.DatabaseHelper;

public class ManageMovieActivity extends AppCompatActivity {

    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;
    private DatabaseHelper databaseHelper;
    private Button addMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
        movieRecyclerView = findViewById(R.id.movieRecyclerView);
        movieRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        movieAdapter = new MovieAdapter(this);
        movieAdapter.renewItems(databaseHelper.getAllMovies());
        movieRecyclerView.setAdapter(movieAdapter);
        addMovie = findViewById(R.id.addMovie);
        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageMovieActivity.this, EditMovieActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        movieAdapter.renewItems(databaseHelper.getAllMovies());
    }
}
