package com.intexh.pretendtoreadnews.utils;

/**
 * Created by AndroidIntexh1 on 2018/8/31.
 */


public class DoubleClickExit {
    public static long mLastClick = 0L;
    private static final int THRESHOLD = 2000;

    public static boolean check() {
        long now = System.currentTimeMillis();
        boolean b = now - mLastClick < THRESHOLD;
        mLastClick = now;
        return b;
    }
}
