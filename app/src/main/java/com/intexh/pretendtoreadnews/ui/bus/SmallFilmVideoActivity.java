package com.intexh.pretendtoreadnews.ui.bus;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.base.app.AppConstants;
import com.intexh.pretendtoreadnews.base.ui.BaseActivity;
import com.intexh.pretendtoreadnews.base.ui.LoadingPage;
import com.intexh.pretendtoreadnews.model.bus.SmallFilmVideoBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.intexh.pretendtoreadnews.base.app.AppConstants.videoService_one;
import static com.intexh.pretendtoreadnews.base.app.AppConstants.videoService_three;
import static com.intexh.pretendtoreadnews.base.app.AppConstants.videoService_two;


public class SmallFilmVideoActivity extends BaseActivity {
    private LoadingPage mLoadingPage;
    private JzvdStd jzvdStd;
    private Subscription subscription;
    private SmallFilmVideoBean mItem;


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
                    jzvdStd.setUp(mItem.getPlayUrl(),
                            mItem.getTitle() , Jzvd.SCREEN_WINDOW_NORMAL );
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

    private void getFileFromServer() {
        String playUrl =  AppConstants.videoOneHost + getIntent().getStringExtra("url");
        subscription = Observable.just(playUrl).subscribeOn(Schedulers.io()).map(new Func1<String, SmallFilmVideoBean>() {
            @Override
            public SmallFilmVideoBean call(String url) {
                SmallFilmVideoBean item = new SmallFilmVideoBean();

                try {
                    Document doc = Jsoup.connect(url).timeout(10000).get();
                    Log.e("songwang", doc.toString() );
                    Elements cates = doc.select("script");
//                    int i = 0;
//                    for (Element element :cates)
//                    {
//                        Log.e("songwang", i+"  "+ element.toString() );
//                        i++;
//
//                    }
                    if (cates.size()>10)
                    {
                        Element element = cates.get(10);
                        String tempUrl = element.data().trim();
//                        tempUrl.substring(tempUrl.indexOf("= \""));
                        Log.e("songwang1",tempUrl);
                        if(TextUtils.isEmpty(tempUrl))
                        {
                            tempUrl = cates.get(9).data().trim();
                        }
                        // 去掉空格
                        tempUrl = tempUrl.replaceAll(" ", "");
                        // 去掉双引号
                        tempUrl = tempUrl.replaceAll("\"", "");
                        // 去掉";
                        tempUrl = tempUrl.replaceAll(";", "");
                        // 根据等号切割字符串
                        String[] ss = tempUrl.split("=");
                        if (ss.length>2)
                        {
                            String replace= "";
                            if(ss[2].contains("CN1"))
                            {
                                replace = ss[2].replace("+CN1+", videoService_one);
                            }else if (ss[2].contains("CN2"))
                            {
                                replace = ss[2].replace("+CN2+", videoService_two);

                            }else if (ss[2].contains("CN3"))
                            {
                                replace = ss[2].replace("+CN3+", videoService_three);
                            }

                            item.setTitle(getIntent().getStringExtra("title"));
                            item.setPlayUrl(replace);
                            Log.e("songwang1",replace);
                        }else
                        {
                            Log.e("songwang1","解析出错");
                        }

                    }

                } catch (IOException e) {
                    Log.e("songwang",e.toString());
                    e.printStackTrace();
                }
                return item;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SmallFilmVideoBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                mLoadingPage.state = AppConstants.STATE_ERROR;
                mLoadingPage.showPage();
                Log.e("songwang",e.toString());
            }

            @Override
            public void onNext(SmallFilmVideoBean item) {
                mItem = item;
                mLoadingPage.state = AppConstants.STATE_SUCCESS;
                mLoadingPage.showPage();
            }
        });
    }


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
