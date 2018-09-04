package com.dev.alarmclock.adapter;


import android.support.annotation.Nullable;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.alarmclock.R;
import com.dev.alarmclock.entity.FaceRecordEntity;


import java.util.List;

/**
 * Created by ${Estelle} on 2018/7/26.
 */

public class FaceRecordAdapter extends BaseQuickAdapter<FaceRecordEntity,BaseViewHolder> {

    public FaceRecordAdapter(@Nullable List<FaceRecordEntity> data) {
        super(R.layout.item_face_info, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final FaceRecordEntity entity) {
        helper.setText(R.id.tv_name,"名称：" + entity.getName());
        helper.setText(R.id.tv_author,"作者：" + entity.getAuthor());
        helper.setText(R.id.tv_pages,"页数：" + entity.getPages() + "");
        helper.setText(R.id.tv_price,"价格：" + entity.getPrice() + "");
        ImageView avatar = helper.getView(R.id.iv_picture);
        avatar.setImageBitmap(entity.getPicture());
        ImageView deleteIv = helper.getView(R.id.iv_delete);
        deleteIv.setImageResource(R.drawable.icon_delete);
        helper.addOnClickListener(R.id.iv_picture);
        helper.addOnClickListener(R.id.iv_delete);
    }
}
