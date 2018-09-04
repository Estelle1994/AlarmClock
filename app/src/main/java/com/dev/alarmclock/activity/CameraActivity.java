package com.dev.alarmclock.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;


import com.dev.alarmclock.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import static android.R.attr.orientation;

/**
 * Created by andayoung on 16-3-11.
 */
public class CameraActivity extends Activity {
    private ImageButton btnTakePicture = null;//拍照
    private ImageButton btnAutoFocus = null;//自动对焦
    private ImageButton btnCheckCamera = null;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera = null;
    private int cameraPosition = 1;
    private boolean safeToTakePicture = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
    }


    @Override
    public void onStart() {
        super.onStart();
        //如果硬件有拍照的功能
        if (this.checkCameraHardware(this) && (camera == null)) {
            camera = getCamera();
            if (surfaceHolder != null) {
                setStartPreview(camera, surfaceHolder);//启动预览
                safeToTakePicture = true;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //延迟5秒自动拍照
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (camera != null)
                    camera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            // data为完整数据
                            File file = new File("/sdcard/photo.png");
                            // 使用流进行读写
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            try {
                                if (fos == null) {
                                    fos = new FileOutputStream(file);
                                }
                                fos.write(data);
                                // 关闭流
                                fos.close();
                                // 查看图片
                                Intent intent = new Intent();
                                // 传递路径
                                intent.putExtra("path", file.getAbsolutePath());
                                setResult(0, intent);
                                finish();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });
            }
        }, 30 * 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void initView() {
        btnTakePicture = (ImageButton) findViewById(R.id.btnTakePicture);
        btnAutoFocus = (ImageButton) findViewById(R.id.btnAutoFocus);
        btnCheckCamera = (ImageButton) findViewById(R.id.btnCheckCamera);
        btnTakePicture.setOnClickListener(onClickListener);
        btnAutoFocus.setOnClickListener(onClickListener);
        btnCheckCamera.setOnClickListener(onClickListener);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceCallback());
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.setFixedSize(320, 240);
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnTakePicture) {
                /*if (camera != null)
                    camera.takePicture(null, null, new TakePictureCallback()); // 拍照
                Log.e("2333", "takePicture");*/

                if (camera == null) {
                    camera = getCamera();
                }
                if (safeToTakePicture) {

                    camera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            // data为完整数据
                            File file = new File("/sdcard/photo.png");
                            // 使用流进行读写
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            try {
                                if (fos == null) {
                                    fos = new FileOutputStream(file);
                                }
                                fos.write(data);
                                // 关闭流
                                fos.close();
                                // 查看图片
                                Intent intent = new Intent();
                                // 传递路径
                                intent.putExtra("path", file.getAbsolutePath());
                                setResult(0, intent);
                                finish();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });
                    safeToTakePicture = false;
                }
            } else if (v == btnAutoFocus) {
                if (camera != null)
                    camera.autoFocus(null);
            } else if (v == btnCheckCamera) {
                int cameraCount = 0;
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                cameraCount = Camera.getNumberOfCameras();
                for (int i = 0; i < cameraCount; i++) {
                    Camera.getCameraInfo(i, cameraInfo);
                    if (cameraPosition == 1) {
                        Log.e("AlphaGo", "cpBefore=" + cameraPosition);
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                            releaseCamera();
                            camera = Camera.open(i);
                            setStartPreview(camera, surfaceHolder);
                            safeToTakePicture = true;
                            cameraPosition = 0;
                            Log.e("AlphaGo", "cpAfter=" + cameraPosition);
                            break;
                        }
                    } else {
                        Log.e("AlphaGo", "cpBefore=" + cameraPosition);
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                            releaseCamera();
                            camera = Camera.open(i);
                            setStartPreview(camera, surfaceHolder);
                            safeToTakePicture = true;
                            cameraPosition = 1;
                            Log.e("AlphaGo", "cpAfter=" + cameraPosition);
                            break;
                        }
                    }
                }
            }
        }
    };

    private final class SurfaceCallback implements SurfaceHolder.Callback {
        private boolean preview;

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            setStartPreview(camera, holder);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                int pictureWidth = 0;
                int pictureHeight = 0;
                Camera.Parameters parameters = camera.getParameters();
                List<Camera.Size> sizeList = parameters.getSupportedPictureSizes();
                if (sizeList.size() >= 1) {
                    Iterator<Camera.Size> itor = sizeList.iterator();
                    while (itor.hasNext()) {
                        Camera.Size cur = itor.next();
                        if (cur.width >= pictureWidth && cur.height >= pictureHeight) {
                            pictureWidth = cur.width;
                            pictureHeight = cur.height;
                            break;
                        }
                    }
                }
                parameters.setPictureFormat(PixelFormat.JPEG);
                camera.getParameters().setJpegQuality(85);
                parameters.setPictureSize(pictureWidth, pictureHeight);
                camera.setParameters(parameters);
                setStartPreview(camera, surfaceHolder);
                preview = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            releaseCamera();
        }
    }

    private final class TakePictureCallback implements Camera.PictureCallback {
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            //设置缩放
            matrix.postScale(0.5f, 0.5f);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            String pathUrl = Environment.getExternalStorageDirectory().getPath() + "/WHAT/";
            File fp = new File(pathUrl);
            fp.mkdirs();
            File file = new File(fp.getPath(), System.currentTimeMillis() + ".jpg");
            try {
                Log.e("2333", "file=" + file);
                FileOutputStream outStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.close();
                camera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private void setStartPreview(Camera camera, SurfaceHolder holder) {
        //如果不是竖屏
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            //设置显示方向
            camera.setDisplayOrientation(90);
        } else {
            if (null == camera)
                return;
            Method method;
            try {
                //通过反射机制获取相应的方法
                method = camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
                if (null != method) {
                    method.invoke(camera, new Object[]{orientation});
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            //设置预览显示
            camera.setPreviewDisplay(holder);
        } catch (IOException exception) {
            Log.d("2333", "Error starting camera preview: " + exception.getMessage());
            camera.release();
            camera = null;
        }
        //启动预览
        camera.startPreview();

    }

    private Camera getCamera() {
        try {
            camera = Camera.open(0);
        } catch (Exception e) {
            camera = null;
            Log.e("2333", "Camera is not available (in use or does not exist)");
        }
        return camera;
    }
}
