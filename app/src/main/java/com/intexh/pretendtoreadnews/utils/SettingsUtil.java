package com.intexh.pretendtoreadnews.utils;


import com.intexh.pretendtoreadnews.base.app.App;

/**
 * Created by liyu on 2016/11/18.
 */

public class SettingsUtil {

    public static final String THEME = "theme_color";//主题

    public static void setTheme(int themeIndex) {
        SPUtil.put(App.getAppContext(), THEME, themeIndex);
    }

    public static int getTheme() {
        return (int) SPUtil.get(App.getAppContext(), THEME, 0);
    }
}


