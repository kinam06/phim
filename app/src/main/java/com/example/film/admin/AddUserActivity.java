package com.example.film.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.film.R;
import com.example.film.database.DatabaseHelper;
import com.example.film.database.User;

public class AddUserActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        addUser = findViewById(R.id.add);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    private void addUser() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "VUI LÒNG NHẬP ĐỦ THÔNG TIN", Toast.LENGTH_LONG).show();
        }
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
            User checkUser = databaseHelper.getUser(username);
            if (checkUser != null) {
                Toast.makeText(getApplicationContext(), "TÊN ĐĂNG NHẬP ĐÃ TỒN TẠI!", Toast.LENGTH_LONG).show();
                return;
            }
            User user = new User(username, password);
            databaseHelper.insertUser(user);
            Toast.makeText(getApplicationContext(), "THÊM THÀNH CÔNG!", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "CÓ LỖi XẢY RA!", Toast.LENGTH_LONG).show();
        }
    }
}
