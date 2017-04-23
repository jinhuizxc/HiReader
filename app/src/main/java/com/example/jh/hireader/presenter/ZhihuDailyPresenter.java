package com.example.jh.hireader.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.jh.hireader.api.Api;
import com.example.jh.hireader.api.ApiService;
import com.example.jh.hireader.bean.StoriesBean;
import com.example.jh.hireader.bean.ZhihuDailyBean;
import com.example.jh.hireader.commons.BeanType;
import com.example.jh.hireader.interfaces.ZhihuDailyContract;
import com.example.jh.hireader.ui.activity.WebViewDetailActivity;
import com.example.jh.hireader.utils.DateFormatUtils;
import com.example.jh.hireader.utils.HttpUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinhui  on 2017/4/21
 * 邮箱: 1004260403@qq.com
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {

    private static final String TAG = "ZhihuDailyPresenter";
    private Context mContext;
    private ZhihuDailyContract.View view;
    // 链表内容
    private List<StoriesBean> list = new ArrayList<StoriesBean>();

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view) {
        this.mContext = context;
        this.view = view;
        this.view.setPresenter(this);
        Log.e(TAG, "this =" + this);
    }

    @Override
    public void start() {
        requestData(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void requestData(long date, final boolean clearing) {
        Log.e(TAG, "date =" + date); // 1492923779851
        DateFormatUtils dateFormat = new DateFormatUtils();
        if (clearing) {
            Log.e(TAG, "clearing =" + clearing);
            view.showLoading();
        }
        // 请求数据的逻辑部分，很重要！
        HttpUtils.getInstance()
                .create(ApiService.class, Api.ZHIHU_HISTORY)
                .getZhihuDaily(dateFormat.ZhihuDailyDateFormat(date))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ZhihuDailyBean>() {
                    @Override
                    public void accept(ZhihuDailyBean zhihuDailyBean) throws Exception {
                        Log.e(TAG, "获取内容 =" + zhihuDailyBean.toString());
                        if (clearing) {
                            list.clear();
                        }
                        for (StoriesBean storiesBean : zhihuDailyBean.getStories()) {
                            list.add(storiesBean);
                        }
                        view.showResult(list);
                        view.stopLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // 无用的logger?
                        com.orhanobut.logger.Logger.d(throwable);
                        view.showError();
                        view.stopLoading();
                    }
                });
    }

    @Override
    public void refresh() {
        requestData(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        requestData(date, false);
    }

    @Override
    public void loadDetail(int position) {
        Intent intent = new Intent(mContext, WebViewDetailActivity.class);
        Logger.d(list.get(position).getId());
        intent.putExtra("id", list.get(position).getId() + "");
        intent.putExtra("type", BeanType.TYPE_ZHIHU);
        Logger.d(position);
        mContext.startActivity(intent);
    }
}
