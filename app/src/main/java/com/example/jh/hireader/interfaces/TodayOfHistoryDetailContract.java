package com.example.jh.hireader.interfaces;

import com.example.jh.hireader.base.BasePresenter;
import com.example.jh.hireader.base.BaseView;
import com.example.jh.hireader.bean.TodayOfHistoryDetailBean;

/**
 * Created by jinhui  on 2017/4/23
 * 邮箱: 1004260403@qq.com
 */

public interface TodayOfHistoryDetailContract {

    interface  View extends BaseView<Presenter> {
        void showResult(TodayOfHistoryDetailBean todayOfHistoryDetailBean);
        void showError();
        void shareData(String txt);
    }
    interface Presenter extends BasePresenter {
        void requestData(String id);
    }
}
