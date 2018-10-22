package com.example.hp.challengecup.fragment.instance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.challengecup.mvp.BasePresenter;
import com.example.hp.challengecup.mvp.IBaseView;

import butterknife.ButterKnife;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {

    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(),container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        if(mPresenter!=null){
            mPresenter.attach(this);
        }
        initView();
        initListener();
        initData();
    }

    protected abstract int getContentView();

    protected abstract void initPresenter();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initView();
}
