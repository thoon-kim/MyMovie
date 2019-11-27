package org.thk.mymovie;

import android.util.Log;
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
        Log.d("R.A", item.toString());
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
        TextView textWriter;
        TextView textTime;
        RatingBar reviewRating;
        TextView textContent;
        TextView textRecommend;

        public ViewHolder(View itemView){
            super(itemView);
            textWriter = itemView.findViewById(R.id.textWriter);
            textTime = itemView.findViewById(R.id.textTime);;
            reviewRating = itemView.findViewById(R.id.reviewRating);;
            textContent = itemView.findViewById(R.id.textContent);;
            textRecommend = itemView.findViewById(R.id.textRecommend);;
        }

        public void setItem(Review item){
            textWriter.setText(String.valueOf(item.getWriter()));
            textTime.setText(item.getTime());
            reviewRating.setRating(item.getRating() / 2);
            textContent.setText(item.getContents());
            textRecommend.setText(String.valueOf(item.getRecommend()));
        }
    }
}
