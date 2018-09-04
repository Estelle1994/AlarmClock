package com.dev.alarmclock.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

/**
 * Created by margin on 2017/12/8.
 * 可刷新的RecycleView
 */

public class RefreshRecycleView extends SmartRefreshLayout {
    private RecyclerView mRecyclerView;

    public RefreshRecycleView(Context context) {
        super(context);
        init(context);
    }

    public RefreshRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mRecyclerView = new RecyclerView(context);
        SmartRefreshLayout.LayoutParams layoutParams = new SmartRefreshLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mRecyclerView.setLayoutParams(layoutParams);
        addView(mRecyclerView);
    }

    public void setLoadmoreListener(OnLoadmoreListener loadmoreListener) {
        setRefreshFooter(new ClassicsFooter(getContext()));
        setEnableLoadmore(true);
        setOnLoadmoreListener(loadmoreListener);
    }

    public void setRefreshListener(OnRefreshListener refreshListener) {
        setRefreshHeader(new ClassicsHeader(getContext()));
        setEnableRefresh(true);
        setOnRefreshListener(refreshListener);
    }

    public void setRefreshLoadmoreListener(OnRefreshLoadmoreListener refreshLoadmoreListener) {
        setRefreshHeader(new ClassicsHeader(getContext()));
        setRefreshFooter(new ClassicsFooter(getContext()));
        setEnableRefresh(true);
        setEnableLoadmore(true);
        setOnRefreshLoadmoreListener(refreshLoadmoreListener);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }
}
