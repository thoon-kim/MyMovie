package org.thk.mymovie.review;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.thk.mymovie.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewWriteActivity extends AppCompatActivity {
    private final InternalListener iListener = new InternalListener();

    TextView textTitle, textComment;
    ImageView imageGrade;
    RatingBar ratingBar;
    TextView btnSubmit, btnCancel;

    private int id;
    private String title, grade;

    ReviewThread reviewThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        textTitle = findViewById(R.id.textTitle);
        textComment = findViewById(R.id.textComment);
        imageGrade = findViewById(R.id.imageGrade);
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        reviewThread = new ReviewThread(this, new Handler());

        Bundle bundle = getIntent().getExtras();

        id = bundle.getInt("id");
        title = bundle.getString("title");
        grade = bundle.getString("grade");

        textTitle.setText(title);
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

        btnSubmit.setOnClickListener(iListener);
        btnCancel.setOnClickListener(iListener);
    }

    public class InternalListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnSubmit:
                    String writer = "hoon";
                    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
                    Calendar calendar = Calendar.getInstance();
                    String time = format.format(calendar.getTime());
                    float rating = ratingBar.getRating() * 2;
                    String comment = textComment.getText().toString();
                    reviewThread.setReview(id, writer, time, rating, comment);
                case R.id.btnCancel:
                    finish();
                    break;
            }
        }
    }

    public class ReviewThread extends Thread {
        final static String TAG = "RLA / ReviewThread";
        Context mContext;
        ReviewWriteRepo reviewWriteRepo;
        Handler handler;

        public ReviewThread(Context mContext, Handler handler) {
            this.mContext = mContext;
            this.handler = handler;
        }

        public void setReview(int id, String writer, String time, float rating, String contents) {
            super.run();
            Retrofit client = new Retrofit.Builder().baseUrl("http://boostcourse-appapi.connect.or.kr:10000/movie/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            ReviewWriteRepo.reviewInterface service = client.create(ReviewWriteRepo.reviewInterface.class);
            Call<ReviewWriteRepo> call = service.createReview(id, writer, time, rating, contents);

            call.enqueue(new Callback<ReviewWriteRepo>() {
                @Override
                public void onResponse(Call<ReviewWriteRepo> call, Response<ReviewWriteRepo> response) {
                    if (response.isSuccessful()) {
                        reviewWriteRepo = response.body();
                        Log.d(TAG, "response.raw : " + response.raw());

                        if (reviewWriteRepo.getCode() == 200) {
                            Log.e(TAG, "리뷰 입력 성공 : " + reviewWriteRepo.getCode());
                        } else {
                            Log.e(TAG, "요청 실패 : " + reviewWriteRepo.getCode());
                            Log.e(TAG, "메시지 : " + reviewWriteRepo.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReviewWriteRepo> call, Throwable t) {
                    Log.e(TAG, "리뷰 입력 요청 실패 : " + t.getMessage());
                    Log.e(TAG, "요청 메시지 : " + call.request());
                }
            });
        }
    }
}
