package com.intexh.pretendtoreadnews.ui.bus.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.base.ui.BaseActivity;

public abstract class SmallFilmListActivity extends BaseActivity {

    private String url;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_small_film_list;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        url = getIntent().getStringExtra("url");
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        }

    }

    @Override
    protected void loadData() {
        // 把fragment比喻成为数据存储到fragment
        Fragment fragment = AddFragment();
        Bundle data = new Bundle();
        data.putString("url", url);
        fragment.setArguments(data);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment,fragment);
        fragmentTransaction.commit();
    }


    protected abstract Fragment AddFragment();
}
