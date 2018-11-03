package com.example.hp.challengecup.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.cropper.CropImageView;
import com.example.hp.challengecup.cropper.CropListener;
import com.example.hp.challengecup.global.Constant;
import com.example.hp.challengecup.utils.FileUtils;
import com.example.hp.challengecup.utils.ImageUtils;
import com.example.hp.challengecup.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Date         2018/6/24
 * Desc	        ${拍照界面}
 */
public class CameraActivity extends Activity implements View.OnClickListener {
    public final static String TAG = "CameraActivity";
    public final static String ORIENTATION_PORT = "port";
    public final static String ORIENTATION_LAND = "land";
    public String ORIENTATION_CURRENT = "";

    public final static int    TYPE_IDCARD_FRONT      = 1;//身份证正面
    public final static int    TYPE_IDCARD_BACK       = 2;//身份证反面
    public final static int    REQUEST_CODE           = 0X11;//请求码
    public final static int    RESULT_CODE            = 0X12;//结果码
    public final static int    PERMISSION_CODE_FIRST = 0x13;//权限请求码
    public final static String TAKE_TYPE              = "take_type";//拍摄类型标记
    public final static String IMAGE_PATH             = "image_path";//图片路径标记
    public static int      mType;//拍摄类型
    public static Activity mActivity;
    private boolean isToast = true;//是否弹吐司，为了保证for循环只弹一次

    private CropImageView mCropImageView;   //相机裁剪区域中部自定义CropImageView
    private Bitmap mCropBitmap;
    private CameraPreview mCameraPreview;   //SurfaceView
    private View mLlCameraCropContainer;   //相机裁剪区域(上中下)
    private ImageView mIvCameraCrop;        //相机裁剪区域中部img
    private ImageView mIvCameraFlash;       //闪电图标
    private View mLlCameraOption;      //操作布局
    private View mLlCameraResult;      //拍照后操作布局
    private TextView tvBottom;//下方"聚焦"字体
    private Button btMakeupOut;
    private ImageView ivMakeup;
    private ImageView ivNextStep;

    //2


    private int stepNum = 0;
    List<String> steps = new ArrayList<>();

    private OrientationEventListener listener;

    /**
     * 跳转到拍照界面
     *
     * @param activity
     * @param type     拍摄类型
     */
    public static void toCameraActivity(Activity activity, int type) {
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(TAKE_TYPE, type);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * 获取图片路径
     *
     * @param data
     * @return
     */
    public static String getImagePath(Intent data) {
        if (data != null) {
            return data.getStringExtra(IMAGE_PATH);
        }
        return "";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*动态请求需要的权限*/
        boolean checkPermissionFirst = PermissionUtils.checkPermissionFirst(this, PERMISSION_CODE_FIRST,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
        if (checkPermissionFirst) {
            init();
        }
    }

    /**
     * 处理请求权限的响应
     *
     * @param requestCode  请求码
     * @param permissions  权限数组
     * @param grantResults 请求权限结果数组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isPermissions = true;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isPermissions = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) { //用户选择了"不再询问"
                    if (isToast) {
                        Toast.makeText(this, "请手动打开该应用需要的权限", Toast.LENGTH_SHORT).show();
                        isToast = false;
                    }
                }
            }
        }
        isToast = true;
        if (isPermissions) {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "允许所有权限");
            init();
        } else {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "有权限不允许");
            finish();
        }
    }

    private void init() {
        setContentView(R.layout.activity_camera2);
        mType = getIntent().getIntExtra(TAKE_TYPE, 0);
        //2
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        initView();
        initListener();
    }

    private void initView() {
        mCameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
        mLlCameraCropContainer = findViewById(R.id.ll_camera_crop_container);
//        mIvCameraCrop = (ImageView) findViewById(R.id.iv_camera_crop);
        mIvCameraFlash = (ImageView) findViewById(R.id.iv_camera_flash);
        mLlCameraOption = findViewById(R.id.ll_camera_option);
        mLlCameraResult = findViewById(R.id.ll_camera_result);
        mCropImageView = findViewById(R.id.crop_image_view);

        ivNextStep = findViewById(R.id.iv_next_step);
        ivMakeup = findViewById(R.id.iv_makeup);
        tvBottom =  findViewById(R.id.tv_bottom);
        btMakeupOut = findViewById(R.id.bt_makeup_out);
        btMakeupOut.setOnClickListener(this);
        ivNextStep.setOnClickListener(this);
        getStepString();

        //获取屏幕最小边，设置为cameraPreview较窄的一边
        float screenMinSize = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        //根据screenMinSize，计算出cameraPreview的较宽的一边，长宽比为标准的16:9
        float maxSize = screenMinSize / 9.0f * 16.0f;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) maxSize, (int) screenMinSize);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//        mCameraPreview.setLayoutParams(layoutParams);

        float height = (int) (screenMinSize * 0.75);
        float width = (int) (height * 75.0f / 47.0f);
//        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //cropParams 裁剪区域的大小
//        LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
        mLlCameraCropContainer.setLayoutParams(containerParams);
        //mIvCameraCrop 的设置
//        LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//        mIvCameraCrop.setLayoutParams(cropParams);

        switch (mType) {
            case TYPE_IDCARD_FRONT:
//                mIvCameraCrop.setImageResource(R.drawable.camera_idcard_front);
                break;
            case TYPE_IDCARD_BACK:
//                mIvCameraCrop.setImageResource(R.drawable.camera_idcard_back);
                break;
        }

        /*增加0.5秒过渡界面，解决个别手机首次申请权限导致预览界面启动慢的问题*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCameraPreview.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 500);
    }

    private void getStepString() {
        if(steps.isEmpty()) {
            String[] strings = getResources().getStringArray(R.array.makeup_step);
            for (int i = 0; i < strings.length; i++) {
                steps.add(strings[i]);
            }
        }
        if(stepNum==steps.size()){
            stepNum = 0;
        }
        tvBottom.setText(steps.get(stepNum));
    }

    private void initListener() {
        mCameraPreview.setOnClickListener(this);
        mIvCameraFlash.setOnClickListener(this);
        findViewById(R.id.iv_camera_close).setOnClickListener(this);
        findViewById(R.id.iv_camera_take).setOnClickListener(this);
        findViewById(R.id.iv_camera_result_ok).setOnClickListener(this);
        findViewById(R.id.iv_camera_result_cancel).setOnClickListener(this);
        //2
        ivMakeup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initOrientationListener();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ivMakeup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    ivMakeup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
        initOrientationListener();
    }

    private void initOrientationListener(){
        listener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                if(orientation>340||orientation<20){
                    if(ORIENTATION_CURRENT!=ORIENTATION_PORT){
                        setOrientationPort();
                    }
                }else if(orientation>70 && orientation<110){

                }else if(orientation>160 && orientation<200){

                }else if(orientation>250 && orientation<290){
                    if(ORIENTATION_CURRENT!=ORIENTATION_LAND){
                        setOrientationLandLeft();
                    }
                }
            }
        };
        if (listener.canDetectOrientation()) {
            Log.v(TAG, "Can detect orientation");
            listener.enable();
        } else {
            Log.v(TAG, "Cannot detect orientation");
            listener.disable();
        }
    }

    private void setOrientationPort(){
//        RelativeLayout.LayoutParams lpIvMakeup = (RelativeLayout.LayoutParams) ivMakeup.getLayoutParams();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            lpIvMakeup.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        }
//        lpIvMakeup.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        lpIvMakeup.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        ivMakeup.setLayoutParams(lpIvMakeup);
        getViewInfo(ivMakeup,"iv_makeup");
        ivMakeup.setRotation(-90);
        getViewInfo(ivMakeup,"iv_makeup");
//        RelativeLayout.LayoutParams lpBtNext = new RelativeLayout.LayoutParams(DisplayUtil.dip2px(this,60),DisplayUtil.dip2px(this,55));
//        lpBtNext.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        lpBtNext.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        btNext2.setLayoutParams(lpBtNext);
//        btNext2.setRotation(-90);
//        RelativeLayout.LayoutParams lpTvIntro = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lpTvIntro.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        lpTvIntro.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        tvBottom.setLayoutParams(lpTvIntro);
//        tvBottom.setRotation(-90);

//        RelativeLayout.LayoutParams lpLinear = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lpLinear.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
////        lpLinear.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        llBottom.setLayoutParams(lpLinear);
//        llBottom.setRotation(-90);

        ORIENTATION_CURRENT = ORIENTATION_PORT;
    }

    private void setOrientationLandLeft() {
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivMakeup.getLayoutParams();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        }
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        ivMakeup.setLayoutParams(layoutParams);
        getViewInfo(ivMakeup,"iv_makeup");
        ivMakeup.setRotation(0);
        getViewInfo(ivMakeup,"iv_makeup");
//        RelativeLayout.LayoutParams lpBtNext = new RelativeLayout.LayoutParams(DisplayUtil.dip2px(this,60),DisplayUtil.dip2px(this,55));
//        lpBtNext.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        lpBtNext.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        btNext2.setLayoutParams(lpBtNext);
//        btNext2.setRotation(0);
//        RelativeLayout.LayoutParams lpTvIntro = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lpTvIntro.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        lpTvIntro.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        tvBottom.setLayoutParams(lpTvIntro);
//        tvBottom.setRotation(0);


        ORIENTATION_CURRENT = ORIENTATION_LAND;
    }
    private void getViewInfo(View v,String name){
        Log.e(name, "left  = "+v.getLeft());
        Log.e(name, "top   = "+v.getTop());
        Log.e(name, "right = "+v.getRight());
        Log.e(name, "bottom= "+v.getBottom());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.camera_preview) {
            mCameraPreview.focus();
        } else if (id == R.id.iv_camera_close) {
            finish();
        } else if (id == R.id.iv_camera_take) {
            takePhoto();
        } else if (id == R.id.iv_camera_flash) {
            boolean isFlashOn = mCameraPreview.switchFlashLight();
            mIvCameraFlash.setImageResource(isFlashOn ? R.drawable.camera_flash_on : R.drawable.camera_flash_off);
        } else if (id == R.id.iv_camera_result_ok) {
            confirm();
        } else if (id == R.id.iv_camera_result_cancel) {
            mCameraPreview.setEnabled(true);
            mCameraPreview.startPreview();
            mIvCameraFlash.setImageResource(R.drawable.camera_flash_off);
            setTakePhotoLayout();
        }else if(id == R.id.bt_makeup_out){
            finish();
        }else if(id == R.id.iv_next_step){
            stepNum++;
            getStepString();
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        mCameraPreview.setEnabled(false);
        mCameraPreview.takePhoto(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(final byte[] data, Camera camera) {
                camera.stopPreview();
                //子线程处理图片，防止ANR
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                        Log.e(TAG, "bitmap:"+bitmap.getWidth()+";"+bitmap.getHeight());
                        /*计算裁剪位置，占预览图的百分比形式表示*/
                        /**
                         * left ： 中间（上中下）部分的左边到perview左边的间隔占preview的百分比
                         */
//                        float left, top, right, bottom;
//                        left = ((float) mLlCameraCropContainer.getLeft() - (float) mCameraPreview.getLeft()) / (float) mCameraPreview.getWidth();
//                        top = (float) mIvCameraCrop.getTop() / (float) mCameraPreview.getHeight();
//                        right = (float) mLlCameraCropContainer.getRight() / (float) mCameraPreview.getWidth();
//                        bottom = (float) mIvCameraCrop.getBottom() / (float) mCameraPreview.getHeight();
//                        Log.e(TAG, "run: left="+left);
//                        Log.e(TAG, "run: top="+top);
//                        Log.e(TAG, "run: right="+right);
//                        Log.e(TAG, "run: bottom="+bottom);
//                        Log.e(TAG, "run: zhongbu image="+mIvCameraCrop.getBottom());
//                        Log.e(TAG, "run: perview height="+mCameraPreview.getHeight());

                        /*自动裁剪*/
//                        mCropBitmap = Bitmap.createBitmap(bitmap,
//                                (int) (left * (float) bitmap.getWidth()),
//                                (int) (top * (float) bitmap.getHeight()),
//                                (int) ((right - left) * (float) bitmap.getWidth()),
//                                (int) ((bottom - top) * (float) bitmap.getHeight()));
                        mCropBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight());

                        /*手动裁剪*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //将裁剪区域设置成与扫描框一样大
//                                mCropImageView.setLayoutParams(new LinearLayout.LayoutParams(mIvCameraCrop.getWidth(), mIvCameraCrop.getHeight()));
//                                mIvCameraCrop.setVisibility(View.GONE);
//                                mCropImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                                Log.e(TAG, "screen width="+getResources().getDisplayMetrics().widthPixels); //854
                                Log.e(TAG, "screen width="+getResources().getDisplayMetrics().heightPixels);//480
                                Log.e(TAG, "crop width="+mCropImageView.getMeasuredWidth());
                                Log.e(TAG, "crop width="+mCropImageView.getMeasuredHeight());
                                setCropLayout();
                                mCropImageView.setImageBitmap(mCropBitmap);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    /**
     * 设置裁剪布局
     */
    private void setCropLayout() {
//        mIvCameraCrop.setVisibility(View.GONE);
        mCameraPreview.setVisibility(View.GONE);
        mLlCameraOption.setVisibility(View.GONE);
        mCropImageView.setVisibility(View.VISIBLE);
        mLlCameraResult.setVisibility(View.VISIBLE);
        tvBottom.setText("");
    }

    /**
     * 设置拍照布局
     */
    private void setTakePhotoLayout() {
//        mIvCameraCrop.setVisibility(View.VISIBLE);
        mCameraPreview.setVisibility(View.VISIBLE);
        mLlCameraOption.setVisibility(View.VISIBLE);
        mCropImageView.setVisibility(View.GONE);
        mLlCameraResult.setVisibility(View.GONE);
        tvBottom.setText(getString(R.string.touch_to_focus));

        mCameraPreview.focus();
    }

    /**
     * 点击确认，返回图片路径
     */
    private void confirm() {
        /*裁剪图片*/
        mCropImageView.crop(new CropListener() {
            @Override
            public void onFinish(Bitmap bitmap) {
                if(bitmap == null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.crop_fail), Toast.LENGTH_SHORT).show();
                    finish();
                }

                /*保存图片到sdcard并返回图片路径*/
                if (FileUtils.createOrExistsDir(Constant.DIR_ROOT)) {
                    StringBuffer buffer = new StringBuffer();
                    String imagePath = "";
                    if (mType == TYPE_IDCARD_FRONT) {
                        imagePath = buffer.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("idCardFrontCrop.jpg").toString();
                    } else if (mType == TYPE_IDCARD_BACK) {
                        imagePath = buffer.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("idCardBackCrop.jpg").toString();
                    }

                    if (ImageUtils.save(bitmap, imagePath, Bitmap.CompressFormat.JPEG)) {
                        Intent intent = new Intent();
                        intent.putExtra(CameraActivity.IMAGE_PATH, imagePath);
                        setResult(RESULT_CODE, intent);
                        finish();
                    }
                }
            }
        }, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCameraPreview != null) {
            mCameraPreview.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCameraPreview != null) {
            mCameraPreview.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //2
        listener.disable();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged: changed");
    }
}