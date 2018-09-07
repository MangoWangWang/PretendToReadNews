package com.intexh.pretendtoreadnews.ui.bus;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.base.app.AppConstants;
import com.intexh.pretendtoreadnews.base.ui.BaseActivity;
import com.intexh.pretendtoreadnews.base.ui.LoadingPage;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class SmallFilmVideoActivity extends BaseActivity {
    private LoadingPage mLoadingPage;
    private JzvdStd jzvdStd;


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
        FrameLayout baseFl = findView(R.id.fl_contentView);
        if (mLoadingPage == null)
        {
            mLoadingPage = new LoadingPage(this) {
                @Override
                protected void initView() {
                    jzvdStd = contentView.findViewById(R.id.videoplayer);
                    jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4",
                            "饺子闭眼睛" , Jzvd.SCREEN_WINDOW_NORMAL );
//                    jzvdStd.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
                }

                @Override
                protected void loadData() {

                }

                @Override
                protected int getLayoutId() {
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

    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

}
