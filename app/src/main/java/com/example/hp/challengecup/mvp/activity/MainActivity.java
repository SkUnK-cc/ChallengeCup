package com.example.hp.challengecup.mvp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.adapter.viewpager.TabFragmentPagerAdapter;
import com.example.hp.challengecup.custom.CustomViewPager;
import com.example.hp.challengecup.fragment.instance.CommunityFragment;
import com.example.hp.challengecup.fragment.instance.HomeFragment;
import com.example.hp.challengecup.fragment.instance.MarketFragment;
import com.example.hp.challengecup.fragment.instance.MyselfFragment;
import com.example.hp.challengecup.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
    public static final int INDEX_HOME = 0;
    public static final int INDEX_COMMUNITY = 1;
    public static final int INDEX_MARKET = 2;
    public static final int INDEX_MYSELF = 3;

    public static final int TAKE_PHOTO = 10;


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

    @Bind(R.id.iv_take_photo)
    ImageView takePhoto;

    TabFragmentPagerAdapter vpAdapter;
    List<Fragment> fragments = new ArrayList<>();
    SparseArray<LinearLayout> sparseArray = new SparseArray<>();
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void doBeforeContentView() {

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
        fragments.add(new MyselfFragment());
        vpAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(vpAdapter);
        mViewPager.setOffscreenPageLimit(vpAdapter.getCount());
        mViewPager.setCurrentItem(0);
        mViewPager.setCanScroll(false);


        takePhoto.setOnClickListener(this);
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
            case R.id.iv_take_photo:
                toDisposeActivity();
                break;
            default:
                break;
        }
    }

    private void toDisposeActivity() {
        Intent intent = new Intent(this,DisposePictureActivity.class);
        startActivity(intent);
    }

    private void takePhoto() {
        File outpuImage = FileUtil.createLookFile();
        if(Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(this,"com.example.hp.challengecup.fileprovider",outpuImage);
        }else{
            imageUri = Uri.fromFile(outpuImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    Intent intent = new Intent(this,DisposePictureActivity.class);
                    intent.putExtra(DisposePictureActivity.PICTURE_PATH,imageUri.getPath());
                    startActivity(intent);
                }
                break;
            default:
                super.onActivityResult(requestCode,resultCode,data);
                break;
        }
    }

    private void navSelect(int index){
        for (int i=0;i<sparseArray.size();i++) {
            int key = sparseArray.keyAt(i);
            LinearLayout linearLayout = sparseArray.get(key);
            if(key == index){
                linearLayout.getChildAt(0).setSelected(true);
                TextView textView = (TextView) linearLayout.getChildAt(1);
                textView.setSelected(true);
            }else{
                linearLayout.getChildAt(0).setSelected(false);
                TextView textView = (TextView) linearLayout.getChildAt(1);
                textView.setSelected(false);
            }
        }
    }
}
