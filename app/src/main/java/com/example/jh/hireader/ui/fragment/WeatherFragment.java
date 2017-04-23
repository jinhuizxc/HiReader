package com.example.jh.hireader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jh.hireader.R;

/**
 * Created by jinhui  on 2017/4/23
 * 邮箱: 1004260403@qq.com
 *
 * 天气界面参考seeWeather来做！
 */

public class WeatherFragment extends Fragment{

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment_weather,container,false);

        return view;
    }


}
