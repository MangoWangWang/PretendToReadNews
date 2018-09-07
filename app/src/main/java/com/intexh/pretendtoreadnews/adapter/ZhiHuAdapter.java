package com.intexh.pretendtoreadnews.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.model.zhihu.HomeListBean;
import com.intexh.pretendtoreadnews.utils.GlideHelper;

import java.util.List;


/**
 * Created by quantan.liu on 2017/3/23.
 * 多类型adapter
 */

public class ZhiHuAdapter extends BaseMultiItemQuickAdapter<HomeListBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    public ZhiHuAdapter(List<HomeListBean> data) {
        super(data);
        addItemType(HomeListBean.TITLE, R.layout.item_zhihu_title);
        addItemType(HomeListBean.DAILY, R.layout.item_zhihu_daily);
        addItemType(HomeListBean.HOT, R.layout.item_zhihu_hot);
        addItemType(HomeListBean.THEME, R.layout.item_zhihu_theme);
        addItemType(HomeListBean.SECTION, R.layout.item_zhihu_saction);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HomeListBean item) {
        switch (helper.getItemViewType()) {
            case HomeListBean.TITLE:
                helper.setText(R.id.tv_title, item.getTitle());
                break;
            case HomeListBean.DAILY:
                helper.setText(R.id.tv_daily_dec, item.getDailyList().getTitle());
                 GlideHelper.INSTANCE.loadImage(2, item.getDailyList().getImages().get(0), (ImageView) helper.getView(R.id.iv_daily),4);
                helper.getView(R.id.ll_section).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClickListener(item.getDailyList().getId(), helper.getView(R.id.iv_daily));
                    }
                });
                break;
            case HomeListBean.HOT:
//                String[] splits1 = item.getHotList().get(0).getTitle().split("·");
//                String[] splits2 = item.getHotList().get(1).getTitle().split("·");
//                String[] splits3 = item.getHotList().get(2).getTitle().split("·");
                helper.setText(R.id.tv_three_one_one_title,  item.getHotList().get(0).getTitle());
                helper.setText(R.id.tv_three_one_two_title, item.getHotList().get(1).getTitle());
                helper.setText(R.id.tv_three_one_three_title, item.getHotList().get(2).getTitle());
                 GlideHelper.INSTANCE.loadImage(3, item.getHotList().get(0).getThumbnail(), (ImageView) helper.getView(R.id.iv_three_one_one));
                 GlideHelper.INSTANCE.loadImage(3, item.getHotList().get(1).getThumbnail(), (ImageView) helper.getView(R.id.iv_three_one_two));
                 GlideHelper.INSTANCE.loadImage(3, item.getHotList().get(2).getThumbnail(), (ImageView) helper.getView(R.id.iv_three_one_three));
                onItemClick(helper, R.id.iv_three_one_one, item.getHotList().get(0).getNews_id());
                onItemClick(helper, R.id.iv_three_one_two, item.getHotList().get(1).getNews_id());
                onItemClick(helper, R.id.iv_three_one_three, item.getHotList().get(2).getNews_id());
                break;
            case HomeListBean.THEME:
                helper.setText(R.id.tv_two_one_one_title, item.getThemeList().get(0).getDescription());
                helper.setText(R.id.tv_two_one_two_title, item.getThemeList().get(1).getDescription());
                 GlideHelper.INSTANCE.loadImage(3, item.getThemeList().get(0).getThumbnail(), (ImageView) helper.getView(R.id.iv_two_one_one));
                 GlideHelper.INSTANCE.loadImage(3, item.getThemeList().get(1).getThumbnail(), (ImageView) helper.getView(R.id.iv_two_one_two));
                OnItemThemeClick(helper, R.id.iv_two_one_one, item.getThemeList().get(0).getId());
                OnItemThemeClick(helper, R.id.iv_two_one_two, item.getThemeList().get(1).getId());
                break;
            case HomeListBean.SECTION:
                helper.setText(R.id.tv_one_photo_title, item.getSectionList().get(0).getName());
                helper.setText(R.id.tv_two_one_one_title, item.getSectionList().get(1).getName());
                helper.setText(R.id.tv_two_one_two_title, item.getSectionList().get(2).getName());
                 GlideHelper.INSTANCE.loadImage(3, item.getSectionList().get(0).getThumbnail(), (ImageView) helper.getView(R.id.iv_one_photo));
                 GlideHelper.INSTANCE.loadImage(3, item.getSectionList().get(1).getThumbnail(), (ImageView) helper.getView(R.id.iv_two_one_one));
                 GlideHelper.INSTANCE.loadImage(3, item.getSectionList().get(2).getThumbnail(), (ImageView) helper.getView(R.id.iv_two_one_two));
                OnItemSectionClick(helper, R.id.iv_one_photo, item.getSectionList().get(0).getId());
                OnItemSectionClick(helper, R.id.iv_two_one_one, item.getSectionList().get(1).getId());
                OnItemSectionClick(helper, R.id.iv_two_one_two, item.getSectionList().get(2).getId());
                break;
        }
    }

    /**
     * 抽取成一个方法不然每一个都要重新写setOnClickListener(new View.OnClickListener()
     *
     * @param helper
     * @param id
     * @param urlId
     */
    public void onItemClick(BaseViewHolder helper, int id, final int urlId) {
        helper.getView(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(urlId, v);
            }
        });
    }

    public void OnItemThemeClick(BaseViewHolder helper, int id, final int urlId) {
        helper.getView(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemThemeClickListener(urlId, v);
            }
        });
    }

    public void OnItemSectionClick(BaseViewHolder helper, int id, final int urlId) {
        helper.getView(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemSectionClickListener(urlId, v);
            }
        });
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int id, View view);

        void OnItemThemeClickListener(int id, View view);

        void OnItemSectionClickListener(int id, View view);
    }

}
