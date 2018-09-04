package com.dev.alarmclock.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.alarmclock.R;
import com.dev.alarmclock.activity.ShowPictureActivity;
import com.dev.alarmclock.entity.FaceRecordEntity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Estelle} on 2018/7/4.
 */

public class FaceInfoAdapter extends BaseAdapter {
    private List<FaceRecordEntity> list = new ArrayList<>();
    private Context context;

    public FaceInfoAdapter(List<FaceRecordEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FaceRecordEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_face_info, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final FaceRecordEntity entity = getItem(position);
        viewHolder.nameTv.setText(entity.getName());
        viewHolder.authorTv.setText(entity.getAuthor());
        viewHolder.pagesTv.setText(entity.getPages() + "");
        viewHolder.priceTv.setText(entity.getPrice() + "");
        viewHolder.pictureIv.setImageBitmap(entity.getPicture());

        viewHolder.pictureIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = entity.getPicture();
                if (bitmap == null) {
                    return;
                } else {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] bytes = baos.toByteArray();
                    Bundle b = new Bundle();
                    b.putByteArray("bitmap", bytes);
                    Intent intent = new Intent(context, ShowPictureActivity.class);
                    intent.putExtras(b);
                    context.startActivity(intent);
                }

            }
        });

        return convertView;
    }

    static class ViewHolder {
        private TextView nameTv;
        private TextView authorTv;
        private TextView pagesTv;
        private TextView priceTv;
        private ImageView pictureIv;

        public ViewHolder(View convertView) {
            nameTv = (TextView) convertView.findViewById(R.id.tv_name);
            authorTv = (TextView) convertView.findViewById(R.id.tv_author);
            pagesTv = (TextView) convertView.findViewById(R.id.tv_pages);
            priceTv = (TextView) convertView.findViewById(R.id.tv_price);
            pictureIv = (ImageView) convertView.findViewById(R.id.iv_picture);
        }
    }
}
