package com.intexh.pretendtoreadnews.model.bus;

import java.io.Serializable;

/**
 * Created by AndroidIntexh1 on 2018/9/5.
 */

public class SmallFilmItemBean implements Serializable {
    private String title;
    private String url;
    private String time;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;

    @Override
    public String toString() {
        return "SmallFilmItemBean{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", time='" + time + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    private String score;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
