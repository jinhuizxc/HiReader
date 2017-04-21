package com.example.jh.hireader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jh.hireader.R;

/**
 * Created by jinhui  on 2017/4/21
 * 邮箱: 1004260403@qq.com
 */

public class GuokrFragment extends Fragment {

    public static GuokrFragment newInstance() {
        return new GuokrFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content,container,false);
        return view;
    }
}
