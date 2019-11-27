package org.thk.mymovie;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ReviewView extends LinearLayout {
    private final InternalListener iListener = new InternalListener();
    public static final String TBNAME = "review";

    SQLiteDatabase database;
    ReviewAdapter adapter;
    RecyclerView recyclerView;
    String title;
    Cursor cursor;
    TextView textView;
    TextView reviewWrite;

    public ReviewView(Context context) {
        super(context);
        preInit();
    }

    public void init(SQLiteDatabase database, String title, Cursor cursor) {
        this.database = database;
        this.title = title;
        this.cursor = cursor;
    }

    public void preInit() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.review_view, this, true);

        recyclerView = findViewById(R.id.reviewRecyclerView);
    }

    public void destroy() {
        this.destroy();
    }

    protected class InternalListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
            }
        }
    }

//    public void getReviews(){
//        adapter = new ReviewAdapter();
//
//        int recordCount = cursor.getCount();
//
//        for(int i = 0; i < recordCount; i++){
//            cursor.moveToNext();
//            String title = cursor.getString(0);
//            String id = cursor.getString(1);
//            String date = cursor.getString(2);
//            String rating = cursor.getString(3);
//            String comment = cursor.getString(4);
//            adapter.addItem(new Review(title, id, date, rating, comment));
//        }
//
//        cursor.close();
//
//        recyclerView.setAdapter(adapter);
//    }
}
