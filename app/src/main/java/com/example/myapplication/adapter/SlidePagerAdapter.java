package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myapplication.R;
import com.example.myapplication.model.Slide;

import java.util.List;

public class SlidePagerAdapter extends PagerAdapter {
    private Context context;
    private List<Slide> slideList;

    public SlidePagerAdapter(Context context, List<Slide> slideList) {
        this.context = context;
        this.slideList = slideList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.slide_layout, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(slideList.get(position).getImageResId());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return slideList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}