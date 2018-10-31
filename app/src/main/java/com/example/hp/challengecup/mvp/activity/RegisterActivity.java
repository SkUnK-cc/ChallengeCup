package com.example.hp.challengecup.mvp.activity;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.utils.DensityUtil;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void doBeforeContentView() {
        DensityUtil.setCustomDensity(this,getApplication());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initPresenter() {

    }
}
