package com.example.jh.hireader.base;

import android.view.View;

/**
 * Created by jinhui  on 2017/4/20
 * 邮箱: 1004260403@qq.com
 */

public interface BaseView <T>{
    void setPresenter(T presenter);
    void initView(View view);
}

