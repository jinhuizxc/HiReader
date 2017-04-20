package com.example.jh.hireader.base;

import android.support.v4.app.Fragment;

import com.orhanobut.logger.Logger;

/**
 * Created by jinhui  on 2017/4/20
 * 邮箱: 1004260403@qq.com
 */

public class BaseFragment extends Fragment{

    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    /**  懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次 */
    protected void lazyFetchData() {
        Logger.d(getClass().getName() + "------>lazyFetchData");
    }

    private void lazyFetchDataIfPrepared() {
// 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true; //已加载过数据
            lazyFetchData();
        }
    }
}

