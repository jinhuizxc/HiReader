package com.example.jh.hireader.presenter;

import android.content.Context;

import com.example.jh.hireader.api.Api;
import com.example.jh.hireader.api.ApiService;
import com.example.jh.hireader.chat.ChatContract;
import com.example.jh.hireader.chat.bean.ChatBean;
import com.example.jh.hireader.chat.bean.ChatMsgBean;
import com.example.jh.hireader.utils.HttpUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinhui  on 2017/4/20
 * 邮箱: 1004260403@qq.com
 */

public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.View view;

    public ChatPresenter(Context context, ChatContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    // 请求聊天的数据资源
    @Override
    public void requestData(String data) {
        HttpUtils.getInstance()
                .create(ApiService.class, Api.ROBOT)
                .getChatData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ChatBean>() {
                    @Override
                    public void accept(ChatBean chatBean) throws Exception {
                        String msg = chatBean.getText();
                        ChatMsgBean chatMsgBean = new ChatMsgBean();
                        chatMsgBean.setMsg(msg);
                        chatMsgBean.setType(ChatMsgBean.TYPE_RECIV);
                        view.showReslut(chatMsgBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError();
                    }
                });
    }

    @Override
    public void sendMsg(String data) {
        requestData(data);
    }
}
