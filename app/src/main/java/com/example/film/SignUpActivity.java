package com.example.film;

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

import com.example.film.database.User;
import com.example.film.database.DatabaseHelper;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirm_password);
        buttonSignUp = findViewById(R.id.signUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirm = editTextConfirmPassword.getText().toString();
        if (!password.equals(confirm)) {
            Toast.makeText(getApplicationContext(), "MẬT KHẨU KHÔNG TRÙNG KHỚP!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
            User checkUser = databaseHelper.getUser(username);
            if (checkUser != null) {
                Toast.makeText(getApplicationContext(), "TÊN ĐĂNG NHẬP ĐÃ TỒN TẠI!", Toast.LENGTH_LONG).show();
            }
            User user = new User(username, password);
            databaseHelper.insertUser(user);
            Toast.makeText(getApplicationContext(), "ĐĂNG KÝ THÀNH CÔNG!", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "CÓ LỖi XẢY RA!", Toast.LENGTH_LONG).show();
        }
    }
}