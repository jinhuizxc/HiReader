package com.example.jh.hireader.chat.bean;

/**
 * Created by jinhui  on 2017/4/20
 * 邮箱: 1004260403@qq.com
 */

public class ChatMsgBean {

    public static final int TYPE_SEND =1;
    public static final int TYPE_RECIV=2;
    private String msg;
    private int type;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
