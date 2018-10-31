package com.example.hp.challengecup.utils;

import android.hardware.Camera;

public class CameraUtil {
    public static int FindFrontCamera(){
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for(int camIdx = 0;camIdx<cameraCount;camIdx++){
            Camera.getCameraInfo(camIdx,cameraInfo);
            if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                return camIdx;
            }
        }
        return -1;
    }
    public static int FindBackCamera(){
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for(int camIdx = 0;camIdx<cameraCount;camIdx++){
            Camera.getCameraInfo(camIdx,cameraInfo);
            if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                return camIdx;
            }
        }
        return -1;
    }
}
