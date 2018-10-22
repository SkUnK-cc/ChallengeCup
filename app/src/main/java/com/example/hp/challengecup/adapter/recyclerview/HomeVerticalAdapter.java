package com.example.hp.challengecup.adapter.recyclerview;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.challengecup.R;

import java.util.List;

public class HomeVerticalAdapter extends CommonAdapter<HomeVerticleItem> {

    public HomeVerticalAdapter(Context context, int layoutId, List<HomeVerticleItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(CommonViewHolder holder, HomeVerticleItem item) {
        ((ImageView)holder.getView(R.id.iv_home_verticle_item)).setImageResource(item.imgId);
        ((TextView)holder.getView(R.id.tv_home_verticle_item)).setText(item.text);
    }
}
