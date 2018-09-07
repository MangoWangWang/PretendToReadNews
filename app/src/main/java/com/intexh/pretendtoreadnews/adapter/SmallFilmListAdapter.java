package com.intexh.pretendtoreadnews.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.model.bus.SmallFilmItemBean;
import com.intexh.pretendtoreadnews.utils.GlideHelper;

import java.util.List;

/**
 * Created by AndroidIntexh1 on 2018/9/5.
 */

public class SmallFilmListAdapter extends BaseQuickAdapter<SmallFilmItemBean, BaseViewHolder> {
    public SmallFilmListAdapter(@Nullable List<SmallFilmItemBean> data) {
        super(R.layout.item_film_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SmallFilmItemBean item) {
        //可链式调用赋值
        helper.setText(R.id.tv_film_title, item.getTitle())
        .setText(R.id.tv_film_time,item.getTime()).setText(R.id.tv_film_score,item.getScore());
        ImageView film_image = helper.getView(R.id.iv_film);
        GlideHelper.INSTANCE.loadImage(item.getImg(),film_image);
        //获取当前条目position
        //int position = helper.getLayoutPosition();
    }

}
