package com.example.film.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "FILM.DB";
    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);
        db.execSQL(Movie.CREATE_TABLE);
        initMovieData(db);
    }

    private void initMovieData(SQLiteDatabase db) {
        try {
            String pathMatBiec = createFileFromAssets("matbiec.png");
            Movie matBiec = new Movie(
                    "MẮT BIẾC",
                    pathMatBiec,
                    "Mắt Biếc được chuyển thể từ cuốn tiểu thuyết cùng tên của nhà văn Nguyễn Nhật Ánh, kể dưới góc nhìn của Ngạn - một chàng trai sinh ra và lớn lên trong ngôi làng Đo Đo nghèo khó, đem lòng yêu một cô gái xinh đẹp với đôi mắt biếc của Đo Đo suốt nửa đời người.",
                    "Việt Nam",
                    "Chính kịch, lãng mạng",
                    "Victor Vũ",
                    "Trần Nghĩa, Trúc Anh, Thảo Tâm, Trần Phong, Khánh Vân,...",
                    114,
                    0L
            );
            String pathRom = createFileFromAssets("rom.png");
            Movie rom = new Movie(
                    "MẮT BIẾC",
                    pathRom,
                    "Mắt Biếc được chuyển thể từ cuốn tiểu thuyết cùng tên của nhà văn Nguyễn Nhật Ánh, kể dưới góc nhìn của Ngạn - một chàng trai sinh ra và lớn lên trong ngôi làng Đo Đo nghèo khó, đem lòng yêu một cô gái xinh đẹp với đôi mắt biếc của Đo Đo suốt nửa đời người.",
                    "Việt Nam",
                    "Chính kịch, lãng mạng",
                    "Victor Vũ",
                    "Trần Nghĩa, Trúc Anh, Thảo Tâm, Trần Phong, Khánh Vân,...",
                    114,
                    0L
            );
            insertMovie(db, matBiec);
            insertMovie(db, rom);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long insertMovie(SQLiteDatabase db, Movie movie) {
        ContentValues values = new ContentValues();
        values.put(Movie.NAME, movie.getName());
        values.put(Movie.IMAGE, movie.getImage());
        values.put(Movie.DESCRIPTION, movie.getDescription());
        values.put(Movie.COUNTRY, movie.getCountry());
        values.put(Movie.GENRE, movie.getGenre());
        values.put(Movie.DIRECTOR, movie.getDirector());
        values.put(Movie.ACTOR, movie.getActor());
        values.put(Movie.DURATION, movie.getDuration());
        values.put(Movie.RELEASE, movie.getReleaseDate());

        // insert row
        return db.insert(Movie.TABLE_NAME, null, values);
    }

    private String createFileFromAssets(String fileName) {

        try {
            InputStream inputStream = context.getAssets().open(fileName);
            File f = new File(context.getExternalFilesDir("images"), fileName);
            OutputStream outputStream = Files.newOutputStream(f.toPath());
            byte[] buffer = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return f.getAbsolutePath();
        } catch (IOException e) {
            //Logging exception
        }

        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long insertMovie(User user) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(User.USERNAME, user.getUsername());
        values.put(User.PASSWORD, user.getPassword());

        // insert row
        long id = db.insert(User.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public User getUser(String username) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(User.TABLE_NAME,
                new String[]{User.USER_ID, User.USERNAME, User.PASSWORD, User.CREATE_TIME},
                User.USERNAME + "=?",
                new String[]{String.valueOf(username)}, null, null, null, null);

        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();

        // prepare note object
        @SuppressLint("Range")
        User user = new User(
                cursor.getInt(cursor.getColumnIndex(User.USER_ID)),
                cursor.getString(cursor.getColumnIndex(User.USERNAME)),
                cursor.getString(cursor.getColumnIndex(User.PASSWORD)),
                cursor.getLong(cursor.getColumnIndex(User.CREATE_TIME))
        );

        // close the db connection
        cursor.close();

        return user;
    }

    public long insertMovie(Movie movie) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Movie.NAME, movie.getName());
        values.put(Movie.IMAGE, movie.getImage());
        values.put(Movie.DESCRIPTION, movie.getDescription());
        values.put(Movie.COUNTRY, movie.getCountry());
        values.put(Movie.GENRE, movie.getGenre());
        values.put(Movie.DIRECTOR, movie.getDirector());
        values.put(Movie.ACTOR, movie.getActor());
        values.put(Movie.DURATION, movie.getDuration());
        values.put(Movie.RELEASE, movie.getReleaseDate());

        // insert row
        long id = db.insert(Movie.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Movie getMovie(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Movie.TABLE_NAME,
                new String[]{
                        Movie.MOVIE_ID,
                        Movie.NAME,
                        Movie.IMAGE,
                        Movie.DESCRIPTION,
                        Movie.COUNTRY,
                        Movie.GENRE,
                        Movie.DIRECTOR,
                        Movie.ACTOR,
                        Movie.DURATION,
                        Movie.RELEASE,
                },
                Movie.MOVIE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();

        // prepare note object
        @SuppressLint("Range")
        Movie user = new Movie(
                cursor.getInt(cursor.getColumnIndex(Movie.MOVIE_ID)),
                cursor.getString(cursor.getColumnIndex(Movie.NAME)),
                cursor.getString(cursor.getColumnIndex(Movie.IMAGE)),
                cursor.getString(cursor.getColumnIndex(Movie.DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(Movie.COUNTRY)),
                cursor.getString(cursor.getColumnIndex(Movie.GENRE)),
                cursor.getString(cursor.getColumnIndex(Movie.DIRECTOR)),
                cursor.getString(cursor.getColumnIndex(Movie.ACTOR)),
                cursor.getInt(cursor.getColumnIndex(Movie.DURATION)),
                cursor.getLong(cursor.getColumnIndex(Movie.RELEASE))
        );

        // close the db connection
        cursor.close();

        return user;
    }

    public List<Movie> getRandomMovie() {
        List<Movie> movies = new ArrayList<>();
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Movie.TABLE_NAME,
                new String[]{
                        Movie.MOVIE_ID,
                        Movie.NAME,
                        Movie.IMAGE,
                        Movie.DESCRIPTION,
                        Movie.COUNTRY,
                        Movie.GENRE,
                        Movie.DIRECTOR,
                        Movie.ACTOR,
                        Movie.DURATION,
                        Movie.RELEASE,
                },
                null,
                null, null, null, "RANDOM()", String.valueOf(5));

        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")
                Movie movie = new Movie(
                        cursor.getInt(cursor.getColumnIndex(Movie.MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndex(Movie.NAME)),
                        cursor.getString(cursor.getColumnIndex(Movie.IMAGE)),
                        cursor.getString(cursor.getColumnIndex(Movie.DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(Movie.COUNTRY)),
                        cursor.getString(cursor.getColumnIndex(Movie.GENRE)),
                        cursor.getString(cursor.getColumnIndex(Movie.DIRECTOR)),
                        cursor.getString(cursor.getColumnIndex(Movie.ACTOR)),
                        cursor.getInt(cursor.getColumnIndex(Movie.DURATION)),
                        cursor.getLong(cursor.getColumnIndex(Movie.RELEASE))
                );
                movies.add(movie);
            } while (cursor.moveToNext());
        }

        // close the db connection
        cursor.close();

        return movies;
    }

    public List<Movie> getTopMovie() {
        List<Movie> movies = new ArrayList<>();
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Movie.TABLE_NAME,
                new String[]{
                        Movie.MOVIE_ID,
                        Movie.NAME,
                        Movie.IMAGE,
                        Movie.DESCRIPTION,
                        Movie.COUNTRY,
                        Movie.GENRE,
                        Movie.DIRECTOR,
                        Movie.ACTOR,
                        Movie.DURATION,
                        Movie.RELEASE,
                },
                null,
                null, null, null, null, String.valueOf(5));

        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")
                Movie movie = new Movie(
                        cursor.getInt(cursor.getColumnIndex(Movie.MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndex(Movie.NAME)),
                        cursor.getString(cursor.getColumnIndex(Movie.IMAGE)),
                        cursor.getString(cursor.getColumnIndex(Movie.DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(Movie.COUNTRY)),
                        cursor.getString(cursor.getColumnIndex(Movie.GENRE)),
                        cursor.getString(cursor.getColumnIndex(Movie.DIRECTOR)),
                        cursor.getString(cursor.getColumnIndex(Movie.ACTOR)),
                        cursor.getInt(cursor.getColumnIndex(Movie.DURATION)),
                        cursor.getLong(cursor.getColumnIndex(Movie.RELEASE))
                );
                movies.add(movie);
            } while (cursor.moveToNext());
        }

        // close the db connection
        cursor.close();

        return movies;
    }
}
