package com.example.hp.challengecup.fragment.instance;

import android.content.Intent;
import android.view.View;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.custom.MyCircleImageView;
import com.example.hp.challengecup.mvp.activity.RegisterActivity;

import butterknife.Bind;

public class MyselfFragment extends BaseFragment implements View.OnClickListener {
    public static final int REGISTER_REQEUSTCODE = 6;
    @Bind(R.id.img_myself_head)
    MyCircleImageView ivHead;

    @Override
    protected int getContentView() {
        return R.layout.fragment_myself;
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
        ivHead.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_myself_head:
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivityForResult(intent,REGISTER_REQEUSTCODE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
