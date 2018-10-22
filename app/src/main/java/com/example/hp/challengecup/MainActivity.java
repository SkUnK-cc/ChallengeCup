package com.example.hp.challengecup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.challengecup.adapter.viewpager.TabFragmentPagerAdapter;
import com.example.hp.challengecup.custom.CustomViewPager;
import com.example.hp.challengecup.fragment.instance.CommunityFragment;
import com.example.hp.challengecup.fragment.instance.HomeFragment;
import com.example.hp.challengecup.fragment.instance.MarketFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
    public static final int INDEX_HOME = 0;
    public static final int INDEX_COMMUNITY = 1;
    public static final int INDEX_MARKET = 2;
    public static final int INDEX_MYSELF = 3;


    @Bind(R.id.vp_main)
    CustomViewPager mViewPager;
    @Bind(R.id.ll_home)
    LinearLayout llHome;
    @Bind(R.id.img_home)
    ImageView ivHome;
    @Bind(R.id.tv_home)
    TextView tvHome;
    @Bind(R.id.ll_community)
    LinearLayout llCommunity;
    @Bind(R.id.img_community)
    ImageView ivCommunity;
    @Bind(R.id.tv_community)
    TextView tvCommunity;
    @Bind(R.id.ll_market)
    LinearLayout llMarket;
    @Bind(R.id.img_market)
    ImageView ivMarket;
    @Bind(R.id.tv_market)
    TextView tvMarket;
    @Bind(R.id.ll_myself)
    LinearLayout llMyself;
    @Bind(R.id.img_myself)
    ImageView ivMyself;
    @Bind(R.id.tv_myself)
    TextView tvMyself;

    TabFragmentPagerAdapter vpAdapter;
    List<Fragment> fragments = new ArrayList<>();
    SparseArray<LinearLayout> sparseArray = new SparseArray<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        fragments.add(new HomeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new MarketFragment());
        vpAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(vpAdapter);
        mViewPager.setOffscreenPageLimit(vpAdapter.getCount());
        mViewPager.setCurrentItem(0);
        mViewPager.setCanScroll(false);


        llHome.setOnClickListener(this);
        llCommunity.setOnClickListener(this);
        llMarket.setOnClickListener(this);
        llMyself.setOnClickListener(this);
        sparseArray.put(INDEX_HOME,llHome);
        sparseArray.put(INDEX_COMMUNITY,llCommunity);
        sparseArray.put(INDEX_MARKET,llMarket);
        sparseArray.put(INDEX_MYSELF,llMyself);
        navSelect(INDEX_HOME);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_home:
                navSelect(INDEX_HOME);
//                llHome.getChildAt(0).setSelected(true);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.ll_community:
                navSelect(INDEX_COMMUNITY);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.ll_market:
                navSelect(INDEX_MARKET);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.ll_myself:
                navSelect(INDEX_MYSELF);
                mViewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }

    private void navSelect(int index){
//        Log.e(TAG, "navSelect: size = "+sparseArray.size());
        for (int i=0;i<sparseArray.size();i++) {
            int key = sparseArray.keyAt(i);
            LinearLayout linearLayout = sparseArray.get(key);
            if(key == index){
//                Log.e(TAG, "navSelect: select = " + i);
                linearLayout.getChildAt(0).setSelected(true);
                TextView textView = (TextView) linearLayout.getChildAt(1);
                textView.setSelected(true);
            }else{
//                Log.e(TAG, "navSelect: unselect = " + i);
                linearLayout.getChildAt(0).setSelected(false);
                TextView textView = (TextView) linearLayout.getChildAt(1);
                textView.setSelected(false);
            }
        }
    }
}
