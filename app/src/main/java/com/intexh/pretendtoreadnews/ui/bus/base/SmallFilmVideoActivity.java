package com.intexh.pretendtoreadnews.ui.bus.base;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.base.app.AppConstants;
import com.intexh.pretendtoreadnews.base.ui.BaseActivity;
import com.intexh.pretendtoreadnews.base.ui.LoadingPage;
import com.intexh.pretendtoreadnews.model.bus.SmallFilmVideoBean;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import rx.Subscription;


public abstract class SmallFilmVideoActivity extends BaseActivity {
    public LoadingPage mLoadingPage;
    public JzvdStd jzvdStd;
    public TextView tv_playUrl;
    public Subscription subscription;
    public SmallFilmVideoBean mItem;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_small_film_video;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        // 加载初始布局的framelayout
        FrameLayout baseFl = findView(R.id.fl_contentView);
        if (mLoadingPage == null)
        {
            // 初始化数据加载布局
            mLoadingPage = new LoadingPage(this) {
                @Override
                protected void initView() {
                    // loadingPage中调用,实例化真正的控件
                    jzvdStd = contentView.findViewById(R.id.videoplayer);
                    tv_playUrl = contentView.findViewById(R.id.playURL);
                    jzvdStd.setUp(mItem.getPlayUrl(),
                            mItem.getTitle() , Jzvd.SCREEN_WINDOW_NORMAL );
                    tv_playUrl.setText(mItem.getPlayUrl());
//                    jzvdStd.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
                }

                @Override
                protected void loadData() {
                    // 加载错误页面点击调用的方法 一般调用fragment或activity中的loadData
                    this.loadData();

                }

                @Override
                protected int getLayoutId() {
                    // 返回需要显示的实际内容布局,用于数据请求完成后loadPage关闭后实例内容布局
                    return R.layout.item_video;
                }
            };
        }

        baseFl.addView(mLoadingPage);


    }

    @Override
    protected void loadData() {
            mLoadingPage.state = AppConstants.STATE_LOADING;
            mLoadingPage.showPage();
        getFileFromServer();



    }

    protected abstract void getFileFromServer();




    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    // 播放控制器进行销毁
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}
