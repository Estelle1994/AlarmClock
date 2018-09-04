package com.dev.alarmclock.util;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dev.alarmclock.view.RefreshRecycleView;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by margin on 2017/12/7.
 * 这个类很重要,它接管了 App 里面的所有列表式页面的创建,Adapter 初始化, 数据初始化加载刷新, 以及各种 item 点击
 * 事件的处理逻辑.
 */

public class RefreshRecycleViewManager<T, K extends BaseViewHolder, A extends BaseQuickAdapter<T, K>> {
    private RefreshRecycleView mRefreshRecycleView;
    private View mEmptyView;
    private View mHeaderView;
    private A mAdapter;
    private List<T> mDatas;
    private View.OnClickListener mOnHeaderViewClickListener;
    private BaseQuickAdapter.OnItemClickListener mItemClickListener;
    private BaseQuickAdapter.OnItemChildClickListener mItemChildClickListener;
    private OnRefreshListener mRefreshListener;
    private OnRefreshLoadmoreListener mRefreshLoadmoreListener;
    private IAdapterCreate<T, K, A> mAdapterCreate;
    private OnDataFirstLoadListener<T> mOnDataFirstLoadListener;
    private OnMergeDataListener<T> mMergeDataListener;
    private int mPages = 1;
//    标记在下拉刷新未获取到数据时（刷新成功）是否需要清空旧的数据，默认是清空的
    private boolean isHoldData;

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int END = 2;

    private static final int UNINITIALIZED = 0;
    private static final int INITIALIZED = 1;
    public static final int LOADINGMORE = 2;
    public static final int REFRESHING = 3;
    private int currentStatus = UNINITIALIZED;
    private int mDelay = 100;


    public RefreshRecycleViewManager(RefreshRecycleView view, RecyclerView.LayoutManager layoutManager) {
        mRefreshRecycleView = view;
        mRefreshRecycleView.setLayoutManager(layoutManager);
    }

    public void addOnItemClickListener(BaseQuickAdapter.OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }

    public void addOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener) {
        mItemChildClickListener = onItemChildClickListener;
    }

    public void addRefreshListener(OnRefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    public void addRefreshLoadmoreListener(OnRefreshLoadmoreListener refreshLoadmoreListener) {
        mRefreshLoadmoreListener = refreshLoadmoreListener;
    }

    public void setOnDataFirstLoadListener(OnDataFirstLoadListener<T> onDataFirstLoadListener) {
        mOnDataFirstLoadListener = onDataFirstLoadListener;
    }

    public void setMergeDataListener(OnMergeDataListener<T> mergeDataListener) {
        mMergeDataListener = mergeDataListener;
    }

    public void setOnHeaderViewClickListener(View.OnClickListener clickListener) {
        mOnHeaderViewClickListener = clickListener;
    }

    public void setEmptyView(View view) {
        mEmptyView = view;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
    }

    public void setAdapterCreate(IAdapterCreate<T, K, A> adapterCreate) {
        mAdapterCreate = adapterCreate;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setHoldData(boolean holdData) {
        isHoldData = holdData;
    }

    public int increasePages() {
        int ret = mPages;
        mPages += 1;
        return ret;
    }

    public void onDataLoadFinish(List<T> datas, int resutStatus) {
        switch (resutStatus) {
            case SUCCESS:
                handleLoadSuccess(datas);
                break;
            case END:
                handleLoadEnd();
                break;
            case FAIL:
                handleLoadFail();
                break;
        }
    }

    private void handleLoadFail() {
        switch (currentStatus) {
            case UNINITIALIZED:
                init();
                break;
            case LOADINGMORE:
                loadMoreFail();
                break;
            case REFRESHING:
                refreshComplete(false);
                break;
        }
    }

    private void handleLoadEnd() {
        switch (currentStatus) {
            case UNINITIALIZED:
                init();
                break;
            case LOADINGMORE:
                loadMoreEnd();
                break;
            case REFRESHING:
                refreshComplete(true);
                break;
        }
    }

    private void handleLoadSuccess(List<T> datas) {
        switch (currentStatus) {
            case UNINITIALIZED:
                initWithDatas(datas);
                break;
            case LOADINGMORE:
                loadMoreComplete(datas);
                break;
            case REFRESHING:
                refreshComplete(datas);
                break;
        }
    }


    private void initWithDatas(List<T> datas) {
        mDatas = datas;
        earlyInit();
    }

    public void init() {
        mDatas = new ArrayList<>();
        earlyInit();
    }

    public void earlyInit() {
        if (mOnDataFirstLoadListener != null && mDatas.size() > 0) {
            mOnDataFirstLoadListener.onDataFirstLoad(mDatas);
        }
        mAdapter = mAdapterCreate.create(mDatas);
        mRefreshRecycleView.setAdapter(mAdapter);
        if (mItemClickListener != null) {
            mAdapter.setOnItemClickListener(mItemClickListener);
        }
        if (mItemChildClickListener != null) {
            mAdapter.setOnItemChildClickListener(mItemChildClickListener);
        }
        if (mRefreshListener != null) {
            mRefreshRecycleView.setRefreshListener(mRefreshListener);
        }
        if (mRefreshLoadmoreListener != null) {
            mRefreshRecycleView.setRefreshLoadmoreListener(mRefreshLoadmoreListener);
        }
        if (mEmptyView != null) {
            mAdapter.setEmptyView(mEmptyView);
        }
        if (mHeaderView != null) {
            mAdapter.setHeaderView(mHeaderView);
            mAdapter.setHeaderAndEmpty(true);
            if (mOnHeaderViewClickListener != null) {
                mHeaderView.setOnClickListener(mOnHeaderViewClickListener);
            }
        }
        currentStatus = INITIALIZED;
    }

    private void refreshComplete(List<T> datas) {
        mRefreshRecycleView.finishRefresh(mDelay, true);
        if (datas != null && datas.size() > 0) {
            if (mOnDataFirstLoadListener != null) {
                mOnDataFirstLoadListener.onDataFirstLoad(datas);
            }
            mDatas.clear();
            mDatas.addAll(datas);
            mAdapter.notifyDataSetChanged();
        }
        currentStatus = INITIALIZED;
        mPages = 2;
    }

    public void refreshComplete(boolean success) {
        mRefreshRecycleView.finishRefresh(mDelay, success);
        if (success && mDatas != null && mDatas.size() > 0 && !isHoldData) {
            mDatas.clear();
            mAdapter.notifyDataSetChanged();
        }
        currentStatus = INITIALIZED;
    }

    private void loadMoreComplete(List<T> datas) {
        int start = 0;
        if (mMergeDataListener == null) {
            if (mDatas.size() > 0) {
                start = mDatas.size() - 1;
            }
            mDatas.addAll(start, datas);
        } else {
            start = mMergeDataListener.onMergeData(mDatas, datas);
        }
        mAdapter.notifyItemRangeInserted(start, datas.size());
        mRefreshRecycleView.finishLoadmore(mDelay,true);
        currentStatus = INITIALIZED;
    }

    public void loadMoreComplete(List<T> datas, int index) {
        mRefreshRecycleView.finishLoadmore(mDelay, true);
        mDatas.addAll(index, datas);
        mAdapter.notifyItemRangeInserted(index, datas.size());
        currentStatus = INITIALIZED;
    }

    private void loadMoreEnd() {
        mRefreshRecycleView.finishLoadmore(mDelay, true);
        currentStatus = INITIALIZED;
        mPages -= 1;
    }

    private void loadMoreFail() {
        mRefreshRecycleView.finishLoadmore(mDelay, false);
        currentStatus = INITIALIZED;
        mPages -= 1;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public T getData(int position) {
        if (mDatas == null || mDatas.size() <= position) {
            return null;
        }
        return mDatas.get(position);
    }

    public void notifyItemChanged(int position) {
        if (mDatas == null || mDatas.size() <= position) {
            return;
        }
        mAdapter.notifyItemChanged(position);
    }

    public A getAdapter() {
        return mAdapter;
    }

    public void autoRefresh() {
        mRefreshRecycleView.autoRefresh(0);
    }


    public interface IAdapterCreate<T, K extends BaseViewHolder, A extends BaseQuickAdapter<T, K>> {
        A create(List<T> datas);
    }

    public interface OnDataFirstLoadListener<T> {
        void onDataFirstLoad(List<T> datas);
    }

    public interface OnMergeDataListener<T> {
        int onMergeData(List<T> oldDatas, List<T> newDatas);
    }

}
