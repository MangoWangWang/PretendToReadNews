package com.intexh.pretendtoreadnews.ui.bus.one;

import android.support.v4.app.Fragment;

import com.intexh.pretendtoreadnews.ui.bus.base.SmallFilmListActivity;

public class SmallFilmOneListActivity extends SmallFilmListActivity {


    @Override
    protected Fragment AddFragment() {
        return new SmallFilmOneListFragment();
    }
}
