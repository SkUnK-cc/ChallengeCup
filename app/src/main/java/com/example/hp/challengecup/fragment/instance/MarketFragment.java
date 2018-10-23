package com.example.hp.challengecup.fragment.instance;

import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.adapter.viewpager.TabFragmentPagerAdapter;
import com.example.hp.challengecup.custom.CustomViewPager;
import com.example.hp.challengecup.fragment.instance.fragment_market.DailyCareFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MarketFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.vp_market)
    CustomViewPager mViewPager;
    @Bind(R.id.market_nav_dailycare)
    TextView tvDailyCare;
    @Bind(R.id.market_nav_powderybottom)
    TextView tvPowderyBottom;
    @Bind(R.id.market_nav_blockdefect)
    TextView tvBlockdefect;
    @Bind(R.id.market_nav_labialministry)
    TextView tvLabialministry;
    @Bind(R.id.market_nav_eye)
    TextView tvEye;
    @Bind(R.id.market_nav_tools)
    TextView tvTools;

    TabFragmentPagerAdapter vpAdapter;
    List<Fragment> fragmentList = new ArrayList<>();

    SparseArray<TextView> sparseArray = new SparseArray<>();
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

        tvBlockdefect.setOnClickListener(this);
        tvDailyCare.setOnClickListener(this);
        tvEye.setOnClickListener(this);
        tvLabialministry.setOnClickListener(this);
        tvTools.setOnClickListener(this);
        tvPowderyBottom.setOnClickListener(this);
        sparseArray.put(0,tvDailyCare);
        sparseArray.put(1,tvPowderyBottom);
        sparseArray.put(2,tvBlockdefect);
        sparseArray.put(3,tvLabialministry);
        sparseArray.put(4,tvEye);
        sparseArray.put(5,tvTools);
        sparseArray.get(0).setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.market_nav_dailycare:
                navSelect(0);
//                llHome.getChildAt(0).setSelected(true);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.market_nav_powderybottom:
                navSelect(1);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.market_nav_blockdefect:
                navSelect(2);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.market_nav_labialministry:
                navSelect(3);
                mViewPager.setCurrentItem(3);
                break;
            case R.id.market_nav_eye:
                navSelect(4);
                mViewPager.setCurrentItem(3);
                break;
            case R.id.market_nav_tools:
                navSelect(5);
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
            TextView textView = sparseArray.get(key);
            if(key == index){
//                Log.e(TAG, "navSelect: select = " + i);
                textView.setSelected(true);
            }else{
//                Log.e(TAG, "navSelect: unselect = " + i);
                textView.setSelected(false);
            }
        }
    }
}
