package com.example.jh.hireader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jh.hireader.R;
import com.example.jh.hireader.bean.TodayOfHistoryBean;
import com.example.jh.hireader.interfaces.OnRecyclerViewOnClickListener;
import com.example.jh.hireader.interfaces.TodayOfHistoryContract;
import com.example.jh.hireader.ui.adapter.TodayOfHistoryAdapter;

import java.util.List;

/**
 * Created by jinhui  on 2017/4/23
 * 邮箱: 1004260403@qq.com
 */

public class TodayOfHistoryFragment extends Fragment implements TodayOfHistoryContract.View{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TodayOfHistoryAdapter todayOfHistoryAdapter;
    private TodayOfHistoryContract.Presenter presenter;

    public static TodayOfHistoryFragment newInstance() {
        return new TodayOfHistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        initView(view);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        presenter.start();
        return view;
    }

    @Override
    public void setPresenter(TodayOfHistoryContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_content_swiperefreshlayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_content_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
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
        Snackbar.make(mSwipeRefreshLayout, "加载失败", Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.refresh();
                    }
                })
                .show();
    }

    @Override
    public void showResult(List<TodayOfHistoryBean.ResultBean> list) {
        if (todayOfHistoryAdapter == null) {
            todayOfHistoryAdapter = new TodayOfHistoryAdapter(getContext(), list);

            mRecyclerView.setAdapter(todayOfHistoryAdapter);
            todayOfHistoryAdapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    presenter.loadDetail(position);
                }
            });
        } else {
            todayOfHistoryAdapter.notifyDataSetChanged();
        }
    }
}
