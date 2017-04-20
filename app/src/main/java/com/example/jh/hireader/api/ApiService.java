package com.example.jh.hireader.api;

import com.example.jh.hireader.chat.bean.ChatBean;
import com.example.jh.hireader.news.NewsBean;
import com.example.jh.hireader.zhihudetail.ZhihuDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jinhui  on 2017/4/20
 * 邮箱: 1004260403@qq.com
 */

public interface ApiService {

    // 聊天机器人的api
    @GET("api?key=1c21b0606d78455c8760136c8dadfd70")
    Observable<ChatBean> getChatData(@Query("info") String info);

    @GET("109-35?channelId=&maxResult=10&needAllList=0&needContent=0&needHtml=0&showapi_appid=33655&title=&showapi_sign=01b6b253d82c44a08ab329d453ff9d4b")
    Observable<NewsBean> getNews(@Query("channelName") String type, @Query("page") int page, @Query("showapi_timestamp") String time);
    @GET("api/4/news/{id}")
    Observable<ZhihuDetailBean> getZhihuDetailNews(@Path("id") String id);
}