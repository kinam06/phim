package com.example.film;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film.database.DatabaseHelper;
import com.example.film.database.Movie;

import java.util.Collections;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText search;
    private RecyclerView searchRecyclerView;
    private SearchAdapter adapter;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
        search = findViewById(R.id.search);
        search.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        searchRecyclerView = findViewById(R.id.searchRecyclerView);
        adapter = new SearchAdapter(this, new MovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = new Intent(SearchActivity.this, MovieActivity.class);
                intent.putExtra("id", movie.getId());
                startActivity(intent);
            }
        });
        searchRecyclerView.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchName = s.toString();
                if (searchName.isEmpty()) {
                    adapter.renewItems(Collections.emptyList());
                    return;
                }
                List<Movie> movies = databaseHelper.getMovieByName(searchName);
                Log.e("find", "movies: " + movies.size());
                adapter.renewItems(movies);
            }
        });

    }
}