package org.thk.mymovie.movie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.thk.mymovie.gallery.GalleryAdapter;
import org.thk.mymovie.utils.DBHelper;
import org.thk.mymovie.MainActivity;
import org.thk.mymovie.utils.NetworkStatus;
import org.thk.mymovie.R;
import org.thk.mymovie.review.ReviewAdapter;
import org.thk.mymovie.review.ReviewListActivity;
import org.thk.mymovie.review.ReviewRepo;
import org.thk.mymovie.review.ReviewWriteActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailFragment extends Fragment implements MainActivity.OnBackPressedListener {
    final static private String DB_NAME = "movie.db";
    InternalListener iListener = new InternalListener();
    Toolbar actionBar;

    DBHelper dbHelper;
    MovieThread movieThread;
    NetworkStatus networkStatus;

    RecyclerView reviewRV;
    ReviewAdapter reviewAdapter;

    RecyclerView galleryRV;
    GalleryAdapter galleryAdapter;

    ImageView imageThumb, imageGrade, imageGallery;
    TextView textTitle, textSynopsis, textDirector, textActor;
    TextView textDate, textGenreDuration;
    TextView textLike, textDislike;
    TextView textReservation, textAudienceRating, textAudience;

    RatingBar ratingBar;
    ImageButton btnLike, btnDislike;
    TextView btnReviewWrite, btnReviewList;

    private int id;
    private String grade;
    private String photos;
    private String videos;
    private String[] gallery;

//    TextView btnBook;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        actionBar = ((MainActivity)getActivity()).findViewById(R.id.toolbar);
        actionBar.setTitle("영화 상세");

        id = getArguments().getInt("id");
        Log.d("Taehoon MDF", "ID : " + id);
        imageThumb = root.findViewById(R.id.imageThumb);
        imageGrade = root.findViewById(R.id.imageGrade);
//        imageGallery = root.findViewById(R.id.imageGallery);
        textTitle = root.findViewById(R.id.textTitle);
        textSynopsis = root.findViewById(R.id.textSynopsis);
        textDirector = root.findViewById(R.id.textDirector);
        textActor = root.findViewById(R.id.textActor);
        textDate = root.findViewById(R.id.textDate);
        textGenreDuration = root.findViewById(R.id.textGenreDuration);
        textLike = root.findViewById(R.id.textLike);
        textDislike = root.findViewById(R.id.textDislike);
        textReservation = root.findViewById(R.id.textReservation);
        textAudienceRating = root.findViewById(R.id.textAudienceRating);
        textAudience = root.findViewById(R.id.textAudience);
        ratingBar = root.findViewById(R.id.ratingBar);
        reviewRV = root.findViewById(R.id.reviewPrevealRV);
        galleryRV = root.findViewById(R.id.galleryRV);

        dbHelper = new DBHelper(this.getContext(), DB_NAME, null, 1);

        // 네트워크가 연결되어 있으면 api에서 데이터를 다운받아 DB에 저장
        networkStatus = new NetworkStatus();
        movieThread = new MovieThread(this.getContext(), new Handler());
        if (networkStatus.getConnectivityStatus(this.getContext()) == 1 ||
                networkStatus.getConnectivityStatus(this.getContext()) == 2 ) {
            movieThread.getReviews(id, "all");
        }
        movieThread.getGalleries(id);
        setDetailPage(dbHelper.getMovieDetail(id));

        btnLike = root.findViewById(R.id.btnLike);
        btnDislike = root.findViewById(R.id.btnDislike);
        btnReviewWrite = root.findViewById(R.id.btnReviewWrite);
        btnReviewList = root.findViewById(R.id.btnReviewList);
//        btnBook = root.findViewById(R.id.book);

        btnLike.setOnClickListener(iListener);
        btnDislike.setOnClickListener(iListener);
        btnReviewWrite.setOnClickListener(iListener);
        btnReviewList.setOnClickListener(iListener);
//        imageGallery.setOnClickListener(iListener);
//        btnBook.setOnClickListener(iListener);

        return root;
    }

    public void setNum(ImageButton btn, boolean state, TextView text, int num) {
        btn.setSelected(state);
        text.setText(String.valueOf(Integer.parseInt((String) text.getText()) + num));
    }

    @Override
    public void onBack() {
        Log.e("Other", "onBack()");
        // 리스너를 설정하기 위해 Activity 를 받아옵니다.
        MainActivity activity = (MainActivity)getActivity();
        // 한번 뒤로가기 버튼을 눌렀다면 Listener 를 null 로 해제해줍니다.
        activity.setOnBackPressedListener(null);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, new MovieListFragment());
        fragmentTransaction.commit();
    }

    // Fragment 호출 시 반드시 호출되는 오버라이드 메소드입니다.
    @Override
    // 혹시 Context 로 안되시는분은 Activity 로 바꿔보시기 바랍니다.
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MainActivity)context).setOnBackPressedListener(this);
    }

    protected class InternalListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnLike:
                    if (btnDislike.isSelected() == true) {
                        setNum(btnLike, true, textLike, 1);
                        setNum(btnDislike, false, textDislike, -1);
                    } else if(btnLike.isSelected() == true) {
                        setNum(btnLike, false, textLike, -1);
                    } else {
                        setNum(btnLike, true, textLike, 1);
                    }
                    break;
                case R.id.btnDislike:
                    if (btnLike.isSelected() == true) {
                        setNum(btnDislike, true, textDislike, 1);
                        setNum(btnLike, false, textLike, -1);
                    } else if(btnDislike.isSelected() == true) {
                        setNum(btnDislike, false, textDislike, -1);
                    } else {
                        setNum(btnDislike, true, textDislike, 1);
                    }
                    break;
                case R.id.btnReviewWrite:
                    Intent intent = new Intent(getContext(), ReviewWriteActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("title", textTitle.getText().toString());
                    intent.putExtra("grade", grade);
                    startActivity(intent);
                    break;
                case R.id.btnReviewList:
                    intent = new Intent(getContext(), ReviewListActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("title", textTitle.getText().toString());
                    intent.putExtra("grade", grade);
                    startActivity(intent);
                    break;
                case R.id.imageGallery:
                    break;
            }
        }
    }

    public void setDetailPage(MovieRepo.Movie movie) {
        Log.d("MDF setDetailPage", movie.toString());
        Glide.with(MovieDetailFragment.this)
                .load(movie.getThumb())
                .into(imageThumb);
        textTitle.setText(movie.getTitle());
        textSynopsis.setText(movie.getSynopsis());
        textDirector.setText(movie.getDirector());
        textActor.setText(movie.getActor());

        // 날짜 형식 변경
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date to = df.parse(movie.getDate());
            String result = df.format(to);
            textDate.setText(result + " 개봉");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        grade = String.valueOf(movie.getGrade());
        switch (grade) {
            case "12":
                imageGrade.setImageResource(R.drawable.ic_12);
                break;
            case "15":
                imageGrade.setImageResource(R.drawable.ic_15);
                break;
            case "19":
                imageGrade.setImageResource(R.drawable.ic_19);
                break;
            case "all":
                imageGrade.setImageResource(R.drawable.ic_all);
                break;
        }

        textGenreDuration.setText(movie.getGenre() + " / " + movie.getDuration() + "분");
        textLike.setText(String.valueOf(movie.getLike()));
        textDislike.setText(String.valueOf(movie.getDislike()));
        textReservation.setText(movie.getReservation_grade() + "위 " + movie.getReservation_rate() + "%");
        ratingBar.setRating(movie.getAudience_rating() / 2);
        textAudienceRating.setText(String.valueOf(movie.getAudience_rating()));
        textAudience.setText(String.format("%,3d", movie.getAudience())  + "명");

        reviewAdapter = new ReviewAdapter();
        reviewAdapter.setItems(dbHelper.getReview(id, "2"));
        reviewRV.setAdapter(reviewAdapter);
    }

    public class MovieThread extends Thread {
        final static String TAG = "RLA / ReviewThread";
        Context mContext;
        ReviewRepo reviewRepo;
        MovieRepo movieRepo;
        Handler handler;

        public MovieThread(Context mContext, Handler handler) {
            this.mContext = mContext;
            this.handler = handler;
        }

        public void getReviews(int id, String limit) {
            super.run();
            Retrofit client = new Retrofit.Builder().baseUrl("http://boostcourse-appapi.connect.or.kr:10000/movie/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            ReviewRepo.reviewInterface service = client.create(ReviewRepo.reviewInterface.class);
            Call<ReviewRepo> call = service.getReviews(id, limit);

            call.enqueue(new Callback<ReviewRepo>() {
                @Override
                public void onResponse(Call<ReviewRepo> call, Response<ReviewRepo> response) {
                    if (response.isSuccessful()) {
                        reviewRepo = response.body();
                        Log.d(TAG, "response.raw : " + response.raw());

                        if (reviewRepo.getCode() == 200) {
                            Log.d(TAG, "reviewItems size : " + String.valueOf(reviewRepo.getResults().size()));
                            for (int i = 0; i < reviewRepo.getResults().size(); i++) {
                                ReviewRepo.Review review = reviewRepo.getResults().get(i);
                                dbHelper.setDataReview(review);
                            }
                        } else {
                            Log.e(TAG, "요청 실패 : " + reviewRepo.getCode());
                            Log.e(TAG, "메시지 : " + reviewRepo.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReviewRepo> call, Throwable t) {
                    Log.e(TAG, "리뷰정보 불러오기 실패 : " + t.getMessage());
                    Log.e(TAG, "요청 메시지 : " + call.request());
                }
            });
        }

        public void getGalleries(int id) {
            super.run();
            Retrofit client = new Retrofit.Builder().baseUrl("http://boostcourse-appapi.connect.or.kr:10000/movie/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MovieRepo.movieInterface service = client.create(MovieRepo.movieInterface.class);
            Call<MovieRepo> call = service.getMovieDetail(id);

            call.enqueue(new Callback<MovieRepo>() {
                @Override
                public void onResponse(Call<MovieRepo> call, Response<MovieRepo> response) {
                    if (response.isSuccessful()) {
                        movieRepo = response.body();
                        Log.d(TAG, "response.raw : " + response.raw());

                        if (movieRepo.getCode() == 200) {
                            Log.d(TAG, "reviewItems size : " + String.valueOf(movieRepo.getResults().size()));
                            for (int i = 0; i < movieRepo.getResults().size(); i++) {
                                MovieRepo.Movie movie = movieRepo.getResults().get(i);
                                photos = movie.getPhotos();
                                videos = movie.getVideos();
                                galleryAdapter = new GalleryAdapter(getContext());
                                gallery = (photos + ", " + videos).split(",");

                                for (int j = 0; j < gallery.length; j++) {
                                    galleryAdapter.addItem(gallery[j]);
                                }
                                galleryRV.setAdapter(galleryAdapter);
                                Log.d("MDF getGalleris", photos + ", " + videos);
                            }
                        } else {
                            Log.e(TAG, "요청 실패 : " + movieRepo.getCode());
                            Log.e(TAG, "메시지 : " + movieRepo.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieRepo> call, Throwable t) {
                    Log.e(TAG, "리뷰정보 불러오기 실패 : " + t.getMessage());
                    Log.e(TAG, "요청 메시지 : " + call.request());
                }
            });
        }
    }
}
