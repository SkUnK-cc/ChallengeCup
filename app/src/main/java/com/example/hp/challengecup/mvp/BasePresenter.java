package com.example.hp.challengecup.mvp;

public class BasePresenter<V extends IBaseView> {
    protected V mView;

    public BasePresenter(){

    }

    public void attach(V view){
        this.mView = view;
    }

    public void detach(){
        this.mView = null;
    }
}
