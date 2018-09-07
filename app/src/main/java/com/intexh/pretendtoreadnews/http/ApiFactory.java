package com.intexh.pretendtoreadnews.http;


import com.intexh.pretendtoreadnews.http.api.GirlsController;
import com.intexh.pretendtoreadnews.http.api.ZhuHuController;

/**
 * Created by liyu on 2016/8/25.
 */
public class ApiFactory {
    protected static final Object monitor = new Object();

    private static GirlsController girlsController;
    private static ZhuHuController zhiHuController;

    public static GirlsController getGirlsController() {
        if (girlsController == null) {
            synchronized (monitor) {
                girlsController = RetrofitManager.getInstance("").create(GirlsController.class);
            }
        }
        return girlsController;
    }
    public static ZhuHuController getZhuHuController() {
        if (zhiHuController == null) {
            synchronized (monitor) {
                zhiHuController = RetrofitManager.getInstance(ZhuHuController.HOST).create(ZhuHuController.class);
            }
        }
        return zhiHuController;
    }


    public static void reset() {
        girlsController = null;
        zhiHuController = null;

            }


}
