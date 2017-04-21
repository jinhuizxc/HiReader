package com.example.jh.hireader.interfaces;

import com.example.jh.hireader.base.BasePresenter;
import com.example.jh.hireader.base.BaseView;
import com.example.jh.hireader.bean.StoriesBean;

import java.util.List;

/**
 * Created by jinhui  on 2017/4/21
 * 邮箱: 1004260403@qq.com
 * 发现一个神奇的事情：那就是接口，可以看到左边的方法箭头指向是向下的就是说
 * 只要是子类(presenter、activity、fragment）继承该接口都会调用。
 */

public interface ZhihuDailyContract {
    interface View extends BaseView<Presenter>{
        void showLoading();
        void stopLoading();
        void showError();
        void showResult(List<StoriesBean> list);
        void showPickerDialog();
    }
    interface Presenter extends BasePresenter{
        void requestData(long date, boolean clearing);
        void  refresh();
        void loadMore(long date);
        void loadDetail(int position);
    }
}
