package com.intexh.pretendtoreadnews.ui.bus.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.adapter.SmallFilmCategoryAdapter;
import com.intexh.pretendtoreadnews.base.ui.BaseActivity;
import com.intexh.pretendtoreadnews.model.bus.SmallFilmCategory;

import java.util.ArrayList;
import java.util.List;

public abstract class SmallFilmCategoryActivity extends BaseActivity {
    public List<SmallFilmCategory> filmCategories = new ArrayList<>();

    public RecyclerView category_rv;
    public SmallFilmCategoryAdapter mAdapter;
    public String mHost;
    public Class mStartActivity;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bus_category;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        category_rv = findView(R.id.category_rv);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        category_rv.setLayoutManager(layoutManager);
        category_rv.addItemDecoration(new GridDivider(this, 5,  this.getResources().getColor(R.color.grey_hex_a0)));

    }

    @Override
    protected void loadData() {

        loadLocationCategory();
        // 设置视频器
        mAdapter = new SmallFilmCategoryAdapter(filmCategories);
        category_rv.setAdapter(mAdapter);
        // 设置item的点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(SmallFilmCategoryActivity.this,filmCategories.get(position).getUrl(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SmallFilmCategoryActivity.this,mStartActivity);
                intent.putExtra("url",filmCategories.get(position).getUrl());
                intent.putExtra("title",filmCategories.get(position).getName());
                startActivity(intent);
            }
        });

    }

    protected abstract void loadLocationCategory();

}
