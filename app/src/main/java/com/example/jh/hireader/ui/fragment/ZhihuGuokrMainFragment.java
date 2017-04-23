package com.example.jh.hireader.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jh.hireader.R;
import com.example.jh.hireader.ui.adapter.ZhihuGuokrPagerAdpater;
import com.example.jh.hireader.presenter.GuokrPresenter;
import com.example.jh.hireader.presenter.ZhihuDailyPresenter;

/**
 * Created by jinhui  on 2017/4/21
 * 邮箱: 1004260403@qq.com
 */

public class ZhihuGuokrMainFragment extends Fragment{

    private static final String TAG = "ZhihuGuokrMainFragment";
    private TabLayout mTabLayout;
    private Context mContext;
    private ZhihuGuokrPagerAdpater zhihuGuokrPagerAdpater;
    private ZhihuDailyFragment zhihuDailyFragment;
    private GuokrFragment guokrFragment;
    // 加载数据
    private ZhihuDailyPresenter zhihuDailyPresenter;
    private GuokrPresenter guokrPresenter;

    public static ZhihuGuokrMainFragment newInstance() {
        return  new ZhihuGuokrMainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        zhihuDailyFragment = ZhihuDailyFragment.newInstance();
        guokrFragment = GuokrFragment.newInstance();
        // 请求数据前准备
//        zhihuDailyPresenter = new ZhihuDailyPresenter(getContext(),zhihuDailyFragment);
        zhihuDailyPresenter = new ZhihuDailyPresenter(mContext,zhihuDailyFragment);
        guokrPresenter = new GuokrPresenter(mContext,guokrFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container,false);
        initView(view);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e(TAG, "onTabSelected方法被执行");
//                //这里有问题啊！以后在解决吧！————————————————————————————————————————————————
//                FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fragment_main_fab);
//                if (tab.getPosition() != 0) {
//                    Log.e(TAG, "tab.getPosition =" + tab.getPosition());
//                    fab.hide();
//                }else{
//                    Log.e(TAG, "tab.getPosition1111111111 = " + tab.getPosition());
//                    fab.show();
//                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.fragment_main_tablayout);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragment_main_viewpager);
        viewPager.setOffscreenPageLimit(2);
        // 其实就是一个适配器
        zhihuGuokrPagerAdpater = new ZhihuGuokrPagerAdpater(
                getChildFragmentManager(),
                getContext(),
                zhihuDailyFragment,
                guokrFragment);
        viewPager.setAdapter(zhihuGuokrPagerAdpater);
        mTabLayout.setupWithViewPager(viewPager);
    }
}
