package com.example.film;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.film.database.DatabaseHelper;
import com.example.film.database.Movie;
import com.example.film.database.Preference;
import com.example.film.database.User;

public class PaymentActivity extends AppCompatActivity {

    private TextView film, date, time, seats, booker, total;
    private Button confirm;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
        confirm = findViewById(R.id.buy_ticket);
        film = findViewById(R.id.film);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        seats = findViewById(R.id.seats);
        booker = findViewById(R.id.booker);
        total = findViewById(R.id.total);
        Movie movie = databaseHelper.getMovie(getIntent().getIntExtra("id", -1));
        if (movie == null) {
            finish();
            return;
        }
        film.setText(movie.getName());
        date.setText(getIntent().getStringExtra("date"));
        time.setText(getIntent().getStringExtra("time"));
        seats.setText(getIntent().getStringExtra("seats"));
        String name = "";
        User user = Preference.getUser(getApplicationContext());
        if (user != null) {
            name = user.getUsername();
        }
        booker.setText(name);
        total.setText("$" + getIntent().getIntExtra("total", 0));
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
