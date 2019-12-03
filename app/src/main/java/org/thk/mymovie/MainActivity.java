package org.thk.mymovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import org.thk.mymovie.movie.MovieRepo;
import org.thk.mymovie.review.ReviewRepo;
import org.thk.mymovie.utils.DBHelper;
import org.thk.mymovie.utils.NetworkStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class MainActivity extends AppCompatActivity {
    final static private String DB_NAME = "movie.db";

    private AppBarConfiguration mAppBarConfiguration;
    static DBHelper dbHelper;
    MovieThread movieThread;
    NetworkStatus networkStatus;
    // 리스너 객체 생성
    private OnBackPressedListener mBackListener;
    // 뒤로가기 버튼 입력시간이 담길 long 객체
    private long pressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkStatus = new NetworkStatus();

        movieThread = new MovieThread(this, new Handler());
        if (networkStatus.getConnectivityStatus(this) == 1 ||
                networkStatus.getConnectivityStatus(this) == 2 ) {
            movieThread.getData(1);
        }

        dbHelper = new DBHelper(this, DB_NAME, null, 1);
//        movieThread = new MovieThread(this, new Handler());
//        movieThread.getMovieList(1);
        int movieCount = dbHelper.getCount(0);
//        Log.d("MA", "movieCount : " + movieCount);


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

    public interface OnBackPressedListener {
        public void onBack();
    }

    // 리스너 설정 메소드
    public void setOnBackPressedListener(OnBackPressedListener listener) {
        mBackListener = listener;
    }

    // 뒤로가기 버튼을 눌렀을 때의 오버라이드 메소드
    @Override
    public void onBackPressed() {
        // 다른 Fragment 에서 리스너를 설정했을 때 처리됩니다.
        if(mBackListener != null) {
            mBackListener.onBack();
            Log.e("Other", "Listener is not null");
            // 뒤로가기 버튼을 연속적으로 두번 눌렀을 때 앱이 종료됩니다.
        } else {
            Log.e("Other", "Listener is null");
            if ( pressedTime == 0 ) {
                pressedTime = System.currentTimeMillis();
            }
            else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if ( seconds > 2000 ) {
                    pressedTime = 0 ;
                } else {
                    super.onBackPressed();
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        }
    }


    public static class MovieThread extends Thread {
        final static String TAG = "MA MovieThread";
        Context mContext;
        MovieRepo movieRepo;
        ReviewRepo reviewRepo;
        Handler handler;

        public MovieThread(Context mContext, Handler handler) {
            this.mContext = mContext;
            this.handler = handler;
        }

        public void getData(int type) {
            super.run();
            Retrofit client = new Retrofit.Builder().baseUrl("http://boostcourse-appapi.connect.or.kr:10000/movie/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MovieRepo.movieInterface service = client.create(MovieRepo.movieInterface.class);
            Call<MovieRepo> call = service.getMovieList(type);

            call.enqueue(new Callback<MovieRepo>() {
                @Override
                public void onResponse(Call<MovieRepo> call, Response<MovieRepo> response) {
                    if (response.isSuccessful()) {
                        movieRepo = response.body();
//                        Log.d(TAG, "response.raw : " + response.raw());

                        if (movieRepo.getCode() == 200) {
                            for (int i = 0; i < movieRepo.getResults().size(); i++) {
                                MovieRepo.Movie movie = movieRepo.getResults().get(i);
//                                Log.d("MA MT getMovieList", movie.toString());
                                dbHelper.setData(movie, 0);
                                getMovieDetail(movie.getId());
                            }
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

        public void getMovieDetail(int id) {
            super.run();
            Retrofit client = new Retrofit.Builder().baseUrl("http://boostcourse-appapi.connect.or.kr:10000/movie/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MovieRepo.movieInterface service = client.create(MovieRepo.movieInterface.class);
            Call<MovieRepo> call = service.getMovieDetail(id);

            call.enqueue(new Callback<MovieRepo>() {
                @Override
                public void onResponse(Call<MovieRepo> call, Response<MovieRepo> response) {
                    if(response.isSuccessful()) {
                        movieRepo = response.body();
                        Log.d(TAG, "response.raw : " + response.raw());

                        if(movieRepo.getCode() == 200) {
                            final MovieRepo.Movie movie = movieRepo.getResults().get(0);
//                            Log.d("MA getMovieDetail", movie.toString());
                            dbHelper.setData(movie, 1);
                        } else {
                            Log.e(TAG, "요청 실패 : " + movieRepo.getCode());
                            Log.e(TAG, "메시지 : " + movieRepo.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieRepo> call, Throwable t) {
                    Log.e(TAG, "영화 상세 정보 불러오기 실패 : " + t.getMessage());
                    Log.e(TAG, "요청 메시지 : " + call.request());
                }
            });
        }
    }
}