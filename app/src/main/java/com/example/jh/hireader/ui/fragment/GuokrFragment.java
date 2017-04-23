package com.example.jh.hireader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jh.hireader.R;
import com.example.jh.hireader.ui.adapter.GuokrAdapter;
import com.example.jh.hireader.bean.GuokrNews;
import com.example.jh.hireader.interfaces.GuokrContract;
import com.example.jh.hireader.interfaces.OnRecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by jinhui  on 2017/4/21
 * 邮箱: 1004260403@qq.com
 */

public class GuokrFragment extends Fragment implements GuokrContract.View {

    private GuokrContract.Presenter presenter;
    private GuokrAdapter guokrAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    public static  GuokrFragment newInstance(){
        return new GuokrFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content,container,false);
        initView(view);
        presenter.start();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
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
        Snackbar.make(mSwipeRefreshLayout, "加载失败",Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.refresh();
                    }
                }) .show();
    }

    @Override
    public void showResult(List<GuokrNews.result> list) {
        if(guokrAdapter==null){
            guokrAdapter = new GuokrAdapter(getContext(),list);
            guokrAdapter.setOnItemClick(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    presenter.loadDetail(position);
                }
            });
            mRecyclerView.setAdapter(guokrAdapter);
        }else{
            guokrAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setPresenter(GuokrContract.Presenter presenter) {
        if(presenter!=null){
            this.presenter = presenter;
        }
    }

    @Override
    public void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fragment_content_swiperefreshlayout);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_content_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }
}
