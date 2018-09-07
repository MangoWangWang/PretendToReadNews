package com.intexh.pretendtoreadnews.model.bus;

import java.io.Serializable;

/**
 * Created by AndroidIntexh1 on 2018/9/5.
 */

public class SmallFilmCategory implements Serializable {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
