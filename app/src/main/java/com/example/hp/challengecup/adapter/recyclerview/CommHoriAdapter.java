package com.example.hp.challengecup.adapter.recyclerview;

import android.content.Context;
import android.widget.ImageView;

import com.example.hp.challengecup.R;

import java.util.List;

public class CommHoriAdapter extends CommonAdapter<CommHoriItem> {

    public CommHoriAdapter(Context context, int layoutId, List<CommHoriItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(CommonViewHolder holder, CommHoriItem commHoriItem) {
        ImageView imageView = holder.getView(R.id.comm_recy_hori_img);
        imageView.setImageResource(commHoriItem.imgId);
    }
}
