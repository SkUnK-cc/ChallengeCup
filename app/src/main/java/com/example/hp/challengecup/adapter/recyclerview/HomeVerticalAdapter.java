package com.example.hp.challengecup.adapter.recyclerview;

import android.content.Context;
import android.os.Build;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.utils.BitmapUtil;

import java.util.List;

public class HomeVerticalAdapter extends CommonAdapter<HomeVerticleItem> {

    public HomeVerticalAdapter(Context context, int layoutId, List<HomeVerticleItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(CommonViewHolder holder, final HomeVerticleItem item) {
//        ((ImageView)holder.getView(R.id.iv_home_verticle_item)).setImageResource(item.imgId);
        final ImageView imageView = holder.getView(R.id.iv_home_verticle_item);
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                    imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                BitmapUtil.setBitmapFromResources(imageView,mContext,item.imgId);
            }
        });
//        BitmapUtil.setBitmapFromResources(imageView,mContext,item.imgId);
        ((TextView)holder.getView(R.id.tv_home_verticle_item)).setText(item.text);
    }
}
