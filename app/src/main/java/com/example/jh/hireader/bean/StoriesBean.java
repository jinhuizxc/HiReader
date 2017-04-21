package com.example.jh.hireader.bean;

import java.util.List;

/**
 * Created by jinhui  on 2017/4/21
 * 邮箱: 1004260403@qq.com
 */

public class StoriesBean {
    /**
     * images : ["http://pic2.zhimg.com/7ee215a4fb0b4c0b399a5c7f68749625.jpg"]
     * type : 0
     * id : 9165425
     * ga_prefix : 012122
     * title : 小事 · 被家教性骚扰
     * multipic : true
     */
    private int type;
    private int id;
    private String title;
    private String ga_prefix;
    private List<String> images;
    private boolean multipic;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

    @Override
    public String toString() {
        return "StoriesBean{" +
                "type=" + type +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", images=" + images +
                ", multipic=" + multipic +
                '}';
    }
}
