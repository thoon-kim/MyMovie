package org.thk.mymovie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewListActivity extends AppCompatActivity {
    TextView textTitle;
    ImageView imgGrade;
    RecyclerView reviewList;
    TextView btnWrite;

    MovieThread movieThread;
    ReviewAdapter adapter;

    private int id;
    private String title, grade;

//    private ReviewWrite layout_reviewWrite;
//    private ReviewView layout_reviewView;

    public static final String DBNAME = "review_movie.db";
    public static final String TBNAME = "review";
    SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        textTitle = findViewById(R.id.textTitle);
        imgGrade = findViewById(R.id.imageGrade);
        reviewList = findViewById(R.id.reviewList);
        btnWrite = findViewById(R.id.btnWrite);

//        database = openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);

        Bundle bundle = getIntent().getExtras();

        id = bundle.getInt("id");
        title = bundle.getString("title");
        grade = bundle.getString("grade");

        textTitle.setText(title);
        switch (grade) {
            case "12":
                imgGrade.setImageResource(R.drawable.ic_12);
                break;
            case "15":
                imgGrade.setImageResource(R.drawable.ic_15);
                break;
            case "19":
                imgGrade.setImageResource(R.drawable.ic_19);
                break;
            case "all":
                imgGrade.setImageResource(R.drawable.ic_all);
                break;
        }
        adapter = new ReviewAdapter();
        movieThread = new MovieThread(this, new Handler());
        movieThread.getComments(id, "all");

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewListActivity.this, ReviewWriteActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("grade", grade);
                startActivity(intent);
            }
        });
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
                            Log.d(TAG, "reviewItems size : " + String.valueOf(movieRepo.getResults().size()));

                            for(int i = 0; i < movieRepo.getResults().size(); i++){
                                String writer = movieRepo.getResults().get(i).getWriter();
                                String time = movieRepo.getResults().get(i).getTime();
                                float rating = movieRepo.getResults().get(i).getRating();
                                String contents = movieRepo.getResults().get(i).getContents();
                                int recommend = movieRepo.getResults().get(i).getRecommend();
//                                Log.d(TAG, "Review + " + i + " : " + String.valueOf(movieRepo.getResults().get(i).toString()));

                                Log.d(TAG, "Review " + i + " : " + "writer : " + writer + ", time : " + time +
                                        ", rating : " + rating + ", contents : " + contents + ", recommend : " + recommend);

                                adapter.addItem(new Review(writer, time, rating, contents, recommend));
                            }
                            reviewList.setAdapter(adapter);

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
