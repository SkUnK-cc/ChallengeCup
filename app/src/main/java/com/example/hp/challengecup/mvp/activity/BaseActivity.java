package com.example.hp.challengecup.mvp.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hp.challengecup.mvp.BasePresenter;
import com.example.hp.challengecup.mvp.IBaseView;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView{
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeContentView();
        setContentView(getContentView());
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT >=21){
            getWindow().setStatusBarColor(Color.parseColor("#c6cfd8"));
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initPresenter();
        if(mPresenter!=null){
            mPresenter.attach(this);
        }
        initView();
        initListener();
        initData();
    }

    protected abstract void doBeforeContentView();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract int getContentView();
    protected abstract void initPresenter();
}
