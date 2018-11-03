package com.example.hp.challengecup.adapter.recyclerview;

import android.content.Context;
import android.widget.ImageView;

import com.example.hp.challengecup.R;

import java.util.List;

public class MatchResultAdapter extends CommonAdapter<MatchResultItem> {


    public MatchResultAdapter(Context context, int layoutId, List<MatchResultItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(CommonViewHolder holder, MatchResultItem matchResultItem) {
        ImageView imageView = holder.getView(R.id.iv_match_result);
        imageView.setImageResource(matchResultItem.imgId);
    }

}
