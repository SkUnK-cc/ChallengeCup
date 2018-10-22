package com.example.hp.challengecup.adapter.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.utils.DisplayUtil;

import java.util.List;

public class DailyVerticalAdapter extends CommonAdapter<DailyVerticalItem> {
    public DailyVerticalAdapter(Context context, int layoutId, List<DailyVerticalItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(CommonViewHolder holder, DailyVerticalItem dailyVerticalItem) {
        ImageView imageView = holder.getView(R.id.iv_daily_verticle_item);
        imageView.setImageResource(dailyVerticalItem.imgId);
        TextView textView = holder.getView(R.id.tv_daily_verticle_item);
        SpannableStringBuilder text = new SpannableStringBuilder(dailyVerticalItem.price+" ");
        text.setSpan(new ForegroundColorSpan(Color.RED),0,text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        text.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(mContext,20)),0,text.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(text.append(dailyVerticalItem.introduce));
    }
}
