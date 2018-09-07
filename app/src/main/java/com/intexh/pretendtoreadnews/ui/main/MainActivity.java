package com.intexh.pretendtoreadnews.ui.main;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.LinearLayout;

import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.ui.easyRead.EasyReadActivity;
import com.intexh.pretendtoreadnews.ui.bus.BusActivity;
import com.intexh.pretendtoreadnews.utils.DoubleClickExit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;
    private LinearLayout ll_esay_read;
    private LinearLayout ll_bus;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);  // 启动支持vector图像
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.vp_content);
        mDrawerLayout = findViewById(R.id.dl_layout);
        ll_esay_read = findViewById(R.id.home_easy_read);
        ll_bus = findViewById(R.id.home_bus);
        initView();
        initListener();
    }
    private void initView() {
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return null;
            }
        });
    }

    private void initListener()
    {
        ll_esay_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EasyReadActivity.class);
                startActivity(intent);
            }
        });
        ll_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BusActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * 主页面返回键监听
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (!DoubleClickExit.check()) {
                Snackbar.make(MainActivity.this.getWindow().getDecorView().findViewById(android.R.id.content), "再按一次退出 App!", Snackbar.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
                finish();
            }
        }
    }
}