package org.thk.mymovie;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MoviewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> items = new ArrayList<Fragment>();

    public MoviewPagerAdapter(FragmentManager fm) {
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
