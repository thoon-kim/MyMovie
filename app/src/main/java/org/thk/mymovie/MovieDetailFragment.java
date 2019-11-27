package org.thk.mymovie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailFragment extends Fragment {
    ImageView imageThumb, imageGrade;
    TextView textTitle, textSynopsis, textDirector, textActor;
    TextView textDate, textGenreDuration;
    TextView textLike, textDislike;
    TextView textReservation, textAudienceRating, textAudience;

    RatingBar ratingBar;
    ImageButton btnLike, btnDislike;
    TextView btnWrite, viewAll;
    ListView listView;
    Intent intent;

    MovieThread movieThread;
    private int id;
    private String grade;

    TextView btnBook;

    InternalListener iListener = new InternalListener();

    RecyclerView recyclerView;
    ReviewAdapter adapter;

//    public static final String TBNAME = "review";
//    SQLiteDatabase database;
//    private static final String CLIENT_ID = "drnF6o3Zx6VJajDJPHHA";
//    private static final String CLIENT_SECRET = "AETxsPbTsV";
//
//    String apiUrl = "https://openapi.naver.com/v1/search/movie.json?query=";
//    String Url = "https://www.naver.com/";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        id = getArguments().getInt("id");

        imageThumb = root.findViewById(R.id.imageThumb);
        imageGrade = root.findViewById(R.id.imageGrade);
        textTitle = root.findViewById(R.id.textTitle);
        textSynopsis = root.findViewById(R.id.textSynopsis);
        textDirector = root.findViewById(R.id.textDirector);
        textActor = root.findViewById(R.id.textActor);
        textDate = root.findViewById(R.id.textDate);
        textGenreDuration = root.findViewById(R.id.textGenreDuration);
        textLike = root.findViewById(R.id.textLike);
        textDislike = root.findViewById(R.id.textDislike);
        textReservation = root.findViewById(R.id.textReservation);
        textAudienceRating = root.findViewById(R.id.textAudienceRating);
        textAudience = root.findViewById(R.id.textAudience);
        ratingBar = root.findViewById(R.id.ratingBar);

        movieThread = new MovieThread(this.getContext(), new Handler());
        movieThread.getMovieDetail(id);

        btnLike = root.findViewById(R.id.btnLike);
        btnDislike = root.findViewById(R.id.btnDislike);
//        btnWrite = root.findViewById(R.id.btnWrite);
//        viewAll = root.findViewById(R.id.viewAll);
//        btnBook = root.findViewById(R.id.book);
//
        recyclerView = root.findViewById(R.id.reviewPreveal);
        movieThread.getComments(id, "2");

//
        btnLike.setOnClickListener(iListener);
        btnDislike.setOnClickListener(iListener);
//        btnWrite.setOnClickListener(iListener);
//        viewAll.setOnClickListener(iListener);
//        btnBook.setOnClickListener(iListener);
//
//        getReviews();

        return root;
    }

    public void setNum(ImageButton btn, boolean state, TextView text, int num) {
        btn.setSelected(state);
        text.setText(String.valueOf(Integer.parseInt((String) text.getText()) + num));
    }

    protected class InternalListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnLike:
                    if (btnDislike.isSelected() == true) {
                        setNum(btnLike, true, textLike, 1);
                        setNum(btnDislike, false, textDislike, -1);
                    } else if(btnLike.isSelected() == true) {
                        setNum(btnLike, false, textLike, -1);
                    } else {
                        setNum(btnLike, true, textLike, 1);
                    }
                    break;
                case R.id.btnDislike:
                    if (btnLike.isSelected() == true) {
                        setNum(btnDislike, true, textDislike, 1);
                        setNum(btnLike, false, textLike, -1);
                    } else if(btnDislike.isSelected() == true) {
                        setNum(btnDislike, false, textDislike, -1);
                    } else {
                        setNum(btnDislike, true, textDislike, 1);
                    }
                    break;
                case R.id.btnWrite:
//                    intent = new Intent(getApplicationContext(), ReviewListActivity.class);
//                    intent.putExtra("title", textTitle.getText());
//                    intent.putExtra("rating", "15");
//                    intent.putExtra("type", "write");
//                    startActivityForResult(intent, 101);
                    break;
                case R.id.viewAll:
                    intent = new Intent(getContext(), ReviewListActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("title", textTitle.getText().toString());
                    intent.putExtra("grade", grade);
                    startActivity(intent);
                    break;
                case R.id.book:
                    break;
            }

        }
    }

    public class MovieThread extends Thread {
        final static String TAG = "MDF / MovieThread";
        Context mContext;
        MovieRepo movieRepo;
        Handler handler;

        public MovieThread(Context mContext, Handler handler) {
            this.mContext = mContext;
            this.handler = handler;
        }

        public void getMovieDetail(int id) {
            super.run();
            Retrofit client = new Retrofit.Builder().baseUrl("http://boostcourse-appapi.connect.or.kr:10000/movie/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MovieRepo.MovieListInterface service = client.create(MovieRepo.MovieListInterface.class);
            Call<MovieRepo> call = service.getMovieDetail(id);

            call.enqueue(new Callback<MovieRepo>() {
                @Override
                public void onResponse(Call<MovieRepo> call, Response<MovieRepo> response) {
                    if(response.isSuccessful()) {
                        movieRepo = response.body();
                        Log.d(TAG, "response.raw : " + response.raw());

                        if(movieRepo.getCode() == 200) {
                            final MovieRepo.Movie movie = movieRepo.getResults().get(0);

                            Glide.with(MovieDetailFragment.this)
                                    .load(movie.getThumb())
                                    .into(imageThumb);
                            textTitle.setText(movie.getTitle());
                            textSynopsis.setText(movie.getSynopsis());
                            textDirector.setText(movie.getDirector());
                            textActor.setText(movie.getActor());

                            // 날짜 형식 변경
                            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
                            try {
                                Date to = df.parse(movie.getDate());
                                String result = df.format(to);
                                textDate.setText(result + " 개봉");
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            grade = String.valueOf(movie.getGrade());
                            switch (grade) {
                                case "12":
                                    imageGrade.setImageResource(R.drawable.ic_12);
                                    break;
                                case "15":
                                    imageGrade.setImageResource(R.drawable.ic_15);
                                    break;
                                case "19":
                                    imageGrade.setImageResource(R.drawable.ic_19);
                                    break;
                                case "all":
                                    imageGrade.setImageResource(R.drawable.ic_all);
                                    break;
                            }

                            textGenreDuration.setText(movie.getGenre() + " / " + movie.getDuration() + "분");
                            textLike.setText(String.valueOf(movie.getLike()));
                            textDislike.setText(String.valueOf(movie.getDislike()));
                            textReservation.setText(movie.getReservation_grade() + "위 " + movie.getReservation_rate() + "%");
                            ratingBar.setRating(movie.getAudience_rating() / 2);
                            textAudienceRating.setText(String.valueOf(movie.getAudience_rating()));
                            textAudience.setText(String.format("%,3d", movie.getAudience())  + "명");
                        } else {
                            Log.e(TAG, "요청 실패 : " + movieRepo.getCode());
                            Log.e(TAG, "메시지 : " + movieRepo.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieRepo> call, Throwable t) {
                    Log.e(TAG, "영화정보 불러오기 실패 : " + t.getMessage());
                    Log.e(TAG, "요청 메시지 : " + call.request());
                }
            });
        }

        public void getComments(int id, final String limit) {
            super.run();
            Retrofit client = new Retrofit.Builder().baseUrl("http://boostcourse-appapi.connect.or.kr:10000/movie/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MovieRepo.MovieListInterface service = client.create(MovieRepo.MovieListInterface.class);
            Call<MovieRepo> call = service.getComments(id, limit);

            call.enqueue(new Callback<MovieRepo>() {
                @Override
                public void onResponse(Call<MovieRepo> call, Response<MovieRepo> response) {
                    if(response.isSuccessful()) {
                        movieRepo = response.body();
                        Log.d(TAG, "response.raw : " + response.raw());

                        if(movieRepo.getCode() == 200) {
                            adapter = new ReviewAdapter();

                            for(int i = 0; i < Integer.parseInt(limit); i++){
                                String writer = movieRepo.getResults().get(i).getWriter();
                                String time = movieRepo.getResults().get(i).getTime();
                                float rating = movieRepo.getResults().get(i).getRating();
                                String contents = movieRepo.getResults().get(i).getContents();
                                int recommend = movieRepo.getResults().get(i).getRecommend();
                                adapter.addItem(new Review(writer, time, rating, contents, recommend));
                            }
                            recyclerView.setAdapter(adapter);

                        } else {
                            Log.e(TAG, "요청 실패 : " + movieRepo.getCode());
                            Log.e(TAG, "메시지 : " + movieRepo.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieRepo> call, Throwable t) {
                    Log.e(TAG, "영화정보 불러오기 실패 : " + t.getMessage());
                    Log.e(TAG, "요청 메시지 : " + call.request());
                }
            });
        }
    }
}
