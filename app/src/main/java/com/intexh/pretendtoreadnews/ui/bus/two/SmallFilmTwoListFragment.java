package com.intexh.pretendtoreadnews.ui.bus.two;

import android.util.Log;

import com.intexh.pretendtoreadnews.base.app.AppConstants;
import com.intexh.pretendtoreadnews.model.bus.SmallFilmItemBean;
import com.intexh.pretendtoreadnews.ui.bus.base.SmallFilmListFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SmallFilmTwoListFragment extends SmallFilmListFragment {
    @Override
    protected void getFileFromServer() {
        showRefreshing(true);
        baseUrl = getArguments().getString("url");
        if (currentPage!=1)
        {
            baseUrl = baseUrl + "index-" + currentPage +".html";
        }
        subscription = Observable.just(baseUrl).subscribeOn(Schedulers.io()).map(new Func1<String, List<SmallFilmItemBean>>() {
            @Override
            public List<SmallFilmItemBean> call(String url) {
                List<SmallFilmItemBean> Items = new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(baseUrl).timeout(10000).get();
                    Element cate = doc.select("div.vodlist_l").first();
                    Elements lus = cate.select("ul");
                    for (int i = 0 ; i<lus.size();i++) {
                        SmallFilmItemBean smallFilm = new SmallFilmItemBean();
                        Element lu = lus.get(i);
                        // 获取图片
                        smallFilm.setImg(lu.select("img").attr("src"));
                        // 获取标题
                        smallFilm.setTitle(lu.select("h2").select("a").text());
                        // 获取播放地址
                        smallFilm.setUrl(lu.select("a").get(3).attr("href"));
                        // 获取时间
                        smallFilm.setTime(lu.select("p").first().text());
                        smallFilm.setScore("0.0");
                        Items.add(smallFilm);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Items;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<SmallFilmItemBean>>() {
            @Override
            public void onCompleted() {
                isLoading = false;
            }

            @Override
            public void onError(Throwable e) {
                isLoading = false;
                showRefreshing(false);
            }

            @Override
            public void onNext(List<SmallFilmItemBean> Items) {
                Log.e("onNext", "xianduItems: "+ Items.size());
                mLoadingPage.state = AppConstants.STATE_SUCCESS;
                mLoadingPage.showPage();
                currentPage++;
                showRefreshing(false);
                if (adapter.getData() == null || adapter.getData().size() == 0) {
                    adapter.setNewData(Items);
                } else {
                    adapter.addData(adapter.getData().size(), Items);
                }
                mVideoClass = SmallFilmTwoVideoActivity.class;
            }
        });
    }
}
