package org.thk.mymovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class ReviewWrite extends LinearLayout {
    private final InternalListener iListener = new InternalListener();
    public static final String TBNAME = "review";

    Context mContext;
    SQLiteDatabase database;
    RatingBar ratingBar;
    TextView review, submit, cancel;
    String title;

    public ReviewWrite(Context context) {
        super(context);
        mContext = context;
        preInit();
    }

    public void init(SQLiteDatabase database, String title) {
        this.database = database;
        this.title = title;
    }

    public void preInit() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.review_write, this, true);

        ratingBar = findViewById(R.id.ratingBar);
        review = findViewById(R.id.review);
        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);

        submit.setOnClickListener(iListener);
        cancel.setOnClickListener(iListener);
    }

    public void destroy() {
        this.destroy();
    }

    protected class InternalListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.submit:
                    long time = System.currentTimeMillis();
                    SimpleDateFormat dayTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                    String str = dayTime.format(new Date(time));
                    String rating = String.valueOf(ratingBar.getRating());
                    String comment = String.valueOf(review.getText());

                    submitReview(title, "mirpower1004", str, rating, comment);
                    Toast.makeText(mContext, "한줄평이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                    break;
                case R.id.cancel:

            }
        }
    }

    public void submitReview(String title, String id, String date, String rating, String comment) {
        database.execSQL("create table if not exists " + TBNAME + " ("
                + "title text, "
                + "id text, "
                + "date time, "
                + "rating integer, "
                + "comment text)");

        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", title);
        contentValues.put("ID", id);
        contentValues.put("DATE", date);
        contentValues.put("RATING", rating);
        contentValues.put("COMMENT", comment);

        database.insert(TBNAME, null, contentValues);
    }
}
