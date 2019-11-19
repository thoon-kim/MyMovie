package org.thk.mymovie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieFragment extends Fragment {
    ImageView imageMovie;
    TextView index;
    TextView title;
    TextView bookRate;
    TextView rating;
    TextView openDate;

    int _drawable;
    String _index;
    String _title;
    String _bookRate;
    String _rating;
    String _openDate;

    public MovieFragment(int _drawable, String _index, String _title, String _bookRate, String _rating, String _openDate) {
        this._drawable = _drawable;
        this._index = _index;
        this._title = _title;
        this._bookRate = _bookRate;
        this._rating = _rating;
        this._openDate = _openDate;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie, container, false);

        imageMovie = rootView.findViewById(R.id.imageMovie);
        index = rootView.findViewById(R.id.index);
        title = rootView.findViewById(R.id.title);
        bookRate = rootView.findViewById(R.id.bookRate);
        rating = rootView.findViewById(R.id.rating);
        openDate = rootView.findViewById(R.id.openDate);

        setInfo();

        return rootView;
    }

    private void setInfo() {
        imageMovie.setImageResource(_drawable);
        index.setText(_index);
        title.setText(_title);
        bookRate.setText(_bookRate);
        rating.setText(_rating);
        openDate.setText(_openDate);
    }
}
