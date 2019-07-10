package com.example.hp.challengecup.mvp.activity;

import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.adapter.recyclerview.MatchFacialAdapter;
import com.example.hp.challengecup.adapter.recyclerview.MatchFacialItem;
import com.example.hp.challengecup.adapter.recyclerview.MatchResultAdapter;
import com.example.hp.challengecup.adapter.recyclerview.MatchResultItem;
import com.example.hp.challengecup.custom.ScrollListenerRecyclerView;
import com.example.hp.challengecup.custom.StrokeTextView;
import com.example.hp.challengecup.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MatchActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.rv_match_result)
    ScrollListenerRecyclerView rvMatchResult;
    @Bind(R.id.rv_match_facial)
    RecyclerView rvMatchFacial;
    @Bind(R.id.iv_next_page)
    StrokeTextView ivNextPage;

    MatchResultAdapter resultAdapter;
    List<MatchResultItem> resultList = new ArrayList<>();
    MatchFacialAdapter facialAdapter;
    List<MatchFacialItem> facialList = new ArrayList<>();

    private boolean isNextPage = true;
    @Override
    protected int getContentView() {
        return R.layout.activity_match;
    }

    @Override
    protected void doBeforeContentView() {
        DensityUtil.setCustomDensity(this,getApplication(),375);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        rvMatchResult.addScrollListener(new ScrollListenerRecyclerView.ScrollListener() {
            @Override
            public void isBottom() {
//                ivNextPage.setImageResource(R.drawable.rematch);
                ivNextPage.setText("重新检测");
                isNextPage = false;
            }
            @Override
            public void notBottom() {
                if(isNextPage)return;
//                ivNextPage.setImageResource(R.drawable.next_page);
                ivNextPage.setText("下一页");
                isNextPage = true;
            }
        });
    }

    @Override
    protected void initView() {
        initMatchResult();
        initMatchFacial();
    }

    private void initMatchFacial() {
        facialAdapter = new MatchFacialAdapter(this,R.layout.match_result_facial_item,facialList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        rvMatchFacial.setAdapter(facialAdapter);
        rvMatchFacial.setLayoutManager(layoutManager);
        initFacialData();
    }

    private void initFacialData() {
        TypedArray array = getResources().obtainTypedArray(R.array.match_facial);
        String[] strings = getResources().getStringArray(R.array.match_facial_text);
        for(int i=0;i<array.length();i++){
            facialList.add(new MatchFacialItem(array.getResourceId(i,0),strings[i]));
        }
        facialAdapter.notifyDataSetChanged();
    }

    private void initMatchResult() {
        resultAdapter = new MatchResultAdapter(this,R.layout.match_result_item,resultList);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2, LinearLayoutManager.HORIZONTAL,false);
        rvMatchResult.setAdapter(resultAdapter);
        rvMatchResult.setLayoutManager(layoutManager);
        initResultData();
    }

    private void initResultData() {
        TypedArray array = getResources().obtainTypedArray(R.array.match_result);
        for(int i=0;i<array.length();i++){
            resultList.add(new MatchResultItem(array.getResourceId(i,0)));
        }
        resultAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
        }
    }
}
