package org.thk.mymovie.review;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.thk.mymovie.utils.DBHelper;
import org.thk.mymovie.R;
import org.thk.mymovie.review.ReviewRepo.Review;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {
    final static private String DB_NAME = "movie.db";

    TextView textTitle;
    ImageView imgGrade;
    RecyclerView reviewList;
    TextView btnWrite;

    ReviewAdapter adapter;
    DBHelper dbHelper;

    private int id;
    private String title, grade;

//    private ReviewWrite layout_reviewWrite;
//    private ReviewView layout_reviewView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        textTitle = findViewById(R.id.textTitle);
        imgGrade = findViewById(R.id.imageGrade);
        reviewList = findViewById(R.id.reviewList);
        btnWrite = findViewById(R.id.btnWrite);

        Bundle bundle = getIntent().getExtras();

        id = bundle.getInt("id");
        title = bundle.getString("title");
        grade = bundle.getString("grade");
        Log.d("Taehoon RLA", "ID : " + id);

        // DB에서 데이터를 불러옴
        reviewList.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DBHelper(this, DB_NAME, null, 1);
        getReviews(dbHelper.getReview(id, "all"));


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

    public void getReviews(ArrayList<Review> reviews) {
        adapter = new ReviewAdapter();
        adapter.setItems(reviews);
        reviewList.setAdapter(adapter);
    }
}
