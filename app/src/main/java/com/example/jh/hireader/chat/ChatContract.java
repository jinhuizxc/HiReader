package com.example.jh.hireader.chat;

import com.example.jh.hireader.base.BasePresenter;
import com.example.jh.hireader.base.BaseView;
import com.example.jh.hireader.chat.bean.ChatMsgBean;

/**
 * Created by jinhui  on 2017/4/20
 * 邮箱: 1004260403@qq.com
 */

public interface ChatContract {
    interface View extends BaseView<Presenter> {
        void showError();
        void showReslut(ChatMsgBean list);
    }
    interface Presenter extends BasePresenter {
        void requestData(String data);

        void sendMsg(String data);
    }
}
