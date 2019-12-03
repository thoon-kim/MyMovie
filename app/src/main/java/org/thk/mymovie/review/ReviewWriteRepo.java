package org.thk.mymovie.review;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ReviewWriteRepo {
    @SerializedName("message") String message = null;
    @SerializedName("code") int code;
    @SerializedName("result") String result;

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    public interface reviewInterface {
        @POST("createComment")
        Call<ReviewWriteRepo> createReview(@Query("id") int id, @Query("writer") String writer, @Query("time") String time,
                                                      @Query("rating") float rating, @Query("contents") String contents);
    }
}
