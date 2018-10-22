package com.example.hp.challengecup.fragment.instance;

import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.adapter.recyclerview.HomeHorizontalAdapter;
import com.example.hp.challengecup.adapter.recyclerview.HomeHorizontalItem;
import com.example.hp.challengecup.adapter.recyclerview.HomeVerticalAdapter;
import com.example.hp.challengecup.adapter.recyclerview.HomeVerticleItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class HomeFragment extends BaseFragment {

    @Bind(R.id.home_recycler_horizontal)
    RecyclerView rv_horizontal;
    @Bind(R.id.home_recycler_verticle)
    RecyclerView rv_vertical;

    //horizontal
    List<HomeHorizontalItem> hList = new ArrayList<>();
    HomeHorizontalAdapter hAdapter;
    //verticle
    List<HomeVerticleItem> vList = new ArrayList<>();
    HomeVerticalAdapter vAdapter;
    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
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
        rv_horizontal.setLayoutManager(horizontalLayoutManager);
        hAdapter = new HomeHorizontalAdapter(getContext(),R.layout.home_hori_item, hList);
        rv_horizontal.setAdapter(hAdapter);
    }

    private void getHListData() {
        TypedArray hArray = getContext().getResources().obtainTypedArray(R.array.home_list);
        int len = hArray.length();
        for(int i=0;i<len;i++){
            hList.add(new HomeHorizontalItem(hArray.getResourceId(i,0)));
        }
        hArray.recycle();
    }

    private void initVerticleRecy() {
        getVListData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rv_vertical.setLayoutManager(gridLayoutManager);
        rv_vertical.setNestedScrollingEnabled(false);
        vAdapter = new HomeVerticalAdapter(getContext(),R.layout.home_verticle_item, vList);
        rv_vertical.setAdapter(vAdapter);
    }

    private void getVListData() {
        TypedArray vArray = getContext().getResources().obtainTypedArray(R.array.home_recycler);
        int length = vArray.length();
        for(int i=0;i<length;i++){
            vList.add(new HomeVerticleItem(vArray.getResourceId(i,0),"韩国芝兰深层补水面膜"));
        }
    }
}
