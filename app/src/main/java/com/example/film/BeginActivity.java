package com.example.film;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.film.database.Preference;

public class BeginActivity extends AppCompatActivity {

    private Button buttonGetStared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_begin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        buttonGetStared = findViewById(R.id.getStarted);
        if (!Preference.isFirstTimeOpen(getApplicationContext())) {
            buttonGetStared.setVisibility(View.GONE);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    moveToSignIn();
                }
            }, 1500);
        }
        buttonGetStared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preference.setOpened(getApplicationContext());
                moveToSignIn();
            }
        });
    }

    private void moveToSignIn() {
        startActivity(new Intent(BeginActivity.this, SignInActivity.class));
        finish();
    }
}
