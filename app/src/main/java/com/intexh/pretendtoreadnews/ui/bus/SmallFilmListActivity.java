package com.intexh.pretendtoreadnews.ui.bus;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.base.ui.BaseActivity;
import com.intexh.pretendtoreadnews.ui.easyRead.CategoryFragment;

public class SmallFilmListActivity extends BaseActivity {

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
        Fragment fragment = new SmallFilmListFragment();
        Bundle data = new Bundle();
        data.putString("url", url);
        fragment.setArguments(data);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment,fragment);
        fragmentTransaction.commit();
    }


}
