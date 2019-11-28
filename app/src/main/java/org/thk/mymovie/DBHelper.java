package org.thk.mymovie;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import org.thk.mymovie.MovieRepo.Movie;
import org.thk.mymovie.ReviewRepo.Review;


public class DBHelper extends SQLiteOpenHelper {
    private final static String TB_MOVIE = "movie_list";
    private final static String TB_DETAIL = "movie_detail";
    private final static String TB_REVIEW = "review_list";

    private final static int MOVIE = 0;
    private final static int DETAIL = 1;
    private final static int REVIEW = 2;

    String sql;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        if (database != null) {
            sql = "create table if not exists " + TB_MOVIE
                    + "(_id integer primary key, "
                    + "_title text, "
                    + "_reservation_rate float, "
                    + "_grade integer, "
                    + "_date text, "
                    + "_image text);";
            database.execSQL(sql);

            sql = "create table if not exists " + TB_DETAIL
                    + "(_id integer primary key, "
                    + "_title text, "
                    + "_reservation_rate float, "
                    + "_grade integer, "
                    + "_date text, "
                    + "_thumb text, "
                    + "_reservation_grade integer, "
                    + "_audience_rating float, "
                    + "_genre text, "
                    + "_duration integer, "
                    + "_audience integer, "
                    + "_synopsis text, "
                    + "_director text, "
                    + "_actor text, "
                    + "_like integer, "
                    + "_dislike integer);";
            database.execSQL(sql);

            sql = "create table " + TB_REVIEW
                    + "(_review_id integer primary key autoincrement, "
                    + "_movie_id integer, "
                    + "_writer text, "
                    + "_time date, "
                    + "_rating float, "
                    + "_contents text, "
                    + "_recommend integer);";
            database.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }

    public void setData(Movie movie, int type) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        switch (type) {
            case MOVIE:
                sql = "select * from " + TB_MOVIE
                        + " where _id = "
                        + movie.getId() + ";";
                cursor = db.rawQuery(sql, null);

                if (cursor.getCount() > 0) {
                    Log.d("DBHelper MOVIE", movie.getId() + " is existing in db");
                } else {
                    sql = "insert into " + TB_MOVIE
                            + "(_id, _title, _reservation_rate, _grade, _date, _image)"
                            + " values"
                            + "(" + movie.getId() + ", "
                            + "\'" + movie.getTitle() + "\', "
                            + movie.getReservation_rate() + ", "
                            + movie.getGrade() + ", "
                            + "\'" + movie.getDate() + "\', "
                            + "\'" + movie.getImage() + "\');";
                    db.execSQL(sql);
                    Log.d("DBHelper", movie.toString() + " insertMovieList Success");
                }
                break;
            case DETAIL:
                sql = "select * from " + TB_DETAIL
                        + " where _id = "
                        + movie.getId() + ";";
                cursor = db.rawQuery(sql, null);

                if (cursor.getCount() > 0) {
                    Log.d("DBHelper DETAIL", movie.getId() + " is existing in db");
                } else {
                    sql = "insert into " + TB_DETAIL
                            + "(_id, _title, _reservation_rate, _grade, _date, _thumb, _reservation_grade, _audience_rating, "
                            + "_genre, _duration, _audience, _synopsis, _director, _actor, _like, _dislike)"
                            + " values"
                            + "(" + movie.getId() + ", "
                            + "\'" + movie.getTitle() + "\', "
                            + movie.getReservation_rate() + ", "
                            + movie.getGrade() + ", "
                            + "\'" + movie.getDate() + "\', "
                            + "\'" + movie.getThumb() + "\', "
                            + movie.getReservation_grade() + ", "
                            + movie.getAudience_rating() + ", "
                            + "\'" + movie.getGenre() + "\', "
                            + movie.getDuration() + ", "
                            + movie.getAudience() + ", "
                            + "\'" + movie.getSynopsis().replace("'", "`") + "\', "
                            + "\'" + movie.getDirector() + "\', "
                            + "\'" + movie.getActor() + "\', "
                            + movie.getLike() + ", "
                            + movie.getDislike() + ");";
                    db.execSQL(sql);
                    Log.d("DBHelper", "insertMovieDetail Success");
                }
                break;
        }
        db.close();
    }

    public void setDataReview(Review review) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor;
        sql = "select * from " + TB_REVIEW
                + " where _review_id = "
                + review.getReview_id() + ";";
        cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            Log.d("DBHelper REVIEW", review.getReview_id() + " is existing in db");
        } else {
            sql = "insert into " + TB_REVIEW
                    + "(_review_id, _movie_id, _writer, _time, _rating, _contents, _recommend) "
                    + " values"
                    + "(" + review.getReview_id() + ", "
                    + review.getMovie_id() + ", "
                    + "\'" + review.getWriter() + "\', "
                    + "\'" + review.getTime() + "\', "
                    + review.getRating() + ", "
                    + "\'" + review.getContents() + "\', "
                    + review.getRecommend() + ");";
            db.execSQL(sql);
            Log.d("DBHelper", "insertReviews Success");
        }
        db.close();
    }

    public void deleteData() {
        Log.d("DBHelper", "deleteMovieList");
        SQLiteDatabase db = getWritableDatabase();

        sql = "delete from " + TB_MOVIE + ";";
        db.execSQL(sql);

        sql = "delete from " + TB_DETAIL + ";";
        db.execSQL(sql);

        sql = "delete from " + TB_REVIEW + ";";
        db.execSQL(sql);

        db.close();
    }


    public ArrayList<Movie> getMovieList() {
        Log.d("DBHelper", "getMovieList");
        ArrayList<Movie> movies = new ArrayList<Movie>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TB_MOVIE + ";", null);

        while (cursor.moveToNext()) {
            movies.add(new Movie(cursor.getInt(0), cursor.getString(1), cursor.getFloat(2),
                    cursor.getInt(3), cursor.getString(4), cursor.getString(5)));
        }
        db.close();
        return movies;
    }

    public Movie getMovieDetail(int id) {
        Log.d("DBHelper", "getMovieDetail");
        Movie movie = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TB_DETAIL
                + " where _id = " + id + ";", null);

        while (cursor.moveToNext()) {
            movie = new Movie(cursor.getInt(0), cursor.getString(1),
                    cursor.getFloat(2), cursor.getInt(3), cursor.getString(4),
                    cursor.getString(5), cursor.getInt(6), cursor.getFloat(7),
                    cursor.getString(8), cursor.getInt(9), cursor.getInt(10),
                    cursor.getString(11), cursor.getString(12), cursor.getString(13),
                    cursor.getInt(14), cursor.getInt(15));
        }
        db.close();
        return movie;
    }

    public ArrayList<Review> getReview(int id, String limit) {
        Log.d("DBHelper", "getReviews");
        ArrayList<Review> reviews = new ArrayList<Review>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;

        if (limit.equals("all")) {
            cursor = db.rawQuery("select * from " + TB_REVIEW
                    + " where _movie_id = " + id + ";", null);
        } else {
            cursor = db.rawQuery("select * from " + TB_REVIEW
                    + " where _movie_id=" + id + " limit " + Integer.parseInt(limit) + ";", null);
        }

        while (cursor.moveToNext()) {
            reviews.add(new Review(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                    cursor.getString(3), cursor.getFloat(4), cursor.getString(5), cursor.getInt(6)));
        }
        db.close();
        return reviews;
    }

    public int getCount(int type) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        int count = 0;
        switch (type) {
            case MOVIE:
                cursor = db.rawQuery("select * from " + TB_MOVIE + ";", null);
                count = cursor.getCount();
                break;
            case DETAIL:
                cursor = db.rawQuery("select * from " + TB_DETAIL + ";", null);
                count = cursor.getCount();
                break;
            case REVIEW:
                cursor = db.rawQuery("select * from " + TB_REVIEW + ";", null);
                count = cursor.getCount();
                break;
        }
        db.close();
        return count;
    }
}
