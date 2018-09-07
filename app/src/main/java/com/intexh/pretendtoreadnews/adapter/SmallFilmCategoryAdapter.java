package com.intexh.pretendtoreadnews.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.model.bus.SmallFilmCategory;

import java.util.List;

/**
 * Created by AndroidIntexh1 on 2018/9/5.
 */

public class SmallFilmCategoryAdapter extends BaseQuickAdapter<SmallFilmCategory, BaseViewHolder> {
    public SmallFilmCategoryAdapter(@Nullable List<SmallFilmCategory> data) {
        super(R.layout.item_category, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SmallFilmCategory item) {
        //可链式调用赋值
        helper.setText(R.id.tv_item_category, item.getName());

        //获取当前条目position
        //int position = helper.getLayoutPosition();
    }

}
