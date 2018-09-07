package com.intexh.pretendtoreadnews.ui.bus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.base.ui.BaseActivity;

public class BusActivity extends BaseActivity {


    private ImageButton ib_one;
    private ImageButton ib_two;
    private ImageButton ib_three;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bus;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("公交");
        ib_one = findView(R.id.ib_video_one);
        ib_two = findView(R.id.ib_video_two);
        ib_three = findView(R.id.ib_video_three);
        ib_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusActivity.this,SmallFilmCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
