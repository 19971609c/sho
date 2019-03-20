package com.bw.sho.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/19 13:42:11
 * @Description:
 */
public class DetailsAdapter extends PagerAdapter {

    private List<ImageView> split;

    public DetailsAdapter(List<ImageView> split) {
        this.split = split;
    }

    @Override
    public int getCount() {
        return split.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = split.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
