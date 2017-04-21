package com.example.jh.hireader.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.jh.hireader.api.Api;
import com.example.jh.hireader.api.ApiService;
import com.example.jh.hireader.commons.BeanType;
import com.example.jh.hireader.interfaces.NewsListContract;
import com.example.jh.hireader.news.NewsBean;
import com.example.jh.hireader.ui.WebViewDetailActivity;
import com.example.jh.hireader.utils.HttpUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinhui  on 2017/4/20
 * 邮箱: 1004260403@qq.com
 */

public class NewsListPresenter implements NewsListContract.Presenter {

    private final Context context;
    private final NewsListContract.View view;
    private int mPager =1;
    private List<NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list = new ArrayList<>();
    public NewsListPresenter(Context context, NewsListContract.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void request(String type, int page, String time, final boolean clearing) {
        // Logger.d("dddddddddddddddddddddddddddddddddddd");
        if(clearing){
            view.showLoading();
        }
        HttpUtils.getInstance().create(ApiService.class, Api.SHOWAPI_NEWS)
                .getNews(type,page,time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsBean>() {
                    @Override
                    public void accept(NewsBean newsBean) throws Exception {
                        if(clearing){
                            list.clear();
                        }
                        for(NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean:newsBean.getShowapi_res_body().getPagebean().getContentlist()){
                            list.add(contentlistBean);
                        }
                        //   Logger.d(""+list.size());
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
    public void loadMore(String type , String time) {
        Logger.d("loadMore");
        mPager =mPager+1;
        request(type,mPager,time,false);
    }

    @Override
    public void refresh(String type,String time) {
        mPager=1;
        request(type,mPager,time,true);
    }

    @Override
    public void showDetail(int position) {
        context.startActivity(new Intent(context, WebViewDetailActivity.class)
                .putExtra("type", BeanType.TYPE_NEWS)
                .putExtra("link", list.get(position).getLink()+""));
    }

    @Override
    public void start() {

        //  request(NewsFragment.TYPE_DOMESTIC,1, dateFomatter.NewsDateFormat(),true);
    }
}

