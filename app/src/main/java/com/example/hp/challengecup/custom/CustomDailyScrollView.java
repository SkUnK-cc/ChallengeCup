package com.example.hp.challengecup.custom;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hp.challengecup.utils.DisplayUtil;

public class CustomDailyScrollView extends ScrollView {
    public static final String TAG = "CustomDailyScrollView";

    private LinearLayout inner;
    private TextView hotText;
    private RecyclerView hRecycler;
    private RecyclerView vRecycler;
    private TextView bottomText;

    private float y;
    private int height;
    private Rect normal = new Rect();
    public CustomDailyScrollView(Context context) {
        super(context);
    }

    public CustomDailyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDailyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount()>0){
            inner = (LinearLayout) getChildAt(0);
        }
//        hotText = (TextView) inner.getChildAt(0);
//        hRecycler = (RecyclerView) inner.getChildAt(1);
//        vRecycler = (RecyclerView) ((RelativeLayout) inner.getChildAt(2)).getChildAt(0);
        bottomText = (TextView) inner.getChildAt(3);
    }

    //控制滚动
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        height = inner.getMeasuredHeight() - bottomText.getMeasuredHeight() - getMeasuredHeight()+ DisplayUtil.dip2px(getContext(), 55);
//        Log.e(TAG, "onScrollChanged: height = "+height);
        //这里要确保滚动到固定位置后不再滚动（转为layout），需要让它一直滚动到该位置
        if(t>=height){
            scrollTo(0,height);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(inner == null){
            return super.onTouchEvent(ev);
        }else{
            disposeTouchEvent(ev);
            return super.onTouchEvent(ev);
        }
    }

    private void disposeTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                y = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                if(getScrollY() == height && !normal.isEmpty()){
                    animation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final float preY = y;
                float nowY = ev.getY();
                int deltaY = (int) (nowY-preY);
                if(isNeedMove(deltaY)){
                    Log.e(TAG, "disposeTouchEvent: need move!!");
                    if(normal.isEmpty()){
                        normal.set(inner.getLeft(),inner.getTop(),inner.getRight(),inner.getBottom());
                        Log.e(TAG, "disposeTouchEvent: return");
                        return;
                    }
                    if(nowY < preY){
                        Log.e(TAG, "disposeTouchEvent: move");
                        int moveY = (int) Math.sqrt(Math.abs(deltaY*2));
                        inner.layout(inner.getLeft(),inner.getTop()-moveY,inner.getRight(),inner.getBottom()-moveY);
                    }
                }
                y = nowY;
                break;
            default:
                break;
        }
    }

    private void animation() {
        TranslateAnimation ta = new TranslateAnimation(0,0,inner.getTop(),normal.top);
        Interpolator in = new DecelerateInterpolator();
        ta.setInterpolator(in);
        ta.setDuration(300);
        inner.startAnimation(ta);
        inner.layout(normal.left,normal.top,normal.right,normal.bottom);
        normal.setEmpty();
    }

    private boolean isNeedMove(float deltaY) {
        int scrollY = getScrollY();
        Log.e(TAG, "isNeedMove: scrollY = "+scrollY);
        Log.e(TAG, "isNeedMove: height = "+height);
        return scrollY == height;
    }
}
