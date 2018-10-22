package com.example.hp.challengecup.adapter.recyclerview;

import android.content.Context;
import android.widget.ImageView;

import com.example.hp.challengecup.R;

import java.util.List;

public class HomeHorizontalAdapter extends CommonAdapter<HomeHorizontalItem> {


    public HomeHorizontalAdapter(Context context, int layoutId, List<HomeHorizontalItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(CommonViewHolder holder, HomeHorizontalItem homeHorizontalItem) {
        ImageView imageView = holder.getView(R.id.iv_home_horiz_item);
        imageView.setImageResource(homeHorizontalItem.imgId);
    }
}
