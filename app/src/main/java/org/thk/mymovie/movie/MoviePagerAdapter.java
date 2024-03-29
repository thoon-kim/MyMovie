package org.thk.mymovie.movie;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MoviePagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> items = new ArrayList<Fragment>();

    public MoviePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addItem(Fragment item) { items.add(item); }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
