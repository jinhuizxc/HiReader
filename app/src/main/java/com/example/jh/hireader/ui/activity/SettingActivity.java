package com.example.jh.hireader.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jh.hireader.R;
import com.example.jh.hireader.utils.GlideCacheUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by jinhui  on 2017/4/23
 * 邮箱: 1004260403@qq.com
 *
 * 清除缓存操作！后续加入别的功能，可拓展。
 */

public class SettingActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mTextViewCache;
    private LinearLayout mLinearLayoutClearCache;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        // 打印缓存的logger
        Logger.d(GlideCacheUtils.getInstance().getCacheSize(getApplicationContext()));
        mTextViewCache.setText(GlideCacheUtils.getInstance().getCacheSize(getApplicationContext()));
        mLinearLayoutClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlideCacheUtils.getInstance().clearImageAllCache(getApplicationContext());
                mTextViewCache.setText("0.0Byte");
                Toast.makeText(getApplicationContext(),"清除成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mToolbar = (Toolbar)findViewById(R.id.setting_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("设置");
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        mTextViewCache = (TextView)findViewById(R.id.setting_cache);
        mLinearLayoutClearCache = (LinearLayout)findViewById(R.id.setting_clear_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
