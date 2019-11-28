package org.thk.mymovie;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ReviewRepo {
    @SerializedName("message") String message = null;
    @SerializedName("code") int code;
    @SerializedName("result")
    List<Review> reviews = new ArrayList<>();

    public List<Review> getResults() { return reviews; }
    public static class Review {
        @SerializedName("id") int review_id;
        @SerializedName("movieId") int movie_id;
        @SerializedName("writer") String writer;
        @SerializedName("time") String time;
        @SerializedName("rating") float rating;
        @SerializedName("contents") String contents;
        @SerializedName("recommend") int recommend;

        public Review(int review_id, int movie_id, String writer, String time, float rating, String contents, int recommend) {
            this.review_id = review_id;
            this.movie_id = movie_id;
            this.writer = writer;
            this.time = time;
            this.rating = rating;
            this.contents = contents;
            this.recommend = recommend;
        }

        public int getReview_id() { return review_id; }
        public int getMovie_id() { return movie_id; }
        public String getWriter() { return writer; }
        public String getTime() { return time; }
        public float getRating() { return rating; }
        public String getContents() { return contents; }
        public int getRecommend() { return recommend; }

        @Override
        public String toString() {
            return "Review{" +
                    "review_id=" + review_id +
                    ", writer='" + writer + '\'' +
                    ", movie_id=" + movie_id +
                    ", time='" + time + '\'' +
                    ", rating=" + rating +
                    ", contents='" + contents + '\'' +
                    ", recommend=" + recommend +
                    '}';
        }
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    public interface reviewInterface {
        @GET("readCommentList")
        Call<ReviewRepo> getReviews(@Query("id") int id, @Query("limit") String limit);
    }
}