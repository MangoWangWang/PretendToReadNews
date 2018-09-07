package com.intexh.pretendtoreadnews.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.adapter.ZhiHuAdapter;
import com.intexh.pretendtoreadnews.base.app.AppConstants;
import com.intexh.pretendtoreadnews.base.ui.BaseFragment;
import com.intexh.pretendtoreadnews.model.zhihu.HomeListBean;
import com.intexh.pretendtoreadnews.http.ApiFactory;
import com.intexh.pretendtoreadnews.model.zhihu.DailyListBean;
import com.intexh.pretendtoreadnews.model.zhihu.HotListBean;
import com.intexh.pretendtoreadnews.model.zhihu.SectionListBean;
import com.intexh.pretendtoreadnews.model.zhihu.ThemeListBean;
import com.intexh.pretendtoreadnews.utils.GlideHelper;
import com.intexh.pretendtoreadnews.utils.SPUtils;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by AndroidIntexh1 on 2018/8/28.
 */

public class HomeFragment extends BaseFragment {

    private RecyclerView rvZhihu;
    private MZBannerView banner;
    private List<HomeListBean> homeList = new ArrayList<>();
    private List<DailyListBean.TopStoriesBean> topStoriesList;
    private ZhiHuAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        rvZhihu = (RecyclerView) findView(R.id.rv_home);
    }

    @Override
    protected void lazyFetchData() {
        ApiFactory
                .getZhuHuController()
                .fetchDailyList()
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<DailyListBean, Observable<HotListBean>>() {
                    @Override
                    public Observable<HotListBean> call(DailyListBean dailyListBean) {
                        Log.e("onNext", "onNext: " + "知乎日报");
                        topStoriesList = dailyListBean.getTop_stories();
                        List<DailyListBean.StoriesBean> storiesBeanList = dailyListBean.getStories();
                        settitle("知乎日报");
                        for (int i = 0; i < 3; i++) {
                            HomeListBean homeListBean = settype(1);
                            homeListBean.setDailyList(storiesBeanList.get(i));
                            homeList.add(homeListBean);
                        }
                        return ApiFactory.getZhuHuController().fetchHotList();
                    }
                })
                .flatMap(new Func1<HotListBean, Observable<ThemeListBean>>() {
                    @Override
                    public Observable<ThemeListBean> call(HotListBean hotListBean) {
                        Log.e("onNext", "onNext: " + "知乎热门");
                        List<HotListBean.RecentBean> recent = hotListBean.getRecent();
                        settitle("知乎热门");
                        List<HotListBean.RecentBean> hotList = new ArrayList<>();
                        List<HotListBean.RecentBean> hotList2 = new ArrayList<>();
                        for (int i = 0; i < 6; i++) {
                            if (i < 3) {
                                hotList.add(recent.get(i));
                            } else {
                                hotList2.add(recent.get(i));
                            }
                        }
                        HomeListBean homeListBean = settype(2);
                        homeListBean.setHotList(hotList);
                        homeList.add(homeListBean);
                        HomeListBean homeListBean2 = settype(2);
                        homeListBean2.setHotList(hotList2);
                        homeList.add(homeListBean2);
                        return ApiFactory.getZhuHuController().fetchThemeList();
                    }
                })
                .flatMap(new Func1<ThemeListBean, Observable<SectionListBean>>() {
                    @Override
                    public Observable<SectionListBean> call(ThemeListBean themeListBean) {
                        Log.e("onNext", "onNext: " + "知乎主题");
                        List<ThemeListBean.OthersBean> others = themeListBean.getOthers();
                        settitle("知乎主题");
                        List<ThemeListBean.OthersBean> themeList = new ArrayList<>();
                        List<ThemeListBean.OthersBean> themeList2 = new ArrayList<>();
                        int random = new Random().nextInt(4);
                        for (int i = random; i < random + 4; i++) {
                            if (i < random + 2) {
                                themeList.add(others.get(i));
                            } else {
                                themeList2.add(others.get(i));
                            }
                        }
                        HomeListBean homeListBean = settype(3);
                        homeListBean.setThemeList(themeList);
                        homeList.add(homeListBean);
                        HomeListBean homeListBean2 = settype(3);
                        homeListBean2.setThemeList(themeList2);
                        homeList.add(homeListBean2);
                        return ApiFactory.getZhuHuController().fetchSectionList();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SectionListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SectionListBean sectionListBean) {
                        Log.e("onNext", "onNext: " + "知乎专栏");
                        List<SectionListBean.DataBean> data1 = sectionListBean.getData();
                        settitle("知乎专栏");
                        List<SectionListBean.DataBean> sectionList = new ArrayList<>();
                        int random = new Random().nextInt(4);
                        for (int i = random; i < random + 3; i++) {
                            sectionList.add(data1.get(i));
                        }
                        HomeListBean homeListBean = settype(4);
                        homeListBean.setSectionList(sectionList);
                        homeList.add(homeListBean);
                        mLoadingPage.state = AppConstants.STATE_SUCCESS;
                        mLoadingPage.showPage();
                        refreshView(getHomeList());
                    }
                });
    }


    public void refreshView(List<HomeListBean> mHomeList) {
        homeList = mHomeList;
        Log.e("refreshView", "refreshView: "+homeList.size() );
        if (homeList.size() == 12) {
            View headerView = getActivity().getLayoutInflater().inflate(R.layout.item_zhihu_header, (ViewGroup) rvZhihu.getParent(), false);
            banner = (MZBannerView) headerView.findViewById(R.id.banner);
            ImageButton ibXiandu = (ImageButton) headerView.findViewById(R.id.ib_xiandu);
            ibXiandu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    WebViewActivity.loadUrl(getActivity(), "https://gank.io/xiandu", "加载中...");

                }
            });
            initBanner(topStoriesList);
            mAdapter = new ZhiHuAdapter(null);
            mAdapter.setNewData(homeList);
            mAdapter.addHeaderView(headerView);
            rvZhihu.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvZhihu.setAdapter(mAdapter);
            ((ZhiHuAdapter) mAdapter).setOnItemClickListener(new ZhiHuAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(int id, View view) {
//                    startZhiHuDetailActivity(id, view);
                }

                @Override
                public void OnItemThemeClickListener(int id, View view) {
//                    startZhihuThemeActivity("id", id,view);
                }

                @Override
                public void OnItemSectionClickListener(int id, View view) {
//                    startZhihuThemeActivity("section_id", id,view);
                }
            });
        }
    }

    private void initBanner(List<DailyListBean.TopStoriesBean> topStoriesList) {
        banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(getContext(), "click page:" + position, Toast.LENGTH_LONG).show();
            }
        });
        banner.setIndicatorVisible(true);
        // 设置indicator图标
        banner.setIndicatorRes(R.mipmap.indicator_un_selected,R.mipmap.indicator_selected);
        banner.setPages(topStoriesList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        banner.getViewPager().setPageTransformer(false,new MyScaleYTransformer());
        banner.start();
    }

    public static class BannerViewHolder implements MZViewHolder<DailyListBean.TopStoriesBean> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, DailyListBean.TopStoriesBean data) {
            // 数据绑定
//            GlideHelper.INSTANCE.loadImage(1, data.getImage(), mImageView);
            GlideHelper.INSTANCE.loadImage(mImageView,data.getImage(),8);
        }
    }


    private void settitle(String title) {
        HomeListBean homeListBean = settype(0);
        homeListBean.setTitle(title);
        homeList.add(homeListBean);
    }

    @NonNull
    private HomeListBean settype(int type) {
        HomeListBean homeListBean = new HomeListBean();
        homeListBean.setType(type);
        return homeListBean;
    }

    public List<HomeListBean> getHomeList() {
        List<HomeListBean> newHomeList = new ArrayList<>();
        int daily = 0;
        int hot = 0;
        int theme = 0;
        int section = 0;
        SPUtils spUtils = new SPUtils("home_list");
        String homeListString = spUtils.getString("home_list");
        if (TextUtils.isEmpty(homeListString))
        {
            homeListString = "知乎日报&&知乎热门&&知乎主题&&知乎专栏";
            spUtils.putString("home_list",homeListString);
        }
        String[] split = homeListString.split("&&");
        for (int i = 0; i < split.length; i++) {
            if ("知乎日报".equals(split[i])) {
                daily = i + 1;
                continue;
            }
            if ("知乎热门".equals(split[i])) {
                hot = i + 1;
                continue;
            }
            if ("知乎主题".equals(split[i])) {
                theme = i + 1;
                continue;
            }
            if ("知乎专栏".equals(split[i])) {
                section = i + 1;
                continue;
            }
        }
        for (int y = 1; y <= 4; y++) {
            if (daily == y) {
                checkAddToNewHomeList("知乎日报", 1, newHomeList);
                continue;
            }
            if (hot == y) {
                checkAddToNewHomeList("知乎热门", 2, newHomeList);
                continue;
            }
            if (theme == y) {
                checkAddToNewHomeList("知乎主题", 3, newHomeList);
                continue;
            }
            if (section == y) {
                checkAddToNewHomeList("知乎专栏", 4, newHomeList);
                continue;
            }
        }
        return newHomeList;
    }

    private void checkAddToNewHomeList(String title, int type, List<HomeListBean> newHomeList) {
        for (int i = 1; i <= homeList.size(); i++) {
            if (!TextUtils.isEmpty(title) &&
                    title.equals(homeList.get(i - 1).getTitle())) {
                newHomeList.add(homeList.get(i - 1));
            }

            if (homeList.get(i - 1).getType() == type) {
                newHomeList.add(homeList.get(i - 1));
            }
        }
    }

    private class MyScaleYTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 1.0F;

        public MyScaleYTransformer() {
        }

        public void transformPage(View page, float position) {
//            if(position < -1.0F) {
//                page.setScaleY(0.9F);
//            } else if(position <= 1.0F) {
//                float scale = Math.max(0.9F, 1.0F - Math.abs(position));
//                page.setScaleY(scale);
//            } else {
//                page.setScaleY(0.9F);
//            }

        }
    }


}

