package com.example.bangpt;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exercise1Activity extends AppCompatActivity implements SurfaceHolder.Callback {

    private Button btn_record;
    private SurfaceView surfaceView;
    private Camera camera;
    private MediaRecorder mediaRecorder;
    private SurfaceHolder surfaceHolder;
    private boolean recording = false;

    private String TAG = "Exercise1Activity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1);

        TedPermission.create() //권한을 얻기 위한 코드이다.
                .setPermissionListener(permission)
                .setRationaleMessage("녹화를 위하여 권한을 허용해주세요.")
                .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .check();

        btn_record = (Button)findViewById(R.id.btn_record);
        btn_record.setOnClickListener(v -> {
            if (recording) { //녹화 중일 때 버튼을 누르면 녹화가 종료하도록 한다.
                mediaRecorder.stop();
                mediaRecorder.release();
                camera.lock();
                recording = false;
                btn_record.setText("녹화 시작");
            } else { //녹화 중이 아닐 때 버튼을 누르면 녹화가 시작하게 한다.
                runOnUiThread(new Runnable() { //녹화를 하는 것은 백그라운드로 하는 것이 좋다.
                    @Override
                    public void run() {
                        Toast.makeText(Exercise1Activity.this, "녹화가 시작되었습니다.", Toast.LENGTH_SHORT).show();
                        try {
                            mediaRecorder = new MediaRecorder();
                            camera.unlock();
                            mediaRecorder.setCamera(camera);
                            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                            mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));
                            mediaRecorder.setOrientationHint(270);
                            mediaRecorder.setOutputFile("/storage/emulated/0/Download/test.mp4");
                            mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                            recording = true;
                            btn_record.setText("녹화 종료");
                        } catch (IOException e) {
                            Log.e(TAG, "Error in 79" + e.getMessage());
                            e.printStackTrace();
                            mediaRecorder.release();
                        }
                    }

                });
            }
        });
    }

    PermissionListener permission = new PermissionListener() {
        @Override
        public void onPermissionGranted() { //권한을 허용받았을 때 camera와 surfaceView에 대한 설정을 해준다.

            int numberOfCameras = Camera.getNumberOfCameras();
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.getCameraInfo(i, cameraInfo);

                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    camera = Camera.open(i); // 전면 카메라를 엽니다
                    break;
                }
            }
            if (camera == null) {
                camera = Camera.open(); // 전면 카메라를 사용할 수 없을 경우 기본 카메라(후면 카메라)를 엽니다
            }
            //camera = Camera.open();
            camera.setDisplayOrientation(90);
            surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(Exercise1Activity.this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            Toast.makeText(Exercise1Activity.this, "권한 허가", Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onPermissionDenied(List<String> deniedPermissions) { //권한이 거부됐을 때 이벤트를 설정할 수 있다.
            Toast.makeText(Exercise1Activity.this, "권한 거부", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
    }

    private void refreshCamera(Camera camera) {
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        try {
            camera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setCamera(camera);
    }

    private void setCamera(Camera cam) {
        camera = cam;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        refreshCamera(camera);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}