package com.example.jh.hireader.interfaces;

import com.example.jh.hireader.base.BasePresenter;
import com.example.jh.hireader.base.BaseView;
import com.example.jh.hireader.bean.GuokrNews;

import java.util.List;

/**
 * Created by jinhui  on 2017/4/23
 * 邮箱: 1004260403@qq.com
 */

public interface GuokrContract {
    interface View extends BaseView<Presenter> {
        void showLoading();
        void stopLoading();
        void showError();
        void showResult(List<GuokrNews.result> list);
    }
    interface Presenter extends BasePresenter {
        void requestData();
        void refresh();
        void loadDetail(int position);

    }
}
