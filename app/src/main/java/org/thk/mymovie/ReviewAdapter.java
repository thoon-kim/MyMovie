package org.thk.mymovie;

import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    public ArrayList<Review> items;

    public ReviewAdapter() {
        items = new ArrayList<Review>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.review_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Review item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Review item) {
        items.add(item);
    }

    public void setItems(ArrayList<Review> items) {
        this.items = items;
    }

    public Review getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Review item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewId;
        TextView reviewDate;
        RatingBar reviewRating;
        TextView reviewComment;
        TextView reviewLike;

        public ViewHolder(View itemView){
            super(itemView);
            reviewId = itemView.findViewById(R.id.reviewId);
            reviewDate = itemView.findViewById(R.id.reviewDate);
            reviewRating = itemView.findViewById(R.id.reviewRating);
            reviewComment = itemView.findViewById(R.id.reviewComment);
            reviewLike = itemView.findViewById(R.id.reviewLike);
        }

        public void setItem(Review item){
            reviewId.setText(String.valueOf(item.getId()));
            reviewDate.setText(item.getDate());
            reviewRating.setRating(Float.valueOf(item.getRating()));
            reviewComment.setText(item.getComment());
            reviewLike.setText(String.valueOf(item.getLike()));
        }
    }
}
