package com.example.hp.challengecup.adapter.recyclerview;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.challengecup.R;

import java.util.List;

public class MatchFacialAdapter extends CommonAdapter<MatchFacialItem> {
    public MatchFacialAdapter(Context context, int layoutId, List<MatchFacialItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(CommonViewHolder holder, MatchFacialItem matchFacialItem) {
        ImageView imageView = holder.getView(R.id.iv_match_facial);
        imageView.setImageResource(matchFacialItem.img);
        //note:需要转换成自定义的textView，不需要重写setText
        TextView textView = holder.getView(R.id.tv_match_facial);
        textView.setText(matchFacialItem.text);
    }
}
