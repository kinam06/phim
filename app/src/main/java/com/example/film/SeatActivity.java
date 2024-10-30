package com.example.film;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.film.database.DatabaseHelper;
import com.example.film.database.Preference;
import com.example.film.database.Reservation;
import com.example.film.utils.DateUtilities;
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SeatActivity extends AppCompatActivity {

    private TimeAdapter timeAdapter;
    private SeatAdapter seatAdapter;
    private RecyclerView timeRecyclerView, seatRecyclerView;
    private TextView total;
    private Button buyTicket;
    private int id;
    private Date date;
    private String time;

    private int totalValue;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seat);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        id = getIntent().getIntExtra("id", -1);
        if (id == -1) {
            finish();
        }

//        HorizontalPicker picker = findViewById(R.id.datePicker);
        DayScrollDatePicker picker = findViewById(R.id.datePicker);
        total = findViewById(R.id.total);
        buyTicket = findViewById(R.id.buy_ticket);
        timeRecyclerView = findViewById(R.id.timeRecyclerView);
        seatRecyclerView = findViewById(R.id.seatRecyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(6, LinearLayout.VERTICAL);
        seatRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        timeAdapter = new TimeAdapter(this, new ItemTimeClickListener() {
            @Override
            public void onTimeCLick(String time) {
                SeatActivity.this.time = time;
                seatAdapter.renewItems(initSeats(DateUtilities.dateToString(date), time));
                updateTotal(0);
            }
        });
        seatAdapter = new SeatAdapter(this, new SeatClickListener() {
            @Override
            public void onSeatClick() {
                int tickets = seatAdapter.getSelectedCount();
                updateTotal(tickets * 20);
            }
        });
        timeRecyclerView.setAdapter(timeAdapter);
        seatRecyclerView.setAdapter(seatAdapter);
        buyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalValue == 0) {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn ghế!", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(SeatActivity.this, PaymentActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("date", DateUtilities.dateToString(date));
                intent.putExtra("time", time);
                StringBuilder seat = new StringBuilder();
                List<Integer> selectedSeat = seatAdapter.getSelectedSeats();
                for (int i = 0; i < selectedSeat.size(); i++) {
                    if (i == selectedSeat.size() - 1) {
                        seat.append(selectedSeat.get(i) + 1);
                    } else {
                        seat.append(selectedSeat.get(i) + 1).append(", ");
                    }
                }
                intent.putExtra("seats", seat.toString());
                intent.putExtra("total", totalValue);
                startActivityForResult(intent, 1221);
            }
        });

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        picker.setStartDate(day, month, year);
        picker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@Nullable Date date) {
                timeRecyclerView.setVisibility(View.VISIBLE);
                SeatActivity.this.date = date;
                seatAdapter.renewItems(Collections.emptyList());
                timeAdapter.clearSelected(true);
                updateTotal(0);
            }
        });
    }

    private List<SeatSelection> initSeats(String date, String time) {
        List<SeatSelection> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new SeatSelection(
                    i, false, false
            ));
        }
        Reservation reservation = databaseHelper.getReservation(date, time, id);
        if (reservation != null) {
            for (Integer i : reservation.getSeat()) {
                list.get(i).setBooked(true);
            }
        }
        return list;
    }

    private void updateTotal(int value) {
        totalValue = value;
        total.setText("Total: $" + totalValue);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1221 && resultCode == RESULT_OK) {
            Reservation reservation = new Reservation();
            reservation.setSeat(seatAdapter.getSelectedSeats());
            reservation.setMovieId(id);
            reservation.setUserId(Preference.getLoginUser(getApplicationContext()));
            reservation.setDate(DateUtilities.dateToString(date));
            reservation.setTime(time);
            databaseHelper.insertOrUpdateReservation(reservation);
            Toast.makeText(getApplicationContext(), "THÀNH CÔNG", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SeatActivity.this, ViewTicketActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("date", DateUtilities.dateToString(date));
            intent.putExtra("time", time);
            StringBuilder seat = new StringBuilder();
            List<Integer> selectedSeat = seatAdapter.getSelectedSeats();
            for (int i = 0; i < selectedSeat.size(); i++) {
                if (i == selectedSeat.size() - 1) {
                    seat.append(selectedSeat.get(i) + 1);
                } else {
                    seat.append(selectedSeat.get(i) + 1).append(", ");
                }
            }
            intent.putExtra("seats", seat.toString());
            intent.putExtra("total", totalValue);
            startActivity(intent);
            finish();
        }
    }
}