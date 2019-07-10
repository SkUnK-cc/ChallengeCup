package com.example.hp.challengecup.mvp.activity;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hp.challengecup.R;
import com.example.hp.challengecup.utils.DensityUtil;

import butterknife.Bind;

public class RegisterTwoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.iv_registertwo_back)
    ImageView ivBack;
    @Bind(R.id.iv_registertwo_head)
    ImageView ivHead;

    @Override
    protected int getContentView() {
        return R.layout.activity_register_two;
    }

    @Override
    protected void doBeforeContentView() {
        DensityUtil.setCustomDensity(this,getApplication(),375);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
//        Glide.with(this)
//                .asBitmap()
//                .load(R.drawable.comm_recy_hori_3)
//                .into(new BitmapImageViewTarget(ivHead){
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        super.setResource(resource);
//                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),resource);
//                        roundedBitmapDrawable.setCircular(true);
//                        ivHead.setImageDrawable(roundedBitmapDrawable);
//                        Bitmap bitmap = Bitmap.createBitmap(900,900,Bitmap.Config.ARGB_8888);
//                        bitmap.eraseColor(Color.parseColor("#8a8a8a"));
//                        RoundedBitmapDrawable roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
//                        roundedBitmapDrawable1.setCircular(true);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                            ivHead.setBackground(roundedBitmapDrawable1);
//                        }
//                    }
//                });
        RequestOptions options = RequestOptions.circleCropTransform();
        Glide.with(this)
                .asBitmap()
                .load(R.drawable.comm_recy_hori_3)
                .apply(options)
                .into(ivHead);
        ivBack.setOnClickListener(this);
        ivHead.setOnClickListener(this);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_registertwo_back:
                finish();
                break;
            case R.id.bt_register_finish:
                finish();
                break;
            default:
                break;
        }
    }
}
