package com.example.hp.challengecup.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class ScrollListenerRecyclerView extends RecyclerView {
    private ScrollListener listener;
    public ScrollListenerRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ScrollListenerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListenerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener==null)return;
        if(!canScrollHorizontally(1)){
            //到底了
            listener.isBottom();
        }else{
            listener.notBottom();
        }
    }

    public void addScrollListener(ScrollListener listener){
        this.listener = listener;
    }

    public interface ScrollListener{
        void isBottom();
        void notBottom();
    }
}
