package com.example.film;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
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

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ViewTicketActivity extends AppCompatActivity {

    private TextView film, date, time, seats, booker, total;
    private ImageView qrImage;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_ticket);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
        qrImage = findViewById(R.id.ticketQr);
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
        createQr(movie, name);
    }

    private void createQr(Movie movie, String name) {
        StringBuilder qr = new StringBuilder();
        qr.append(getIntent().getIntExtra("id", -1)).append("|");
        qr.append(movie.getName()).append("|");
        qr.append(getIntent().getStringExtra("date")).append("|");
        qr.append(getIntent().getStringExtra("seats")).append("|");
        qr.append(name).append("|");
        QRGEncoder qrgEncoder = new QRGEncoder(qr.toString(), null, QRGContents.Type.TEXT, 1000);
        qrgEncoder.setColorBlack(Color.RED);
        qrgEncoder.setColorWhite(Color.WHITE);
        try {
            // Getting QR-Code as Bitmap
            Bitmap bitmap = qrgEncoder.getBitmap();
            // Setting Bitmap to ImageView
            qrImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
