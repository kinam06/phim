package com.example.film.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.film.utils.DateUtilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private final Gson gson = new Gson();

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);
        db.execSQL(Movie.CREATE_TABLE);
        db.execSQL(Reservation.CREATE_TABLE);
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
                    DateUtilities.dateToString(DateUtilities.createDate(20, 12, 2019))
            );
            String pathRom = createFileFromAssets("rom.png");
            Movie rom = new Movie(
                    "RÒM",
                    pathRom,
                    "Bộ phim lấy bối cảnh một khu chung cư cũ kỹ của người lao động nghèo Sài Gòn, xoay quanh câu chuyện số đề, một loại hình cá cược phi pháp dựa trên kết quả xổ số kiến thiết của nhà nước. Người chơi sẽ cố gắng dự đoán 2 con số cuối giải đặc biệt kết quả xổ số mỗi ngày vào buổi chiều muộn. Ròm và đám trẻ bụi đời kiếm sống bằng nghề bán vé dò xổ số kiêm luôn nghề “cò” ghi số đề. Chúng sống nhờ tình thương của những người thắng đề khi đoán đúng, rồi bị mắng mỏ thậm chí còn bị đánh đập nếu kết quả dự đoán sai.",
                    "Việt Nam",
                    "Tâm lí xã hội, tội phạm",
                    "Trần Thanh Huy",
                    "Trần Anh Khoa, Nguyễn Phan Anh, Wowy, Cát Phượng,....",
                    79,
                    DateUtilities.dateToString(DateUtilities.createDate(20, 9, 2020))
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
        values.put(Movie.NAME_ENG, movie.getNameEng());
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

    public long insertUser(User user) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(User.USERNAME, user.getUsername());
        values.put(User.PASSWORD, user.getPassword());
        values.put(User.CREATE_TIME, user.getTime());

        // insert row
        long id = db.insert(User.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public void deleteUser(long id) {
        if (id < 0) {
            return;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(User.TABLE_NAME, User.USER_ID + "=?", new String[]{String.valueOf(id)});
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

        @SuppressLint("Range")
        User user = new User(
                cursor.getInt(cursor.getColumnIndex(User.USER_ID)),
                cursor.getString(cursor.getColumnIndex(User.USERNAME)),
                cursor.getString(cursor.getColumnIndex(User.PASSWORD)),
                cursor.getString(cursor.getColumnIndex(User.CREATE_TIME))
        );

        // close the db connection
        cursor.close();

        return user;
    }

    public List<User> getAllUser() {
        // get readable database as we are not inserting anything
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(User.TABLE_NAME,
                new String[]{User.USER_ID, User.USERNAME, User.PASSWORD, User.CREATE_TIME},
                null, null, null, null, null, null);

        if (cursor == null || cursor.getCount() == 0) {
            return list;
        }

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")
                User user = new User(
                        cursor.getInt(cursor.getColumnIndex(User.USER_ID)),
                        cursor.getString(cursor.getColumnIndex(User.USERNAME)),
                        cursor.getString(cursor.getColumnIndex(User.PASSWORD)),
                        cursor.getString(cursor.getColumnIndex(User.CREATE_TIME))
                );
                list.add(user);
            } while (cursor.moveToNext());
        }
        // close the db connection
        cursor.close();

        return list;
    }

    public long insertMovie(Movie movie) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Movie.NAME, movie.getName());
        values.put(Movie.NAME_ENG, movie.getNameEng());
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
        if (id == -1) {
            return null;
        }
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Movie.TABLE_NAME,
                new String[]{
                        Movie.MOVIE_ID,
                        Movie.NAME,
                        Movie.NAME_ENG,
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
                cursor.getString(cursor.getColumnIndex(Movie.RELEASE))
        );

        // close the db connection
        cursor.close();

        return movie;
    }

    public void deleteMovie(long id) {
        if (id < 0) {
            return;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(Movie.TABLE_NAME, Movie.MOVIE_ID + "=?", new String[]{String.valueOf(id)});
    }

    public List<Movie> getMovieByName(String name) {
        List<Movie> movies = new ArrayList<>();
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Movie.TABLE_NAME,
                new String[]{
                        Movie.MOVIE_ID,
                        Movie.NAME,
                        Movie.NAME_ENG,
                        Movie.IMAGE,
                        Movie.DESCRIPTION,
                        Movie.COUNTRY,
                        Movie.GENRE,
                        Movie.DIRECTOR,
                        Movie.ACTOR,
                        Movie.DURATION,
                        Movie.RELEASE,
                },
                Movie.NAME_ENG + " like ?",
                new String[]{"%" + name + "%"}, null, null, null, String.valueOf(5));

        if (cursor == null || cursor.getCount() == 0) {
            return movies;
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
                        cursor.getString(cursor.getColumnIndex(Movie.RELEASE))
                );
                movies.add(movie);
            } while (cursor.moveToNext());
        }

        // close the db connection
        cursor.close();

        return movies;
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Movie.TABLE_NAME,
                new String[]{
                        Movie.MOVIE_ID,
                        Movie.NAME,
                        Movie.NAME_ENG,
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
                null, null, null, null, null);

        if (cursor == null || cursor.getCount() == 0) {
            return movies;
        }

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
                        cursor.getString(cursor.getColumnIndex(Movie.RELEASE))
                );
                movies.add(movie);
            } while (cursor.moveToNext());
        }

        // close the db connection
        cursor.close();

        return movies;
    }

    public List<Movie> getRandomMovies() {
        List<Movie> movies = new ArrayList<>();
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Movie.TABLE_NAME,
                new String[]{
                        Movie.MOVIE_ID,
                        Movie.NAME,
                        Movie.NAME_ENG,
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
            return movies;
        }

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
                        cursor.getString(cursor.getColumnIndex(Movie.RELEASE))
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
                        Movie.NAME_ENG,
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
            return movies;
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
                        cursor.getString(cursor.getColumnIndex(Movie.RELEASE))
                );
                movies.add(movie);
            } while (cursor.moveToNext());
        }

        // close the db connection
        cursor.close();

        return movies;
    }

    public long insertOrUpdateReservation(Reservation reservation) {
        Reservation dbReservation = getReservation(reservation.getDate(), reservation.getTime(), reservation.getMovieId());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (dbReservation == null) {

            values.put(Reservation.MOVIE_ID, reservation.getMovieId());
            values.put(Reservation.USER_ID, reservation.getUserId());
            values.put(Reservation.DATE, reservation.getDate());
            values.put(Reservation.TIME, reservation.getTime());
            values.put(Reservation.SEAT, gson.toJson(reservation.getSeat()));

            // insert row
            long id = db.insert(Reservation.TABLE_NAME, null, values);

            // close db connection
            db.close();

            // return newly inserted row id
            return id;
        } else {
            values.put(Reservation.RESERVATION_ID, dbReservation.getId());
            values.put(Reservation.MOVIE_ID, dbReservation.getMovieId());
            values.put(Reservation.USER_ID, dbReservation.getUserId());
            values.put(Reservation.DATE, dbReservation.getDate());
            values.put(Reservation.TIME, dbReservation.getTime());
            dbReservation.getSeat().addAll(reservation.getSeat());
            values.put(Reservation.SEAT, gson.toJson(dbReservation.getSeat()));
            long id = db.update(
                    Reservation.TABLE_NAME,
                    values,
                    Reservation.RESERVATION_ID + "=?",
                    new String[]{String.valueOf(dbReservation.getId())}
            );
            db.close();
            return id;
        }

    }

    @SuppressLint("Range")
    public Reservation getReservation(String date, String time, int movieId) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Reservation.TABLE_NAME,
                new String[]{
                        Reservation.RESERVATION_ID,
                        Reservation.MOVIE_ID,
                        Reservation.USER_ID,
                        Reservation.DATE,
                        Reservation.TIME,
                        Reservation.SEAT
                },
                Reservation.DATE + "=?" + " AND " + Reservation.TIME + "=?" + " AND " + Reservation.MOVIE_ID + "=?",
                new String[]{date, time, String.valueOf(movieId)}, null, null, null, null);

        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();


        String seat = cursor.getString(cursor.getColumnIndex(Reservation.SEAT));
        List<Integer> seats = gson.fromJson(seat, new TypeToken<List<Integer>>() {
        }.getType());

        Reservation reservation = new Reservation(
                cursor.getInt(cursor.getColumnIndex(Reservation.RESERVATION_ID)),
                cursor.getInt(cursor.getColumnIndex(Reservation.MOVIE_ID)),
                cursor.getInt(cursor.getColumnIndex(Reservation.USER_ID)),
                cursor.getString(cursor.getColumnIndex(Reservation.DATE)),
                cursor.getString(cursor.getColumnIndex(Reservation.TIME)),
                seats
        );

        // close the db connection
        cursor.close();

        return reservation;
    }
}
