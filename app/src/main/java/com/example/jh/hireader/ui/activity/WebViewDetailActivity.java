package com.example.jh.hireader.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jh.hireader.R;
import com.example.jh.hireader.api.Api;
import com.example.jh.hireader.api.ApiService;
import com.example.jh.hireader.commons.BeanType;
import com.example.jh.hireader.utils.HttpUtils;
import com.example.jh.hireader.bean.ZhihuDetailBean;
import com.orhanobut.logger.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinhui  on 2017/4/20
 * 邮箱: 1004260403@qq.com
 */

public class WebViewDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private WebView mWebView;

    private ProgressBar mProgressBar;
    private ProgressBar mProgress;
    private LayoutInflater layoutInflater;
    private WebSettings mWebViewSettings;
    private int mType;
//    private String mUrl = "https://github.com/w77996";
    private String mUrl = "https://github.com/jinhuizxc";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
        layoutInflater = LayoutInflater.from(this);
        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 1);
        // mWebView.setWebViewClient(new webViewClient());
        String id;
        String url;
        switch (mType) {
            case BeanType.TYPE_ZHIHU:
                id = intent.getStringExtra("id");
//                url = Api.ZHIHU_NEWS + id;
                // 再次请求url
                HttpUtils.getInstance()
                        .create(ApiService.class, Api.ZHIHU_NEWS)
                        .getZhihuDetailNews(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ZhihuDetailBean>() {
                            @Override
                            public void accept(ZhihuDetailBean zhihuDetailBean) throws Exception {
                                String url = zhihuDetailBean.getShare_url();
                                Logger.d(url);
                                mUrl = url;
                                mWebView.loadUrl(url);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Logger.e(throwable + "");
                            }
                        });
                break;
            case BeanType.TYPE_GUOKR:
                id = intent.getStringExtra("id");
                url = Api.GUOKR_ARTICLE_LINK + "pick/" + id;
                // http://jingxuan.guokr.com/pick/82611
                Logger.d(url);
                mUrl = url;
                mWebView.loadUrl(url);
                break;
            case BeanType.TYPE_NEWS:
                id = intent.getStringExtra("link");
                mUrl = id;
                mWebView.loadUrl(id);
                break;
        }
        initWebView();
        initWebSettings();
        initWebViewClient();
        initWebChromeClient();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.web_view);
        //  ivLeftBack = (ImageView) findViewById(R.id.iv_left_back);
        // mTitle = (TextView) findViewById(R.id.tv_title);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgress = (ProgressBar) findViewById(R.id.pb_progress);
        mToolbar = (Toolbar) findViewById(R.id.webview_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 返回键！
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }else if (item.getItemId() == R.id.action_more) {
            Logger.d("fasdf");
            // 底部对话框
            final BottomSheetDialog dialog = new BottomSheetDialog(this);

            View view = layoutInflater.inflate(R.layout.menu_sheet, null);

            // copy the article's link to clipboard
            view.findViewById(R.id.layout_copy_link).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    copyLink();
                }
            });

            // open the link in browser
            view.findViewById(R.id.layout_open_in_browser).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(mUrl));
                    startActivity(intent);
                    // presenter.openInBrowser();
                }
            });

//     /*       // copy the text content to clipboard
//            view.findViewById(R.id.layout_copy_text).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                   // presenter.copyText();
//                }
//            });*/
//
            // shareAsText the content as text
            view.findViewById(R.id.layout_share_text).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    //  presenter.shareAsText();
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, mUrl);
                    startActivity(Intent.createChooser(sharingIntent, "分享"));
                }
            });

            dialog.setContentView(view);
            dialog.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }



    private void initWebSettings() {
        mWebViewSettings.setJavaScriptEnabled(true);
        mWebViewSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebViewSettings.setDomStorageEnabled(true);
        mWebViewSettings.setDatabaseEnabled(true);
        mWebViewSettings.setDefaultTextEncodingName("utf-8");
        mWebViewSettings.setUseWideViewPort(false);
        mWebViewSettings.setSupportZoom(true);
        mWebViewSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebViewSettings.setSupportMultipleWindows(true);
        mWebViewSettings.setAllowFileAccess(true);
        mWebViewSettings.setNeedInitialFocus(true);
        mWebViewSettings.setBuiltInZoomControls(true);
        mWebViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebViewSettings.setLoadWithOverviewMode(true);
    }

    private void initWebView() {
        //mWebView.loadUrl(mUrl);
        mWebViewSettings = mWebView.getSettings();

        if (Build.VERSION.SDK_INT >= 19) {
            // 对于系统API在19以上的版本做了兼容。因为4.4以上的系统在
            // onPageFinished时再恢复图片加载，如果存在多张图片引用的
            // 是相同的src时，会只有一个image标签得到加载，因而对于这样的系统我们就先直接加载。
            mWebViewSettings.setLoadsImagesAutomatically(true);
        } else {
            mWebViewSettings.setLoadsImagesAutomatically(false);
        }

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void initWebChromeClient() {
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgress.setProgress(newProgress);
            }
        });
    }

    private void initWebViewClient() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                // return true: 代表在打开新的 url 是 WebView 就不会再加载这个 url 了
                //              所有处理都需要在 WebView中操作，包含加载
                // return false: 则系统就认为上层没有做处理， 接下来还是会继续加载这个 url
                //
                return super.shouldOverrideUrlLoading(view, url);
            }

            // 加载网页时替换某个资源
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                WebResourceResponse response = null;

                return response;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mToolbar.setTitle("正在加载......");
                mProgressBar.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
                mProgress.setVisibility(View.GONE);
                mToolbar.setTitle(view.getTitle());
                if (!mWebViewSettings.getLoadsImagesAutomatically()) {
                    mWebViewSettings.setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(WebViewDetailActivity.this, "出错了", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // mWebView.canGoBack() 在WebView含有一个可后退的浏览记录时返回true
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void copyLink() {
        // 复制文本内容！ClipboardManager
        ClipboardManager manager = (ClipboardManager) getApplication().getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = null;
        clipData = ClipData.newPlainText("text", mUrl);
        manager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(), "已复制链接至粘贴板", Toast.LENGTH_SHORT).show();
    }
}

