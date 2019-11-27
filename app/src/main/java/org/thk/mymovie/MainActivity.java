package org.thk.mymovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_movie_list, R.id.nav_movie_api, R.id.nav_movie_book, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    TextView textTitle, textStory, textDirector, textMain;
//    TextView textNumLike, textNumHate;
//    ImageView imgRating;
//    ImageButton btnLike, btnHate;
//    TextView btnWrite, viewAll;
//    ListView listView;
//    Intent intent;
//
//    TextView btnBook;
//
//    InternalListener iListener = new InternalListener();
//
//    RecyclerView recyclerView;
//    ReviewAdapter adapter;
//
//    public static final String DBNAME = "review_movie.db";
//    public static final String TBNAME = "review";
//    SQLiteDatabase database;
//
////    private static final String CLIENT_ID = "drnF6o3Zx6VJajDJPHHA";
////    private static final String CLIENT_SECRET = "AETxsPbTsV";
////
////    String apiUrl = "https://openapi.naver.com/v1/search/movie.json?query=";
////    String Url = "https://www.naver.com/";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        btnLike = (ImageButton) findViewById(R.id.btnLike);
//        btnHate = (ImageButton) findViewById(R.id.btnHate);
//        btnWrite = (TextView) findViewById(R.id.btnWrite);
//        viewAll = (TextView) findViewById(R.id.viewAll);
//        listView = (ListView) findViewById(R.id.reviewList);
//        btnBook = (TextView) findViewById(R.id.book);
//
//        textNumLike = findViewById(R.id.numLike);
//        textNumHate = findViewById(R.id.numHate);
//
//        recyclerView = findViewById(R.id.reviewPreveal);
//
//        btnLike.setOnClickListener(iListener);
//        btnHate.setOnClickListener(iListener);
//        btnWrite.setOnClickListener(iListener);
//        viewAll.setOnClickListener(iListener);
//        btnBook.setOnClickListener(iListener);
//
//        setMovieInfo();
//    }
//
//    public void setMovieInfo() {
//        textTitle = findViewById(R.id.textTitle);
//        imgRating = findViewById(R.id.imgRating);
//        textStory = findViewById(R.id.textStory);
//        textDirector = findViewById(R.id.textDirector);
//        textMain = findViewById(R.id.textMain);
//
//        textStory.setText(R.string.story);
//        textDirector.setText(R.string.director);
//        textMain.setText(R.string.main);
//
//        getReviews();
//    }
//
//    public void setNum(ImageButton btn, boolean state, TextView text, int num) {
//        btn.setSelected(state);
//        text.setText(String.valueOf(Integer.parseInt((String) text.getText()) + num));
//    }
//
//    protected class InternalListener implements View.OnClickListener {
//
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.btnLike:
//                    if (btnHate.isSelected() == true) {
//                        setNum(btnLike, true, textNumLike, 1);
//                        setNum(btnHate, false, textNumHate, -1);
//                    } else {
//                        if (btnLike.isSelected() == false) {
//                            setNum(btnLike, true, textNumLike, 1);
//                        } else {
//                            setNum(btnLike, false, textNumLike, -1);
//                        }
//                    }
//                    break;
//                case R.id.btnHate:
//                    if (btnLike.isSelected() == true) {
//                        setNum(btnHate, true, textNumHate, 1);
//                        setNum(btnLike, false, textNumLike, -1);
//                    } else {
//                        if (btnHate.isSelected() == false) {
//                            setNum(btnHate, true, textNumHate, 1);
//                        } else {
//                            setNum(btnHate, false, textNumHate, -1);
//                        }
//                    }
//                    break;
//                case R.id.btnWrite:
//                    intent = new Intent(getApplicationContext(), ReviewListActivity.class);
//                    intent.putExtra("title", textTitle.getText());
//                    intent.putExtra("rating", "15");
//                    intent.putExtra("type", "write");
//                    startActivityForResult(intent, 101);
//                    break;
//                case R.id.viewAll:
//                    intent = new Intent(getApplicationContext(), ReviewListActivity.class);
//                    intent.putExtra("title", textTitle.getText());
//                    intent.putExtra("rating", "15");
//                    intent.putExtra("type", "view");
//                    startActivity(intent);
//                    break;
//                case R.id.book:
//                    break;
//            }
//
//        }
//    }
//
//    private void getReviews() {
//        database = openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
//        String sql = "select title, id, date, rating, comment from " + TBNAME + " where title = \'" + textTitle.getText().toString() + "\'";
//        Cursor cursor = database.rawQuery(sql, null);
//        adapter = new ReviewAdapter();
//
//        for(int i = 0; i < 2; i++){
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