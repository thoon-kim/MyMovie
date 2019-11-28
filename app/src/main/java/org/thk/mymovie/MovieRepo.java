package org.thk.mymovie;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MovieRepo {

    @SerializedName("message") String message = null;
    @SerializedName("code") int code;
    @SerializedName("result")
    List<Movie> movies = new ArrayList<>();

    public List<Movie> getResults() { return movies; }
    public static class Movie {
        @SerializedName("id") int id; // id
        @SerializedName("title") String title; // 제목
        @SerializedName("reservation_grade") int reservation_grade; // 예매 순위
        @SerializedName("reservation_rate") float reservation_rate; // 예매율
        @SerializedName("grade") int grade; // 관람 등급
        @SerializedName("date") String date; // 개봉일
        @SerializedName("thumb") String thumb; // 이미지 url
        @SerializedName("image") String image; // 이미지 url
        @SerializedName("audience_rating") float audience_rating; // 관람객 평점
        @SerializedName("genre") String genre; // 장르
        @SerializedName("duration") int duration; // 상영 시간
        @SerializedName("audience") int audience; // 누적 관람객
        @SerializedName("synopsis") String synopsis; // 줄거리
        @SerializedName("director") String director; // 감독
        @SerializedName("actor") String actor; // 배우
        @SerializedName("like") int like; // 좋아요 수
        @SerializedName("dislike") int dislike; // 싫어요 수

        public Movie(int id, String title, float reservation_rate, int grade, String date, String thumb, int reservation_grade, float audience_rating,
                String genre, int duration, int audience, String synopsis, String director, String actor, int like, int dislike) {
            this.id = id;
            this.title = title;
            this.reservation_rate = reservation_rate;
            this.grade = grade;
            this.thumb = thumb;
            this.reservation_grade = reservation_grade;
            this.audience_rating = audience_rating;
            this.genre = genre;
            this.duration = duration;
            this. audience = audience;
            this.synopsis = synopsis;
            this.director = director;
            this.actor = actor;
            this.like = like;
            this.dislike = dislike;
        }

        public Movie(int id, String title, float reservation_rate, int grade, String date, String image) {
            this.id = id;
            this.title = title;
            this.reservation_rate = reservation_rate;
            this.grade = grade;
            this.date = date;
            this.image = image;
        }

        public int getId() { return id; }
        public String getTitle() { return title; }
        public int getReservation_grade() { return reservation_grade; }
        public float getReservation_rate() { return reservation_rate; }
        public int getGrade() { return grade; }
        public String getDate() { return date; }
        public String getThumb() { return thumb; }
        public String getImage() { return image; }
        public float getAudience_rating() { return audience_rating; }
        public String getGenre() { return genre; }
        public int getDuration() { return duration; }
        public int getAudience() { return audience; }
        public String getSynopsis() { return synopsis; }
        public String getDirector() { return director; }
        public String getActor() { return actor; }
        public int getLike() { return like; }
        public int getDislike() { return dislike; }

        @Override
        public String toString() {
            return "Movie{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", reservation_grade=" + reservation_grade +
                    ", reservation_rate=" + reservation_rate +
                    ", grade=" + grade +
                    ", date='" + date + '\'' +
                    ", thumb='" + thumb + '\'' +
                    ", image='" + image + '\'' +
                    ", audience_rating=" + audience_rating +
                    ", genre='" + genre + '\'' +
                    ", duration=" + duration +
                    ", audience=" + audience +
                    ", synopsis='" + synopsis + '\'' +
                    ", director='" + director + '\'' +
                    ", actor='" + actor + '\'' +
                    ", like=" + like +
                    ", dislike=" + dislike +
                    '}';
        }
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    public interface movieInterface {
        @GET("readMovieList")
        Call<MovieRepo> getMovieList(@Query("type") int type);

        @GET("readMovie")
        Call<MovieRepo> getMovieDetail(@Query("id") int id);
    }
}