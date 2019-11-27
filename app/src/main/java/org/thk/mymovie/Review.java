package org.thk.mymovie;

public class Review {
    String writer;
    String time;
    float rating;
    String contents;
    int recommend;

    public Review(String writer, String time, float rating, String contents, int recommend) {
        this.writer = writer;
        this.time = time;
        this.rating = rating;
        this.contents = contents;
        this.recommend = recommend;
    }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public String getContents() { return contents; }
    public void setContents(String contents) { this.contents = contents; }
    public int getRecommend() { return recommend; }
    public void setRecommend(int recommend) { this.recommend = recommend; }

    @Override
    public String toString() {
        return "Review{" +
                "writer='" + writer + '\'' +
                ", time='" + time + '\'' +
                ", rating='" + rating + '\'' +
                ", contents='" + contents + '\'' +
                ", recommend=" + recommend +
                '}';
    }
}
