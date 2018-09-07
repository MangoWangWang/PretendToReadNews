package com.intexh.pretendtoreadnews.ui.bus;

import android.content.Intent;
import android.content.res.Resources;
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

public class SmallFilmCategoryActivity extends BaseActivity {
    private List<SmallFilmCategory> filmCategories = new ArrayList<>();

    private RecyclerView category_rv;
    private SmallFilmCategoryAdapter mAdapter;

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
        getSupportActionBar().setTitle("观影区一");
        category_rv = findView(R.id.category_rv);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        category_rv.setLayoutManager(layoutManager);
        category_rv.addItemDecoration(new GridDivider(this, 5,  this.getResources().getColor(R.color.grey_hex_a0)));

    }

    @Override
    protected void loadData() {
        // 加载本地数据
        Resources res = getResources();
        String host = "https://www.4461dd.com/html/";
        String[] film_category = res.getStringArray(R.array.small_one_category);
        String[] film_category_url = res.getStringArray(R.array.small_one_url);
        // 遍历分类内容
        for (int i = 0 ;i<film_category.length;i++)
        {
            SmallFilmCategory temp = new SmallFilmCategory();
            temp.setName(film_category[i]);
            temp.setUrl(host + film_category_url[i]);
            filmCategories.add(temp);
        }
        if (filmCategories == null||filmCategories.size()==0)
        {
            finish();
        }
        // 设置视频器
        mAdapter = new SmallFilmCategoryAdapter(filmCategories);
        category_rv.setAdapter(mAdapter);
        // 设置item的点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(SmallFilmCategoryActivity.this,filmCategories.get(position).getUrl(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SmallFilmCategoryActivity.this,SmallFilmListActivity.class);
                intent.putExtra("url",filmCategories.get(position).getUrl());
                intent.putExtra("title",filmCategories.get(position).getName());
                startActivity(intent);
            }
        });

    }
}
