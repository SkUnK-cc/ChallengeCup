package com.example.hp.challengecup.fragment.instance;

import android.support.v4.app.Fragment;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.adapter.viewpager.TabFragmentPagerAdapter;
import com.example.hp.challengecup.custom.CustomViewPager;
import com.example.hp.challengecup.fragment.instance.fragment_market.DailyCareFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MarketFragment extends BaseFragment {
    @Bind(R.id.vp_market)
    CustomViewPager mViewPager;

    TabFragmentPagerAdapter vpAdapter;
    List<Fragment> fragmentList = new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.fragment_market;
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
        fragmentList.add(new DailyCareFragment());
        vpAdapter = new TabFragmentPagerAdapter(getChildFragmentManager(),fragmentList);
        mViewPager.setAdapter(vpAdapter);
        mViewPager.setCanScroll(false);
        mViewPager.setOffscreenPageLimit(vpAdapter.getCount());
        mViewPager.setCurrentItem(0);
    }
}
