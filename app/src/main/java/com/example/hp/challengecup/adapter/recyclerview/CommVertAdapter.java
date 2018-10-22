package com.example.hp.challengecup.adapter.recyclerview;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.challengecup.R;

import java.util.List;

public class CommVertAdapter extends CommonAdapter<CommVertItem> {

    public CommVertAdapter(Context context, int layoutId, List<CommVertItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(CommonViewHolder holder, CommVertItem commVertItem) {
        ImageView imageView = holder.getView(R.id.comm_recy_vert_img);
        TextView textView = holder.getView(R.id.comm_recy_vert_text);
        imageView.setImageResource(commVertItem.imgId);
        textView.setText(commVertItem.text);
    }
}
