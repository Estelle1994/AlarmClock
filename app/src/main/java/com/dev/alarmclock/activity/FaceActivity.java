package com.dev.alarmclock.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dev.alarmclock.R;

import com.dev.alarmclock.entity.FaceEntity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by ${Estelle} on 2018/6/20.
 */

public class FaceActivity extends AppCompatActivity implements View.OnClickListener {
    static final String tag = "yan";
    private ImageView imgView;
    private ImageView cropImg;
    private Button detectFaceBtn;
    private Button cropFace;
    private TextView faceInfomation;
    FaceDetector faceDetector = null;
    FaceDetector.Face[] face;
    final int N_MAX = 2;
    ProgressBar progressBar = null;
    Bitmap srcImg = null;
    Bitmap srcFace = null;
    private int ret;
    private String msg;
    private int gender;
    private int age;
    private int expression;
    private int beauty;
    private int glass;
    private String info1;
    private String genderString, expressionString, glassString;
    private int x, y, width, height;
    private static final int CROP_PICTURE = 2;

    Thread checkFaceThread = new Thread() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            //网络请求显示性别，年龄等
            /*sendVolley(new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    String info = jiexiJson(result);
                    Message msg1 = new Message();
                    msg1.what = 3;
                    msg1.obj = info;
                    mainHandler.sendMessage(msg1);
                }
            });*/
            Bitmap faceBitmap = detectFace(x, y, width, height);//发现人脸
            mainHandler.sendEmptyMessage(2);//progressBar和按钮消失
            Message m = new Message();
            m.what = 0;
            m.obj = faceBitmap;
            mainHandler.sendMessage(m);//imgView显示人脸检测结果


        }

    };

    Handler mainHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            //super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Bitmap b = (Bitmap) msg.obj;
                    imgView.setImageBitmap(b);
                    Toast.makeText(FaceActivity.this, "检测完毕", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    showProcessBar();
                    break;
                case 2:
                    progressBar.setVisibility(View.GONE);
                    /*detectFaceBtn.setClickable(false);*/
                    detectFaceBtn.setVisibility(View.GONE);
                    break;
                case 3:
                    String face = (String) msg.obj;
                    faceInfomation.setText(face);
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        initView();
        initFaceDetect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initView() {
        imgView = (ImageView) findViewById(R.id.imgview);
        cropImg = (ImageView) findViewById(R.id.iv_crop);
        detectFaceBtn = (Button) findViewById(R.id.btn_detect_face);
        cropFace = (Button) findViewById(R.id.btn_crop_face);
        faceInfomation = (TextView) findViewById(R.id.tv_face_info);
        ViewGroup.LayoutParams params = imgView.getLayoutParams();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        //      int h = dm.heightPixels;

        srcImg = BitmapFactory.decodeResource(getResources(), R.drawable.kunlong);
        /*srcImg = convertToBitmap("/QQfile_rect/p2.png",150,150);*/
        int h = srcImg.getHeight();
        int w = srcImg.getWidth();
        float r = (float) h / (float) w;
        params.width = w_screen;
        params.height = (int) (params.width * r);
        imgView.setLayoutParams(params);
        imgView.setImageBitmap(srcImg);

        detectFaceBtn.setOnClickListener(this);
        cropFace.setOnClickListener(this);

    }

    public Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    public void initFaceDetect() {
        this.srcFace = srcImg.copy(Bitmap.Config.RGB_565, true);
        int w = srcFace.getWidth();
        int h = srcFace.getHeight();
        Log.i(tag, "待检测图像: w = " + w + "h = " + h);
        faceDetector = new FaceDetector(w, h, N_MAX);
        face = new FaceDetector.Face[N_MAX];
    }

    public boolean checkFace(Rect rect) {
        int w = rect.width();
        int h = rect.height();
        int s = w * h;
        Log.i(tag, "人脸 宽w = " + w + "高h = " + h + "人脸面积 s = " + s);
        if (s < 10000) {
            Log.i(tag, "无效人脸，舍弃.");
            return false;
        } else {
            Log.i(tag, "有效人脸，保存.");
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_detect_face:
                mainHandler.sendEmptyMessage(1);
                checkFaceThread.start();
                break;
            case R.id.btn_crop_face:
                File tempFile = new File ("/sdcard/QQfile_rect");
                Intent intent1 = new Intent("com.android.camera.action.CROP");
                intent1.setDataAndType(Uri.fromFile(new File("/sdcard/QQfile_rect/p2.png")), "image/*");
                intent1.putExtra("crop", "true");
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                intent1.putExtra("aspectX", 1);
                intent1.putExtra("aspectY", 1);
                intent1.putExtra("outputFormat", Bitmap.CompressFormat.JPEG);
                intent1.putExtra("outputX", 720);
                intent1.putExtra("outputY", 720);
                intent1.putExtra("scale", true);
                intent1.putExtra("scaleUpIfNeeded", true);
                intent1.putExtra("return-data", false);
                startActivityForResult(intent1, CROP_PICTURE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode == CROP_PICTURE) {
//            try {
//                Bitmap headShot = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropImageUri));
//                imgView.setImageBitmap(headShot);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
        }
    }

    public Bitmap detectFace(int x, int y, int width, int height) {
        //      Drawable d = getResources().getDrawable(R.drawable.face_2);
        //      Log.i(tag, "Drawable尺寸 w = " + d.getIntrinsicWidth() + "h = " + d.getIntrinsicHeight());
        //      BitmapDrawable bd = (BitmapDrawable)d;
        //      Bitmap srcFace = bd.getBitmap();

        int nFace = faceDetector.findFaces(srcFace, face);
        Log.i(tag, "检测到人脸：n = " + nFace);
        for (int i = 0; i < nFace; i++) {
            FaceDetector.Face f = face[i];
            PointF midPoint = new PointF();
            float dis = f.eyesDistance();
            f.getMidPoint(midPoint);
            int dd = (int) (dis);
            Rect faceRect = new Rect((int) (midPoint.x - dd), (int) (midPoint.y - dd), (int) (midPoint.x + dd), (int) (midPoint.y + dd));
            /*Rect faceRect = new Rect(x, y, x + width , y + height);*/
            if (checkFace(faceRect)) {
                Canvas canvas = new Canvas(srcFace);
                Paint p = new Paint();
                p.setAntiAlias(true);
                p.setStrokeWidth(8);
                p.setStyle(Paint.Style.STROKE);
                p.setColor(Color.GREEN);
                /*canvas.drawCircle(eyeLeft.x, eyeLeft.y, 20, p);
                canvas.drawCircle(eyeRight.x, eyeRight.y, 20, p);*/
                canvas.drawRect(faceRect, p);
            }

        }
        /*ImageUtil.saveJpeg(srcFace);*/
        Log.i(tag, "保存完毕");

        //将绘制完成后的faceBitmap返回
        return srcFace;

    }

    public void showProcessBar() {
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.layout_main);
        progressBar = new ProgressBar(FaceActivity.this, null, android.R.attr.progressBarStyleLargeInverse); //ViewGroup.LayoutParams.WRAP_CONTENT
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        progressBar.setVisibility(View.VISIBLE);
        //progressBar.setLayoutParams(params);
        mainLayout.addView(progressBar, params);

    }

    public String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName), "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String jiexiJson(String json) {
        if (json != null) {
            FaceEntity Md = new Gson().fromJson(json, FaceEntity.class);
            ret = Md.getRet();
            msg = Md.getMsg();
            if (ret == 0) {
                FaceEntity.DataBean db = Md.getData();
                Log.i("FFFFDDDD", "" + db);
                if (db != null) {
                    List<FaceEntity.DataBean.FaceListBean> flb = db.getFace_list();
                    for (FaceEntity.DataBean.FaceListBean faceList : flb) {
                        gender = faceList.getGender();
                        age = faceList.getAge();
                        expression = faceList.getExpression();
                        beauty = faceList.getBeauty();
                        glass = faceList.getGlass();
                        x = faceList.getX();
                        y = faceList.getY();
                        width = faceList.getWidth();
                        height = faceList.getHeight();
                    }
                    //性别
                    if (gender < 50 && gender >= 0) {
                        genderString = "女";
                    } else if (gender >= 50 && gender <= 100) {
                        genderString = "男";
                    }
                    //表情
                    if (expression <= 0) {
                        expressionString = "愁眉苦脸";
                    } else if (expression < 20 && expression >= 0) {
                        expressionString = "莞尔一笑";
                    } else if (expression < 40 && expression >= 20) {
                        expressionString = "笑逐颜开";
                    } else if (expression < 60 && expression >= 40) {
                        expressionString = "喜上眉梢";
                    } else if (expression < 80 && expression >= 60) {
                        expressionString = "眉开眼笑";
                    } else if (expression < 100 && expression >= 80) {
                        expressionString = "心花怒放";
                    } else if (expression >= 100) {
                        expressionString = "捧腹大笑";
                    }
                    //是否戴眼镜
                    if (glass == 0) {
                        glassString = "否";
                    } else if (glass == 1) {
                        glassString = "是";
                    }
                    info1 = "性别：" + genderString + "\n年龄：" + age + "\n表情：" + expressionString + "\n魅力：" + beauty
                            + "\n是否戴眼镜:" + glassString;
                }
            } else {
                Toast.makeText(FaceActivity.this, "出错信息：" + msg, Toast.LENGTH_SHORT).show();
                info1 = null;
            }
        }
        return info1;

    }


}
