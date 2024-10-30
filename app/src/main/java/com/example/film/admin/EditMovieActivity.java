package com.example.film.admin;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.film.R;
import com.example.film.database.DatabaseHelper;
import com.example.film.database.Movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class EditMovieActivity extends AppCompatActivity {

    private Button selectImage, save;
    private EditText name, description, country, genre, director, actor, duration, releaseDate;
    private ImageView movieImage;
    private String imagePath;
    private DatabaseHelper databaseHelper;
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri == null) {
                        return;
                    }
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        File f = new File(getExternalFilesDir("images"), System.currentTimeMillis() + "");
                        OutputStream outputStream = Files.newOutputStream(f.toPath());
                        byte[] buffer = new byte[1024];
                        int length;

                        while ((length = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }

                        outputStream.close();
                        inputStream.close();

                        imagePath = f.getAbsolutePath();
                        movieImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
                        movieImage.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
        selectImage = findViewById(R.id.selectImage);
        movieImage = findViewById(R.id.movieImage);
        save = findViewById(R.id.save);
        name = findViewById(R.id.movieName);
        description = findViewById(R.id.movieDescription);
        country = findViewById(R.id.movieCountry);
        genre = findViewById(R.id.movieGenre);
        director = findViewById(R.id.movieDirector);
        actor = findViewById(R.id.movieActor);
        duration = findViewById(R.id.movieDuration);
        releaseDate = findViewById(R.id.movieReleaseDate);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });
        long id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            initUpdate(id);
        } else {
            initAdd();
        }
    }

    private void initAdd() {
        save.setText("Add");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagePath == null) {
                    Toast.makeText(getApplicationContext(), "VUI LÒNG CHỌN ẢNH BÌA", Toast.LENGTH_LONG).show();
                    return;
                }

                Movie movie = new Movie(
                        name.getText().toString(),
                        imagePath,
                        description.getText().toString(),
                        country.getText().toString(),
                        genre.getText().toString(),
                        director.getText().toString(),
                        actor.getText().toString(),
                        Integer.parseInt(duration.getText().toString()),
                        releaseDate.getText().toString()
                );
                databaseHelper.insertMovie(movie);
                finish();
            }
        });
    }

    private void initUpdate(long id) {

    }
}