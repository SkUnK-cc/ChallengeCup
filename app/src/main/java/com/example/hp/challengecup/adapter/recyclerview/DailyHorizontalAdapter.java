package com.example.hp.challengecup.adapter.recyclerview;

import android.content.Context;
import android.widget.ImageView;

import com.example.hp.challengecup.R;

import java.util.List;

public class DailyHorizontalAdapter extends CommonAdapter<DailyHorizontalItem> {
    public DailyHorizontalAdapter(Context context, int layoutId, List<DailyHorizontalItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(CommonViewHolder holder, DailyHorizontalItem dailyHorizontalItem) {
        ImageView imageView = holder.getView(R.id.daily_recy_hori_img);
        imageView.setImageResource(dailyHorizontalItem.imgId);
    }
}
