package com.example.hp.challengecup.fragment.instance;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.api.cloudmusic.User;
import com.example.hp.challengecup.custom.MyCircleImageView;
import com.example.hp.challengecup.mvp.activity.LoginActivity;
import com.example.myview.imgview.NumImageView;

import butterknife.Bind;

public class MyselfFragment extends BaseFragment implements View.OnClickListener {
    public static final int Login_REQEUSTCODE = 6;
    @Bind(R.id.img_myself_head)
    MyCircleImageView ivHead;
    @Bind(R.id.tv_username)
    TextView tvUsername;

    @Bind(R.id.iv_waitToPay)
    NumImageView ivWaitToPay;

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
        ivWaitToPay.setTextSize(30);
        ivWaitToPay.showPoint("99");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_myself_head:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent,Login_REQEUSTCODE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("Login", "onActivityResult: "+resultCode);
        switch (resultCode){
            case LoginActivity.LOGIN_SUCCESS:
                User user= data.getParcelableExtra("user");
                tvUsername.setText(user.getUsername());
                break;
            default:
                break;
        }

    }
}
