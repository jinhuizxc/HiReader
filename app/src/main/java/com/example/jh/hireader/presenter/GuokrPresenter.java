package com.example.jh.hireader.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.jh.hireader.api.Api;
import com.example.jh.hireader.api.ApiService;
import com.example.jh.hireader.bean.GuokrNews;
import com.example.jh.hireader.commons.BeanType;
import com.example.jh.hireader.interfaces.GuokrContract;
import com.example.jh.hireader.ui.activity.WebViewDetailActivity;
import com.example.jh.hireader.utils.HttpUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinhui  on 2017/4/23
 * 邮箱: 1004260403@qq.com
 */

public class GuokrPresenter implements GuokrContract.Presenter {
    private Context mContext;
    private GuokrContract.View view;
    private List<GuokrNews.result> list = new ArrayList<>();
    public GuokrPresenter(Context context,GuokrContract.View view){
        this.mContext =context;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void requestData() {
        list.clear();
        view.showLoading();
        HttpUtils.getInstance()
                .create(ApiService.class, Api.GUOKR_ARTICLES)
                .getGuokrHandpick()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GuokrNews>() {
                    @Override
                    public void accept(GuokrNews guokrHandpickNews) throws Exception {

                        for(GuokrNews.result result :guokrHandpickNews.getResult()){
                            list.add(result);
                        }
                        Logger.d(list.size()+"");
                        view.showResult(list);
                        view.stopLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.stopLoading();
                        view.showError();
                    }
                });
    }

    @Override
    public void refresh() {
        requestData();
    }

    @Override
    public void loadDetail(int position) {
        mContext.startActivity(new Intent(mContext, WebViewDetailActivity.class)
                .putExtra("type", BeanType.TYPE_GUOKR)
                .putExtra("id", list.get(position).getId()+"")
                .putExtra("title", list.get(position).getTitle()));
    }

    @Override
    public void start() {
        requestData();
    }
}

