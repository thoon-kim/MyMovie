package org.thk.mymovie.movie;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.thk.mymovie.R;

public class MovieItemFragment extends Fragment {
    ImageView imageThumb;
    TextView textIndex;
    TextView textTitle;
    TextView textReservationRate;
    TextView textGrade;
    TextView textDate;

    MovieRepo.Movie movie;

    int index;
    String title;
    float reservation_rate;
    int grade;
    String date;
    String thumb;

    public MovieItemFragment(int index, MovieRepo.Movie movie) {
        this.index = index;
        this.movie = movie;
    }

    public MovieItemFragment(int index, String title, float reservation_rate, int grade, String date, String thumb) {
        this.index = index;
        this.title = title;
        this.reservation_rate = reservation_rate;
        this.grade = grade;
        this.date = date;
        this.thumb = thumb;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_movie_item, container, false);

        imageThumb  = root.findViewById(R.id.imageThumb);
        textIndex = root.findViewById(R.id.textIndex);
        textTitle = root.findViewById(R.id.textTitle);
        textReservationRate = root.findViewById(R.id.textReservationRate);
        textGrade = root.findViewById(R.id.textGrade);
        textDate = root.findViewById(R.id.textDate);

        setInfo();

        return root;
    }

    private void setInfo() {
        Glide.with(this) // 글라이드 사용해서 이미지 보여주기
                .load(movie.getImage())
                .into(imageThumb);
        textIndex.setText(index  + ". ");
        textTitle.setText(movie.getTitle());
        textReservationRate.setText("예매율 " + movie.getReservation_rate() + "%");
        textGrade.setText(movie.getGrade() + "세 관람가");
        textDate.setText(movie.getDate());
    }
}
