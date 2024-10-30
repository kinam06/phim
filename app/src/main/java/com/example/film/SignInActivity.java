package com.example.film;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.film.admin.AdminActivity;
import com.example.film.database.DatabaseHelper;
import com.example.film.database.Preference;
import com.example.film.database.User;

public class SignInActivity extends AppCompatActivity {

    private Button signUp, signIn;
    private EditText editTextUsername, editTextPassword;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);
        rememberMe = findViewById(R.id.remember);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        User user = Preference.getUser(getApplicationContext());
        if (user != null) {
            editTextUsername.setText(user.getUsername());
            editTextPassword.setText(user.getPassword());
            rememberMe.setChecked(true);
        }
    }

    private void signIn() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "TÊN ĐĂNG NHẬP VÀ MẬT KHẨU KHÔNG ĐƯỢC ĐỂ TRỐNG", Toast.LENGTH_LONG).show();
        }
        if (username.equals("admin") && password.equals("admin")) {
            startActivity(new Intent(this, AdminActivity.class));
            return;
        }
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
            User user = databaseHelper.getUser(username);
            if (user == null) {
                Toast.makeText(getApplicationContext(), "NGƯỜI DÙNG KHÔNG TỒN TẠI!", Toast.LENGTH_LONG).show();
                return;
            }
            if (!user.getPassword().equalsIgnoreCase(password)) {
                Toast.makeText(getApplicationContext(), "SAI MẬT KHẨU!", Toast.LENGTH_LONG).show();
                return;
            }
            if (rememberMe.isChecked()) {
                Preference.saveUser(getApplicationContext(), username, password);
            } else {
                Preference.clearUser(getApplicationContext());
            }
            Preference.setLoginUser(getApplicationContext(), user.getId());
            startActivity(
                    new Intent(SignInActivity.this, MainActivity.class)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}