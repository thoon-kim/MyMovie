package org.thk.mymovie;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.*;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class MovieListFragment extends Fragment {
    final static private String DB_NAME = "movie.db";
    DBHelper dbHelper;

    MoviewPagerAdapter adapter;
    MovieItemFragment[] fragments;

    ViewPager pager;
    Button movieDetail;

    int movieCount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_list, container, false);

        pager = (ViewPager) root.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        dbHelper = new DBHelper(this.getContext(), DB_NAME, null, 1);
        movieCount = dbHelper.getCount(0);

//        Log.d("MLF", "movieCount : " + movieCount);
        adapter = new MoviewPagerAdapter(getFragmentManager());
        fragments = new MovieItemFragment[movieCount];
        for (int i = 0; i < movieCount; i++) {
            fragments[i] = new MovieItemFragment(i + 1, dbHelper.getMovieList().get(i));
            adapter.addItem(fragments[i]);
        }
        pager.setAdapter(adapter);

        movieDetail = root.findViewById(R.id.movieDetail);
        movieDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("MLF onclick", String.valueOf(pager.getCurrentItem()));
//                Log.d("MLF onclick id", String.valueOf(fragments[pager.getCurrentItem()].movie.getId()));

                viewMovieDetail(fragments[pager.getCurrentItem()].movie.getId());
                movieDetail.setVisibility(View.GONE);
//                Log.d("MLF", "onClick" + dbHelper.getMovieList().get(pager.getCurrentItem()).toString());
            }
        });

        return root;
    }

    public void viewMovieDetail(int id) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        switch (id) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                args.putInt("id", id);
                break;
        }

        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }

//    public class MovieThread extends Thread {
//        final static String TAG = "MLF / MovieThread";
//        Context mContext;
//        MovieRepo movieRepo;
//        Handler handler;
//
//        public MovieThread(Context mContext, Handler handler) {
//            this.mContext = mContext;
//            this.handler = handler;
//        }
//
//        public void getMovieList(int type) {
//            super.run();
//            Retrofit client = new Retrofit.Builder().baseUrl("http://boostcourse-appapi.connect.or.kr:10000/movie/")
//                    .addConverterFactory(GsonConverterFactory.create()).build();
//            MovieRepo.MovieListInterface service = client.create(MovieRepo.MovieListInterface.class);
//            Call<MovieRepo> call = service.getMovieList(type);
//
//            call.enqueue(new Callback<MovieRepo>() {
//                @Override
//                public void onResponse(Call<MovieRepo> call, Response<MovieRepo> response) {
//                    if (response.isSuccessful()) {
//                        movieRepo = response.body();
//                        Log.d(TAG, "response.raw : " + response.raw());
//                        dbHelper.deleteData();
//                        if (movieRepo.getCode() == 200) {
//                            for (int i = 0; i < 5; i++) {
//                                MovieRepo.Movie movie = movieRepo.getResults().get(i);
//                                dbHelper.setData(movie, 0);
//                            }
////                            되는 부분
////                            for (int i = 0; i < 5; i++) {
////                                fragments[i] = new MovieItemFragment(i+1,
////                                        movieRepo.getResults().get(i).getTitle(),
////                                        movieRepo.getResults().get(i).getReservation_rate(),
////                                        movieRepo.getResults().get(i).getGrade(),
////                                        movieRepo.getResults().get(i).getDate(),
////                                        movieRepo.getResults().get(i).getImage());
////                                adapter.addItem(fragments[i]);
////                                Log.d("MLF", String.valueOf(adapter.getCount()));
////                            }
////                            pager.setAdapter(adapter);
//                        } else {
//                            Log.e(TAG, "요청 실패 : " + movieRepo.getCode());
//                            Log.e(TAG, "메시지 : " + movieRepo.getMessage());
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<MovieRepo> call, Throwable t) {
//                    Log.e(TAG, "영화정보 불러오기 실패 : " + t.getMessage());
//                    Log.e(TAG, "요청 메시지 : " + call.request());
//                }
//            });
//        }
//    }

}
