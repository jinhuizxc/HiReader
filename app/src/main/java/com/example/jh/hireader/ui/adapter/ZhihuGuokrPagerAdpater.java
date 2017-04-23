package com.example.jh.hireader.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jh.hireader.ui.fragment.GuokrFragment;
import com.example.jh.hireader.ui.fragment.ZhihuDailyFragment;

/**
 * Created by jinhui  on 2017/4/21
 * 邮箱: 1004260403@qq.com
 */

public class ZhihuGuokrPagerAdpater extends FragmentPagerAdapter {

    private ZhihuDailyFragment zhihuDailyFragment;
    private GuokrFragment guokrFragment;
    private Context mContext;
    // 设置title
    private String[] titles;

    public ZhihuGuokrPagerAdpater(FragmentManager fm) {
        super(fm);
    }

    public ZhihuGuokrPagerAdpater(FragmentManager fm, Context context, ZhihuDailyFragment zhihuDailyFragment, GuokrFragment guokrFragment) {
        super(fm);
        this.mContext = context;
        this.zhihuDailyFragment = zhihuDailyFragment;
        this.guokrFragment = guokrFragment;
        titles = new String[]{"知乎日报", "果壳精选"};
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return zhihuDailyFragment;
        }else if(position == 1){
            return guokrFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }
    // 设置title字符串到适配器

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
