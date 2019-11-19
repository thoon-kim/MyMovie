package org.thk.mymovie;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {
    TextView textTitle;
    ImageView imgRating;
    String rating;
    FrameLayout container_review;
    RelativeLayout reviewWrite;
    TextView btnWrite;

    private ReviewWrite layout_reviewWrite;
    private ReviewView layout_reviewView;

    public static final String DBNAME = "review_movie.db";
    public static final String TBNAME = "review";
    SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        textTitle = findViewById(R.id.textTitle);
        imgRating = findViewById(R.id.imgRating);

        container_review = (FrameLayout)findViewById(R.id.container_review);

        database = openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);

        Bundle bundle = getIntent().getExtras();

        textTitle.setText(bundle.getString("title"));
        rating = bundle.getString("rating");

        switch (rating) {
            case "12":
                imgRating.setImageResource(R.drawable.ic_12);
                break;
            case "15":
                imgRating.setImageResource(R.drawable.ic_15);
                break;
            case "19":
                imgRating.setImageResource(R.drawable.ic_19);
                break;
            case "all":
                imgRating.setImageResource(R.drawable.ic_all);
                break;
        }

        reviewWrite = findViewById(R.id.reviewWrite);
        btnWrite = findViewById(R.id.btnWrite);

        if (bundle.getString("type").equals("write")) {
            showReviewWrite(true);
        } else if (bundle.getString("type").equals("view")) {
            showReviewView(true);
        }

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReviewView(false);
                showReviewWrite(true);
            }
        });
    }

    public boolean showReviewWrite(final boolean isShow) {
        setTitle("한줄평 작성");
        reviewWrite.setVisibility(View.GONE);
        if (isShow) {
            if (layout_reviewWrite != null) return false;
            layout_reviewWrite = new ReviewWrite(container_review.getContext());
            container_review.addView(layout_reviewWrite);
            layout_reviewWrite.init(database, textTitle.getText().toString());
        } else {
            if (layout_reviewWrite == null) return false;
            container_review.removeView(layout_reviewWrite);
//            layout_reviewWrite.destroy();
            layout_reviewWrite = null;
        }
        return true;
    }

    public boolean showReviewView(final boolean isShow) {
        reviewWrite.setVisibility(View.VISIBLE);
        if (isShow) {
            if (layout_reviewView != null) return false;
            layout_reviewView = new ReviewView(container_review.getContext());
            container_review.addView(layout_reviewView);
            String sql = "select title, id, date, rating, comment from " + TBNAME + " where title = \'" + textTitle.getText().toString() + "\'";
            Cursor cursor = database.rawQuery(sql, null);
            layout_reviewView.init(database, textTitle.getText().toString(), cursor);
            layout_reviewView.getReviews();
        } else {
            if (layout_reviewView == null) return false;
            container_review.removeView(layout_reviewView);
//            layout_reviewView.destroy();
            layout_reviewView = null;
        }
        return true;
    }
}
