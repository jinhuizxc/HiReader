package com.example.jh.hireader.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jh.hireader.R;
import com.example.jh.hireader.adapter.ZhihuDailyAdapter;
import com.example.jh.hireader.bean.StoriesBean;
import com.example.jh.hireader.interfaces.OnRecyclerViewOnClickListener;
import com.example.jh.hireader.interfaces.ZhihuDailyContract;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

/**
 * Created by jinhui  on 2017/4/21
 * 邮箱: 1004260403@qq.com
 * 要把网络数据爬取出来打印log，首要任务!
 */

public class ZhihuDailyFragment extends Fragment implements ZhihuDailyContract.View {

    private static final String TAG = "ZhihuDailyFragment";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    // 用getActivity()找到 FloatingActionButton
    private FloatingActionButton mFloatingActionButton;
    private TabLayout mTabLayout;
    // 获取当前年月日
    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    boolean isSlidingToFooter = false;
    private ZhihuDailyContract.Presenter presenter;
    private ZhihuDailyAdapter zhihuDailyAdapter;

    public static ZhihuDailyFragment newInstance() {
        return new ZhihuDailyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        initView(view);
        presenter.start();
        // 设置刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        // 加载布局时，onScrolled方法会被执行，onScrollStateChanged方法只有拖动时才会被执行。
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e(TAG, "onScrollStateChanged 方法被执行");
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToFooter) {
                        Calendar c = Calendar.getInstance();
                        c.set(mYear, mMonth, --mDay);
                        presenter.loadMore(c.getTimeInMillis());
                    }
                }

            }
            // 参数dx,dy指的是对应屏幕的方向
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e(TAG, "onScrolled 方法被执行");
                isSlidingToFooter = dy > 0; // isSlidingToFooter = true
                if (dy > 0) {
                    mFloatingActionButton.hide();
                } else {
                    mFloatingActionButton.show();
                }
            }
        });
        // 点击事件
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick 方法被执行");
                if (mTabLayout.getSelectedTabPosition() == 0) {
                    showPickerDialog();
                }
            }
        });
        return view;
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void stopLoading() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showError() {
        Snackbar.make(mFloatingActionButton, "加载失败", Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.refresh();
                    }
                })
                .show();
    }

    @Override
    public void showResult(List<StoriesBean> list) {
        if (zhihuDailyAdapter == null) {
            zhihuDailyAdapter = new ZhihuDailyAdapter(getContext(), list);
            zhihuDailyAdapter.setItemOnClick(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    //点击列表事件
//                    presenter.loadDetail(position);
                }
            });
            mRecyclerView.setAdapter(zhihuDailyAdapter);
        } else {
            zhihuDailyAdapter.notifyDataSetChanged();
        }
    }

    public void showPickerDialog() {
        Calendar now = Calendar.getInstance();
        now.set(mYear, mMonth, mDay);
        DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            // 设置日期时才会调用接口
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                Calendar temp = Calendar.getInstance();
                temp.clear();
                temp.set(year, monthOfYear, dayOfMonth);
                // 请求一次数据
                presenter.requestData(temp.getTimeInMillis(), true);
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dialog.setAccentColor(getActivity().getResources().getColor(R.color.colorPrimary));
        dialog.setMaxDate(Calendar.getInstance());
        Calendar minDate = Calendar.getInstance();
        // 2013.5.20是知乎日报api首次上线
        minDate.set(2013, 5, 20);
        dialog.setMinDate(minDate);
        dialog.vibrate(false);
        dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        // 对于字符串而言我们要判断它是否为空，而对于this指向型对象而言要判断它不为空。
        // 像适配器对象要判断是否为空，进而new新的对象
        // 这点要特别注意！
        if (presenter != null) {
            Log.e(TAG, "presenter地址 =" + presenter);
            this.presenter = presenter;
        }
    }

    public void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_content_swiperefreshlayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_content_recyclerview);
        //3，（可选）如果可以确定每个item的高度是固定的，设置这个选项可以提高性能。
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mFloatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fragment_main_fab);
        mTabLayout = (TabLayout) getActivity().findViewById(R.id.fragment_main_tablayout);
    }
}
