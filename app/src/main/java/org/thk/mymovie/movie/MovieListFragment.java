package org.thk.mymovie.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import org.thk.mymovie.utils.DBHelper;
import org.thk.mymovie.MainActivity;
import org.thk.mymovie.R;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class MovieListFragment extends Fragment {
    final static private String DB_NAME = "movie.db";
    DBHelper dbHelper;

    MoviePagerAdapter adapter;
    MovieItemFragment[] fragments;

    ViewPager pager;
    Button movieDetail;

    int movieCount;

    Toolbar actionBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_list, container, false);

        actionBar = ((MainActivity)getActivity()).findViewById(R.id.toolbar);
        actionBar.setTitle("영화 목록");

        pager = (ViewPager) root.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        dbHelper = new DBHelper(this.getContext(), DB_NAME, null, 1);
        movieCount = dbHelper.getCount(0);

//        Log.d("MLF", "movieCount : " + movieCount);
        adapter = new MoviePagerAdapter(getFragmentManager());
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
}
