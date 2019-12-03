package org.thk.mymovie.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.thk.mymovie.MainActivity;
import org.thk.mymovie.R;
import org.thk.mymovie.movie.MovieDetailFragment;
import org.thk.mymovie.review.ReviewAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.FutureTask;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    public ArrayList<String> items;
    Context mContext;

    public GalleryAdapter(Context mContext) {
        items = new ArrayList<>();
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.gallery_item, viewGroup, false);

        return new GalleryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.ViewHolder viewHolder, final int position) {
        String item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.imageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = items.get(position);
                Log.d("GA onClick", "" + url);
                if (url.contains("youtu")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, GalleryActivity.class);
                    intent.putExtra("url", url);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(String item) {
        Log.d("GA.addItem : ", item);
        items.add(item);
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public String getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, String item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageGallery;
        ImageView imagePlay;

        public ViewHolder(View itemView){
            super(itemView);
            imageGallery = itemView.findViewById(R.id.imageGallery);
            imagePlay = itemView.findViewById(R.id.imagePlay);
        }

        public void setItem(String item){
            if (item.contains("youtube.com/watch?v=")) {
                String id = item.substring(item.lastIndexOf("?v=")+3);  //맨마지막 '/'뒤에 id가있으므로 그것만 파싱해줌
                Log.d("파싱한 아이디id 값", id);
                String url ="https://img.youtube.com/vi/"+ id+ "/" + "default.jpg";  //유튜브 썸네일 불러오는 방법

                Glide.with(this.itemView)
                        .load(url)
                        .into(imageGallery);

                imagePlay.setVisibility(View.VISIBLE); //동영상이면 재생버튼도 보이게한다.
            } else if (item.contains("youtu.be")) {
                String id = item.substring(item.lastIndexOf("/") + 1);  //맨마지막 '/'뒤에 id가있으므로 그것만 파싱해줌
                Log.d("파싱한 아이디id 값", id);
                String url ="https://img.youtube.com/vi/"+ id+ "/" + "default.jpg";  //유튜브 썸네일 불러오는 방법

                Glide.with(this.itemView)
                        .load(url)
                        .into(imageGallery);
                imagePlay.setVisibility(View.VISIBLE); //동영상이면 재생버튼도 보이게한다.
            } else {
                Glide.with(this.itemView)
                        .load(item)
                        .into(imageGallery);
            }
        }
    }
}