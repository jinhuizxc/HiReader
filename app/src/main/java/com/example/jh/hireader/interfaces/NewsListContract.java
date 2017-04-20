package com.example.jh.hireader.interfaces;

import com.example.jh.hireader.base.BasePresenter;
import com.example.jh.hireader.base.BaseView;
import com.example.jh.hireader.news.NewsBean;

import java.util.List;

/**
 * Created by jinhui  on 2017/4/20
 * 邮箱: 1004260403@qq.com
 */

public interface NewsListContract {
    interface View extends BaseView<Presenter> {
        void showLoading();
        void stopLoading();
        void showError();
        void showResult(List<NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list);
    }
    interface  Presenter extends BasePresenter {
        void request(String type,int page,String time,boolean clearing);
        void loadMore(String type,String time);
        void refresh(String type,String time);
        void showDetail(int position);
    }
}

