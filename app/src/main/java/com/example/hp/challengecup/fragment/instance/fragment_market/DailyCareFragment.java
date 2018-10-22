package com.example.hp.challengecup.fragment.instance.fragment_market;

import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.adapter.recyclerview.DailyHorizontalAdapter;
import com.example.hp.challengecup.adapter.recyclerview.DailyHorizontalItem;
import com.example.hp.challengecup.adapter.recyclerview.DailyVerticalAdapter;
import com.example.hp.challengecup.adapter.recyclerview.DailyVerticalItem;
import com.example.hp.challengecup.fragment.instance.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class DailyCareFragment extends BaseFragment {

    @Bind(R.id.daily_recycler_horizontal)
    RecyclerView rvHorizontal;
    @Bind(R.id.daily_recycler_vertical)
    RecyclerView rvVertical;

    DailyHorizontalAdapter hAdapter;
    List<DailyHorizontalItem> hList = new ArrayList<>();
    DailyVerticalAdapter vAdapter;
    List<DailyVerticalItem> vList = new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        initHorizontalRecy();
        initVerticleRecy();
    }

    private void initHorizontalRecy(){
        getHListData();
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext());
        horizontalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHorizontal.setLayoutManager(horizontalLayoutManager);
        hAdapter = new DailyHorizontalAdapter(getContext(),R.layout.daily_hori_item, hList);
        rvHorizontal.setAdapter(hAdapter);
    }

    private void getHListData() {
        TypedArray hArray = getContext().getResources().obtainTypedArray(R.array.daily_recy_hori);
        int len = hArray.length();
        for(int i=0;i<len;i++){
            hList.add(new DailyHorizontalItem(hArray.getResourceId(i,0)));
        }
        hArray.recycle();
    }

    private void initVerticleRecy() {
        getVListData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rvVertical.setLayoutManager(gridLayoutManager);
        rvVertical.setNestedScrollingEnabled(false);
        vAdapter = new DailyVerticalAdapter(getContext(),R.layout.daily_vertical_item, vList);
        rvVertical.setAdapter(vAdapter);
    }

    private void getVListData() {
        TypedArray img = getContext().getResources().obtainTypedArray(R.array.daily_recy_vert_img);
        String[] price = getContext().getResources().getStringArray(R.array.daily_recy_vert_price);
        String[] introduce = getContext().getResources().getStringArray(R.array.daily_recy_vert_introduce);
        int length = img.length();
        for(int i=0;i<length;i++){
            vList.add(new DailyVerticalItem(img.getResourceId(i,0),price[i],introduce[i]));
        }
    }
}
