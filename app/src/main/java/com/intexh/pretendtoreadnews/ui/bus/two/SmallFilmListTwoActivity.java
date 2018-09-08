package com.intexh.pretendtoreadnews.ui.bus.two;

import android.support.v4.app.Fragment;

import com.intexh.pretendtoreadnews.ui.bus.base.SmallFilmListActivity;

public class SmallFilmListTwoActivity extends SmallFilmListActivity {


    @Override
    protected Fragment AddFragment() {
        return new SmallFilmTwoListFragment();
    }
}
