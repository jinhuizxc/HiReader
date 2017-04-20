package com.example.jh.hireader.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jh.hireader.R;
import com.example.jh.hireader.fragment.ChatFragment;
import com.example.jh.hireader.fragment.NewsFragment;

/**
 * 目前项目buildToolsVersion "25.0.2",没有25.0.3版本达不到
 * 2017/4/18 今天开始新项目的开发：HiReader
 * 1.使用Android studio 自带Android meterial design侧滑风格，加速开发速度。
 * 2.添加网络url拦截，json内容的获取，在html页面的显示或者是显示log(重要)
 * 3.添加聊天机器人
 *
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    // 新闻资讯
    private NewsFragment newsFragment;
    //聊天机器人
    private ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        chatFragment = ChatFragment.newIntance();
        newsFragment = NewsFragment.newInstance();
        // 添加Fragment 到 FrameLayout
        if (!newsFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.fl, newsFragment, "newsFragment").commit();
        }
        if (!chatFragment.isAdded()) {     // main_container
            getSupportFragmentManager().beginTransaction().add(R.id.fl, chatFragment, "chatFragment").commit();
        }
        showNewsFragment();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // FloatingActionButton
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        // 添加上这句是为了让用户认为这里就是新闻资讯界面
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("新闻资讯");
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_news) {
            // 新闻资讯
            showNewsFragment();
            // Handle the camera action
        } else if (id == R.id.nav_home) {
            // 知乎豆瓣
        } else if (id == R.id.nav_todayofhistory) {
            // 历史上的今天
        } else if (id == R.id.nav_chat) {
            // 聊天机器人
            showChatFragment();
        } else if (id == R.id.nav_weather) {
            // 城市天气
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // 少了这一句DrawerLayout不会隐藏，得点击别的区域才可以。
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showNewsFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(newsFragment);
        fragmentTransaction.hide(chatFragment);
//        fragmentTransaction.hide(zhihuGuokrMainFragment);
//        fragmentTransaction.hide(todayOfHistoryFragment);
//        fragmentTransaction.hide(weatherFragment);
        fragmentTransaction.commit();

        toolbar.setTitle("新闻资讯");
    }

    private void showChatFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(chatFragment);
        fragmentTransaction.hide(newsFragment);
//        fragmentTransaction.hide(todayOfHistoryFragment);
//        fragmentTransaction.hide(zhihuGuokrMainFragment);
//        fragmentTransaction.hide(weatherFragment);
        fragmentTransaction.commit();

        toolbar.setTitle("聊天机器人");
    }
}
