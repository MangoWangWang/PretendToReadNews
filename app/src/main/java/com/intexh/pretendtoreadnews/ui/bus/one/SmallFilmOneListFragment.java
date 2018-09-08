package com.intexh.pretendtoreadnews.ui.bus.one;

import android.util.Log;

import com.intexh.pretendtoreadnews.base.app.AppConstants;
import com.intexh.pretendtoreadnews.model.bus.SmallFilmItemBean;
import com.intexh.pretendtoreadnews.ui.bus.base.SmallFilmListFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SmallFilmOneListFragment extends SmallFilmListFragment {
    @Override
    protected void getFileFromServer() {
        showRefreshing(true);
        baseUrl = getArguments().getString("url");
        if (currentPage!=1)
        {
            baseUrl = baseUrl + "/" + currentPage +".html";
        }
        subscription = Observable.just(baseUrl).subscribeOn(Schedulers.io()).map(new Func1<String, List<SmallFilmItemBean>>() {
            @Override
            public List<SmallFilmItemBean> call(String url) {
                List<SmallFilmItemBean> Items = new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(baseUrl).timeout(10000).get();
                    Elements cate = doc.select("a.video-pic");
                    Elements time = doc.select("div.subtitle");
                    Elements score = doc.select("span.score");
                    for (int i = 0 ; i<cate.size();i++) {
                        SmallFilmItemBean smallFilm = new SmallFilmItemBean();
                        smallFilm.setTitle(cate.get(i).attr("title"));
                        smallFilm.setUrl(cate.get(i).attr("href"));
                        smallFilm.setImg(cate.get(i).attr("data-original"));
                        smallFilm.setTime(time.get(i).text());
                        smallFilm.setScore(score.get(i).text());
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
            }
        });
    }
}
