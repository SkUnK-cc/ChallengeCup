package com.example.hp.challengecup.mvp.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.challengecup.R;
import com.example.hp.challengecup.camera.CameraActivity;
import com.example.hp.challengecup.utils.FileUploadUtil;
import com.example.hp.challengecup.utils.FileUtil;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DisposePictureActivity extends BaseActivity implements View.OnClickListener {
    public static final String PICTURE_PATH = "picture";
    public static final int TAKE_PHOTO = 1;
    public static final int OPEN_ALBUM = 2;
    public static final int PERMISSION_REQUEST_CODE = 3;

    @Bind(R.id.show_picture)
    ImageView ivShowPicture;
    @Bind(R.id.bt_open_camera)
    Button btOpenCamera;
    @Bind(R.id.bt_open_album)
    Button btOpenAlbum;
    @Bind(R.id.bt_upload)
    Button btUpload;
    @Bind(R.id.bt_makeup)
    Button btMakeup;

    private Uri imageUri;

    @Override
    protected int getContentView() {
        return R.layout.activity_dispose;
    }

    @Override
    protected void doBeforeContentView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
       btOpenCamera.setOnClickListener(this);
       btOpenAlbum.setOnClickListener(this);
       btMakeup.setOnClickListener(this);
       btUpload.setOnClickListener(this);
    }

    private void disposeIntent(){
        Intent intent = getIntent();
        String picture_path = intent.getStringExtra(PICTURE_PATH);
        if(picture_path.equals("") || picture_path == null)return;
        Bitmap bitmap = BitmapFactory.decodeFile(picture_path);
        ivShowPicture.setImageBitmap(bitmap);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_open_camera:
                takePhoto();
                break;
            case R.id.bt_open_album:
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
                }else {
                    openAlbum();
                }
                break;
            case R.id.bt_makeup:
                CameraActivity.toCameraActivity(this, CameraActivity.TYPE_IDCARD_FRONT);
//                CameraActivity2.toCameraActivity(this,CameraActivity2.TYPE_IDCARD_FRONT);
                break;
            case R.id.bt_upload:
//                uploadImg();
//                registerTest();
                matchActivity();
            default:
                break;
        }
    }

    private void matchActivity() {
        Intent intent = new Intent(this,MatchActivity.class);
        startActivity(intent);
    }

    private void uploadImg() {
        String url = "http://192.168.1.104:8000/uphomeimg";
//        Resources resource = getResources();
//        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"
//                +resource.getResourcePackageName(R.drawable.home_recycler_2)+"/"
//                +resource.getResourceTypeName(R.drawable.home_recycler_2)+"/"
//                +resource.getResourceEntryName(R.drawable.home_recycler_2));
//        if(imageUri!=null){
//            Toast.makeText(this, "请选择照片", Toast.LENGTH_SHORT).show();
//            return ;
//        }
//        path = imageUri.getPath();
        //159874xzh
        String path = imageUri.getPath();
        FileUploadUtil.imageUpload(url,path,this);
    }

    private void registerTest() {
        String url = "http://192.168.1.104:8000/loginApp?username=root&password=159874xzh";
        OkHttpClient mClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Register", "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("Register", "onResponse: "+response.body().string());
            }
        });
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,OPEN_ALBUM);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this,"You denied the permission!",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(imageUri != null){
                    Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath());
                    ivShowPicture.setImageBitmap(bitmap);
                }
                break;
            case OPEN_ALBUM:
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            case CameraActivity.REQUEST_CODE:
                if(resultCode == CameraActivity.RESULT_CODE){

                }
                break;
            default:
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ivShowPicture.setImageBitmap(bitmap);
        imageUri = Uri.fromFile(new File(imagePath));
    }

    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(externalContentUri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
