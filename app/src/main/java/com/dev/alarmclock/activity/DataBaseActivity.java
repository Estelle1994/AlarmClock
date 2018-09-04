package com.dev.alarmclock.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.RestrictionsManager;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.alarmclock.R;
import com.dev.alarmclock.adapter.FaceInfoAdapter;
import com.dev.alarmclock.adapter.FaceRecordAdapter;
import com.dev.alarmclock.adapter.NameAdapter;
import com.dev.alarmclock.entity.FaceRecordEntity;
import com.dev.alarmclock.entity.NameEntity;
import com.dev.alarmclock.util.MyDatabaseHelper;
import com.dev.alarmclock.util.RefreshRecycleViewManager;
import com.dev.alarmclock.view.RefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Estelle} on 2018/7/4.
 */

public class DataBaseActivity extends AppCompatActivity{
    private EditText nameEt,authorEt,pagesEt,priceEt;
    private ImageView photoImg,arrow;
    private RefreshRecycleView contentLv;
    private ListView nameList;
    private Button addBtn,queryBtn;
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private FaceRecordAdapter adapter;
    private List<FaceRecordEntity> list = new ArrayList<>();
    private RefreshRecycleViewManager<FaceRecordEntity, BaseViewHolder,
                FaceRecordAdapter> mFaceManager;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private boolean isClick = true;
    private NameAdapter nameAdapter;
    private List<NameEntity> namelist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,1);
        db = dbHelper.getWritableDatabase();
        sp = getSharedPreferences("userName",0);
        editor = sp.edit();
        initView();
        initContentView();
        //initData();
    }

    private void initData() {
        list = queryData();
        adapter = new FaceRecordAdapter(list);
        contentLv.setAdapter(adapter);
        contentLv.setRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }
        });

    }

    private void initView() {
        nameEt = (EditText) findViewById(R.id.et_name);
        authorEt = (EditText) findViewById(R.id.et_author);
        pagesEt = (EditText) findViewById(R.id.et_pages);
        priceEt = (EditText) findViewById(R.id.et_price);
        contentLv = (RefreshRecycleView) findViewById(R.id.lv_content);
        addBtn = (Button) findViewById(R.id.btn_add);
        queryBtn = (Button) findViewById(R.id.btn_query);
        photoImg = (ImageView) findViewById(R.id.iv_photo);
        arrow = (ImageView) findViewById(R.id.iv_arrow);
        nameList = (ListView) findViewById(R.id.lv_name);
        //显示头像
        String path = "/sdcard/Tencent/QQfile_recv/p2.png";
        final Bitmap bitmapImg = BitmapFactory.decodeFile(path);
        photoImg.setImageBitmap(bitmapImg);

        //添加数据
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEt.getText().toString().trim();
                String author = authorEt.getText().toString().trim();
                int pages = Integer.parseInt(pagesEt.getText().toString().trim());
                double price = Double.valueOf(priceEt.getText().toString().trim());
                editor.putString("name",name);
                editor.commit();
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("author", author);
                values.put("pages", pages);
                values.put("price", price);
                values.put("picture", getPicture(bitmapImg));
                db.insert("book1",null,values);
                Toast.makeText(DataBaseActivity.this,"数据添加成功",Toast.LENGTH_SHORT).show();
                nameEt.setText("");
                authorEt.setText("");
                pagesEt.setText("");
                priceEt.setText("");
            }
        });

        //查询数据
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                list = queryData();
//                adapter = new FaceInfoAdapter(list,DataBaseActivity.this);
//                contentLv.setAdapter(adapter);
//                //更新 数据
//                adapter.notifyDataSetChanged();
            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClick) {
                    arrow.setImageResource(R.drawable.icon_up);
                    nameList.setVisibility(View.VISIBLE);
                    init();
                    isClick = false;
                } else {
                    arrow.setImageResource(R.drawable.icon_down);
                    nameList.setVisibility(View.GONE);
                    isClick = true;
                }
            }
        });
    }

    private void init() {
        
    }

    private byte[] getPicture(Bitmap bitmap) {
        if(bitmap == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }


    public List<FaceRecordEntity> queryData(){
        List<FaceRecordEntity> list= new ArrayList();
        Cursor cursor = db.query("book1",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String author = cursor.getString(cursor.getColumnIndex("author"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            int pages = cursor.getInt(cursor.getColumnIndex("pages"));
            String name= cursor.getString(cursor.getColumnIndex("name"));
            byte[] bytes = cursor.getBlob(cursor.getColumnIndexOrThrow("picture"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
            FaceRecordEntity faceRecordEntity = new FaceRecordEntity(name,author,pages,price,bitmap);
            list.add(0,faceRecordEntity);
        }
        cursor.close();
        return list;
    }

    private void initContentView() {
        mFaceManager = new RefreshRecycleViewManager<>(contentLv,
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //创建Adapter
        mFaceManager.setAdapterCreate(new RefreshRecycleViewManager.IAdapterCreate<FaceRecordEntity, BaseViewHolder, FaceRecordAdapter>() {
            @Override
            public FaceRecordAdapter create(List<FaceRecordEntity> datas) {
                return new FaceRecordAdapter(datas);
            }
        });
        /*//item的点击事件
        mFaceManager.addOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });*/
        //item的子view的点击事件
        mFaceManager.addOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.iv_picture:
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        Bitmap bitmap = mFaceManager.getData(position).getPicture();
                        if (bitmap == null) {
                            return;
                        } else {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            byte[] bytes = baos.toByteArray();
                            Bundle b = new Bundle();
                            b.putByteArray("bitmap", bytes);
                            Intent intent = new Intent(DataBaseActivity.this, ShowPictureActivity.class);
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                        break;
                    case R.id.iv_delete://删除按钮
                        showDeleteDialog(position);
                        break;
                }
            }
        });
        //上拉加载、下拉刷新,一定要加page
        /*mFaceManager.addRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mFaceManager.setCurrentStatus(RefreshRecycleViewManager.LOADINGMORE);
                list = queryData();
                mFaceManager.onDataLoadFinish(list,RefreshRecycleViewManager.SUCCESS);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mFaceManager.setCurrentStatus(RefreshRecycleViewManager.REFRESHING);
                list = queryData();
                mFaceManager.onDataLoadFinish(list, RefreshRecycleViewManager.SUCCESS);
            }
        });*/
        mFaceManager.addRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mFaceManager.setCurrentStatus(RefreshRecycleViewManager.REFRESHING);
                list = queryData();
                mFaceManager.onDataLoadFinish(list, RefreshRecycleViewManager.SUCCESS);
            }
        });
//        mFaceManager.setEmptyView(ViewHelper.getCustomEmptyView(this, R.drawable.signed_users_empty,
//                "啊哦~您暂未收到消息"));
        //初始化数据
        list = queryData();
        mFaceManager.onDataLoadFinish(list,RefreshRecycleViewManager.SUCCESS);
    }

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定删除吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //删除这条数据
                //db.execSQL("delete from book1 where id = " + position);
            }
        });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

}
