package com.example.film;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;

public class TicketActivity extends AppCompatActivity {

    private TimeAdapter timeAdapter;
    private RecyclerView timeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        HorizontalPicker picker = findViewById(R.id.datePicker);
        timeRecyclerView = findViewById(R.id.timeRecyclerView);
        timeAdapter = new TimeAdapter(this, new ItemTimeClickListener() {
            @Override
            public void onTimeCLick(String time) {

            }
        });
        timeRecyclerView.setAdapter(timeAdapter);

        // initialize it and attach a listener
        picker.setListener(datePickerListener)
                .setDateSelectedColor(getColor(R.color.red))
                .init();
        picker.setDate(new DateTime());
    }

    private final DatePickerListener datePickerListener = new DatePickerListener() {
        @Override
        public void onDateSelected(DateTime dateSelected) {

        }
    };
}