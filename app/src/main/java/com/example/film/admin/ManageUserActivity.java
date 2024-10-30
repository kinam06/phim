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

public class ManageUserActivity extends AppCompatActivity {

    private RecyclerView userRecyclerView;
    private UserAdapter userAdapter;
    private DatabaseHelper databaseHelper;
    private Button addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
        userAdapter = new UserAdapter(this);
        userRecyclerView = findViewById(R.id.userRecyclerView);
        userRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        userRecyclerView.setAdapter(userAdapter);
        userAdapter.renewItems(databaseHelper.getAllUser());
        addUser = findViewById(R.id.addUser);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageUserActivity.this, AddUserActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userAdapter.renewItems(databaseHelper.getAllUser());
    }
}
