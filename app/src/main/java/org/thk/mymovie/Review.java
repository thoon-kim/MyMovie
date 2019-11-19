package org.thk.mymovie;

public class Review {
    String title;
    String id;
    String date;
    String rating;
    String comment;
    int like = 0;

    public Review(String title, String id, String date, String rating, String comment) {
        this.title = title;
        this.id = id;
        this.date = date;
        this.rating = rating;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "Review{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", rating='" + rating + '\'' +
                ", comment='" + comment + '\'' +
                ", like=" + like +
                '}';
    }
}
