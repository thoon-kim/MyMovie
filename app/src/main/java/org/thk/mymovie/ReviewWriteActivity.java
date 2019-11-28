package org.thk.mymovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.thk.mymovie.ReviewRepo.Review;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewWriteActivity extends AppCompatActivity {
    private final InternalListener iListener = new InternalListener();
//    public static final String TBNAME = "review";

//    SQLiteDatabase database;
    TextView textTitle, textComment;
    ImageView imageGrade;
    RatingBar ratingBar;
    TextView btnSubmit, btnCancel;

    private int id;
    private String title, grade;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        textTitle = findViewById(R.id.textTitle);
        textComment = findViewById(R.id.textTitle);
        imageGrade = findViewById(R.id.imageGrade);
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

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

                    float rating = ratingBar.getRating();
                    String comment = textComment.getText().toString();

                case R.id.btnCancel:
                    finish();
                    break;
            }
        }
    }


//
//    public void init(SQLiteDatabase database, String title) {
//        this.database = database;
//        this.title = title;
//    }
//
//    public void preInit() {
////        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        inflater.inflate(R.layout.activity_review_write, this, true);
//
//        ratingBar = findViewById(R.id.ratingBar);
//        review = findViewById(R.id.review);
//        submit = findViewById(R.id.submit);
//        cancel = findViewById(R.id.cancel);
//
//        submit.setOnClickListener(iListener);
//        cancel.setOnClickListener(iListener);
//    }
//
//    public void destroy() {
//        this.destroy();
//    }
//
//    protected class InternalListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.submit:
//                    long time = System.currentTimeMillis();
//                    SimpleDateFormat dayTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
//                    String str = dayTime.format(new Date(time));
//                    String rating = String.valueOf(ratingBar.getRating());
//                    String comment = String.valueOf(review.getText());
//
//                    submitReview(title, "mirpower1004", str, rating, comment);
//                    Toast.makeText(mContext, "한줄평이 등록되었습니다.", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(mContext, MainActivity.class);
//                    mContext.startActivity(intent);
//                    break;
//                case R.id.cancel:
//
//            }
//        }
//    }
//
//    public void submitReview(String title, String id, String date, String rating, String comment) {
//        database.execSQL("create table if not exists " + TBNAME + " ("
//                + "title text, "
//                + "id text, "
//                + "date time, "
//                + "rating integer, "
//                + "comment text)");
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("TITLE", title);
//        contentValues.put("ID", id);
//        contentValues.put("DATE", date);
//        contentValues.put("RATING", rating);
//        contentValues.put("COMMENT", comment);
//
//        database.insert(TBNAME, null, contentValues);
//    }

//    public boolean showReviewWrite(final boolean isShow) {
//        setTitle("한줄평 작성");
//        reviewWrite.setVisibility(View.GONE);
//        if (isShow) {
//            if (layout_reviewWrite != null) return false;
//            layout_reviewWrite = new ReviewWrite(container_review.getContext());
//            container_review.addView(layout_reviewWrite);
//            layout_reviewWrite.init(database, textTitle.getText().toString());
//        } else {
//            if (layout_reviewWrite == null) return false;
//            container_review.removeView(layout_reviewWrite);
////            layout_reviewWrite.destroy();
//            layout_reviewWrite = null;
//        }
//        return true;
//    }
}
