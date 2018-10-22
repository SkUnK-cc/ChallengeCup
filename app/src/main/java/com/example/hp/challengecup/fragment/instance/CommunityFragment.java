package com.example.hp.challengecup.fragment.instance;

import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.adapter.recyclerview.CommHoriAdapter;
import com.example.hp.challengecup.adapter.recyclerview.CommHoriItem;
import com.example.hp.challengecup.adapter.recyclerview.CommVertAdapter;
import com.example.hp.challengecup.adapter.recyclerview.CommVertItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class CommunityFragment extends BaseFragment {

    @Bind(R.id.comm_recycler_horizontal)
    RecyclerView rv_horizontal;
    @Bind(R.id.comm_recycler_verticle)
    RecyclerView rv_verticle;

    CommHoriAdapter hAdapter;
    List<CommHoriItem> hList = new ArrayList<>();

    CommVertAdapter vAdapter;
    List<CommVertItem> vList = new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.fragment_community;
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
        hAdapter = new CommHoriAdapter(getContext(),R.layout.community_hori_item, hList);
        rv_horizontal.setAdapter(hAdapter);
    }

    private void getHListData() {
        TypedArray hArray = getContext().getResources().obtainTypedArray(R.array.comm_recy_hori);
        int len = hArray.length();
        for(int i=0;i<len;i++){
            hList.add(new CommHoriItem(hArray.getResourceId(i,0)));
        }
        hArray.recycle();
    }

    private void initVerticleRecy() {
        getVListData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rv_verticle.setLayoutManager(layoutManager);
        rv_verticle.setNestedScrollingEnabled(false);
        vAdapter = new CommVertAdapter(getContext(),R.layout.community_verticle_item, vList);
        rv_verticle.setAdapter(vAdapter);
    }

    private void getVListData() {
        TypedArray vArray = getContext().getResources().obtainTypedArray(R.array.comm_recy_vert);
        int length = vArray.length();
        for(int i=0;i<length;i++){
            vList.add(new CommVertItem(vArray.getResourceId(i,0),"刷睫毛的三种方法：\\n1.直接从睫毛根部往外拉，让睫毛变纤长2.用z字形刷睫毛ddddd"));
        }
    }
}
