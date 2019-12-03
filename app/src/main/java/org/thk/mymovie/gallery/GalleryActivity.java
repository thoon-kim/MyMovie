package org.thk.mymovie.gallery;

import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import org.thk.mymovie.R;

import static java.lang.Math.abs;

public class GalleryActivity extends AppCompatActivity {
    PhotoView photoView;
    String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");

        photoView = findViewById(R.id.photoView);

        Glide.with(this) // 글라이드 사용해서 이미지 보여주기
                .load(url)
                .into(photoView);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                if (event.getPointerCount() == 2) {
//                    touch_interval_X = abs(event.getX(0) - event.getX(1)); // 두 손가락 X좌표 차이 절대값
//                    touch_interval_Y = abs(event.getY(0) - event.getY(1)); // 두 손가락 Y좌표 차이 절대값
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (event.getPointerCount() == 2) {
//                    float now_interval_X = abs(event.getX(0) - event.getX(1)); // 두 손가락 X좌표 차이 절대값
//                    float now_interval_Y = abs(event.getY(0) - event.getY(1)); // 두 손가락 Y좌표 차이 절대값
//                    // 이전 값과 비교
//                    if(touch_interval_X < now_interval_X && touch_interval_Y < now_interval_Y) {
//                        // 확대기능
//                        imageView.setScaleX((now_interval_X / touch_interval_X) * 2);
//                        imageView.setScaleY((now_interval_Y / touch_interval_Y) * 2);
//                    }
//                    if(touch_interval_X > now_interval_X && touch_interval_Y > now_interval_Y) {
//                        // 축소기능
//                        imageView.setScaleX((now_interval_X / touch_interval_X) * 1/2);
//                        imageView.setScaleY((now_interval_Y / touch_interval_Y) * 1/2);
//                    }
//                    touch_interval_X = abs(event.getX(0) - event.getX(1));
//                    touch_interval_Y = abs(event.getY(0) - event.getY(1));
//                }
//        }
//        return super.onTouchEvent(event);
//    }
}
