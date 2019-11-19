package org.thk.mymovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MovieListActivity extends AppCompatActivity {
    ViewPager pager;
    Button movieInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("영화 목록");
        setContentView(R.layout.activity_movie_list);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        MoviewPagerAdapter adapter = new MoviewPagerAdapter(getSupportFragmentManager());

        MovieFragment fragment1 = new MovieFragment(R.drawable.image1, "1. ", "군        도", "예매율 61.6%", "15세 관람가", "D-1");
        adapter.addItem(fragment1);

        MovieFragment fragment2 = new MovieFragment(R.drawable.image2, "2. ", "공        조", "예매율 10.6%", "15세 관람가", "D-8");
        adapter.addItem(fragment2);

        MovieFragment fragment3 = new MovieFragment(R.drawable.image3, "3. ", "더        킹", "예매율 5.6%", "18세 관람가", "D-8");
        adapter.addItem(fragment3);

        MovieFragment fragment4 = new MovieFragment(R.drawable.image4, "4. ", "레지던트 이블", "예매율 0.6%", "18세 관람가", "D-8");
        adapter.addItem(fragment4);

        MovieFragment fragment5 = new MovieFragment(R.drawable.image5, "5. ", "럭        키", "예매율 0.2%", "12세 관람가", "D-15");
        adapter.addItem(fragment5);

        MovieFragment fragment6 = new MovieFragment(R.drawable.image6, "6. ", "아   수   라", "예매율 0.1%", "18세 관람가", "D-15");
        adapter.addItem(fragment6);

        pager.setAdapter(adapter);

        movieInfo = findViewById(R.id.movieInfo);

        movieInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
