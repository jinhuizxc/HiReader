package com.example.jh.hireader.presenter;

import android.content.Context;

import com.example.jh.hireader.api.Api;
import com.example.jh.hireader.api.ApiService;
import com.example.jh.hireader.bean.TodayOfHistoryDetailBean;
import com.example.jh.hireader.interfaces.TodayOfHistoryDetailContract;
import com.example.jh.hireader.utils.HttpUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinhui  on 2017/4/23
 * 邮箱: 1004260403@qq.com
 */

public class TodayOfHistoryDetailPresenter implements TodayOfHistoryDetailContract.Presenter {

    private Context context;
    TodayOfHistoryDetailContract.View view;
    public TodayOfHistoryDetailPresenter(Context context, TodayOfHistoryDetailContract.View  view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }



    @Override
    public void requestData(String  id) {
        HttpUtils.getInstance()
                .create(ApiService.class, Api.TODAY_HISTORY)
                .getTodayOfHistoryDetailData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TodayOfHistoryDetailBean>() {
                    @Override
                    public void accept(TodayOfHistoryDetailBean todayOfHistoryDetailBean) throws Exception {
                        view.showResult(todayOfHistoryDetailBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError();
                    }
                });
    }



    @Override
    public void start() {

    }
}