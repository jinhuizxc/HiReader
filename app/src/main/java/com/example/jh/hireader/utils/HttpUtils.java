package com.example.jh.hireader.utils;

import android.content.Context;

import com.example.jh.hireader.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jinhui  on 2017/4/20
 * 邮箱: 1004260403@qq.com
 */

public class HttpUtils {
    private static HttpUtils mHttpUtils;
    private OkHttpClient.Builder mBuilder;
    private Context mContext;
    private String url;

    private HttpUtils(Context context, String url) {
        this.mContext = context;
        this.url = url;
    }

    public static HttpUtils getInstance() {
        if (mHttpUtils == null) {
            synchronized (HttpUtils.class) {
                if (mHttpUtils == null) {
                    mHttpUtils = new HttpUtils();
                }
            }
        }
        return mHttpUtils;
    }

    private HttpUtils() {
        initOkhttp();
    }

    private void initOkhttp() {
        mBuilder = new OkHttpClient.Builder()
                .connectTimeout(9, TimeUnit.SECONDS)    //设置连接超时 9s
                .readTimeout(10, TimeUnit.SECONDS);      //设置读取超时 10s
        mBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request(); //Current Request
                Response response = chain.proceed(originalRequest); //Get response of the request
                /** DEBUG STUFF */
                if (BuildConfig.DEBUG) {
                    //I am logging the response body in debug mode. When I do this I consume the response (OKHttp only lets you do this once) so i have re-build a new one using the cached body
                    String bodyString = response.body().string();
                    System.out.println(String.format("Sending request %s with headers %s ",
                            originalRequest.url(), originalRequest.headers()));
                    System.out.println(String.format
                            ("Got response HTTP %s %s \n\n with body %s \n\n with headers %s",
                                    response.code(), response.message(), bodyString, response.headers()));
                    response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build();
                }
                return response;
            }
        });
//        if (BuildConfig.DEBUG) { // 判断是否为debug
//            // 如果为 debug 模式，则添加日志拦截器
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            mBuilder.addInterceptor(interceptor);
//        }
    }

    public <T> T create(Class<T> service, String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(mBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(service);
    }
}
