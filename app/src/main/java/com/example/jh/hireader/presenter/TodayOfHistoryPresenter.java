package com.example.jh.hireader.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.jh.hireader.api.Api;
import com.example.jh.hireader.api.ApiService;
import com.example.jh.hireader.bean.TodayOfHistoryBean;
import com.example.jh.hireader.commons.BeanType;
import com.example.jh.hireader.interfaces.TodayOfHistoryContract;
import com.example.jh.hireader.ui.activity.MainActivity;
import com.example.jh.hireader.ui.activity.TodayOfHistoryDetailActivity;
import com.example.jh.hireader.ui.fragment.TodayOfHistoryFragment;
import com.example.jh.hireader.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinhui  on 2017/4/23
 * 邮箱: 1004260403@qq.com
 */

public class TodayOfHistoryPresenter implements TodayOfHistoryContract.Presenter {

    private static final String TAG = "TodayOfHistoryPresenter";
    private final Context context;
    private List<TodayOfHistoryBean.ResultBean> list = new ArrayList<>();
    private final TodayOfHistoryContract.View view;

    public TodayOfHistoryPresenter(Context context, TodayOfHistoryContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        requestData();
    }


    /**
     * @url
     * @Api.TODAY_HISTORY
     * http://api.juheapi.com/japi/
     * @ApiService.class
     * toh?v=&key=02351f897b139cc86e39a225aaeaa42d
     * @请求历史上今天的url
     * http://api.juheapi.com/japi/toh?v=&key=02351f897b139cc86e39a225aaeaa42d&month=4&day=23
     */

    @Override
    public void requestData() {
        view.showLoading();
        Calendar calendar = Calendar.getInstance();
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        HttpUtils.getInstance()
                .create(ApiService.class, Api.TODAY_HISTORY)
                .getTodayOfHistoryData(month + "", day + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TodayOfHistoryBean>() {
                    @Override
                    public void accept(TodayOfHistoryBean todayOfHistoryBean) throws Exception {
                        Log.e(TAG, "获取内容 = " + todayOfHistoryBean.toString());
                        for(TodayOfHistoryBean.ResultBean resultList :todayOfHistoryBean.getResult()){
                            list.add(resultList);
                        }
                        view.showResult(list);
                        view.stopLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError();
                        view.stopLoading();
                    }
                });
    }

    @Override
    public void refresh() {
        list.clear();
        requestData();
    }

    @Override
    public void loadDetail(int position) {
        context.startActivity(new Intent(context, TodayOfHistoryDetailActivity.class)
                .putExtra("title",list.get(position).getTitle())
                .putExtra("id", list.get(position).get_id()));
    }
}
