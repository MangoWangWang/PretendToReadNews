package com.intexh.pretendtoreadnews.ui.bus.two;

import android.util.Log;

import com.intexh.pretendtoreadnews.base.app.AppConstants;
import com.intexh.pretendtoreadnews.model.bus.SmallFilmVideoBean;
import com.intexh.pretendtoreadnews.ui.bus.base.SmallFilmVideoActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SmallFilmTwoVideoActivity extends SmallFilmVideoActivity {

    @Override
    protected void getFileFromServer() {
        String playUrl = AppConstants.videoTwoHost + getIntent().getStringExtra("url");
        subscription = Observable.just(playUrl).subscribeOn(Schedulers.io()).map(new Func1<String, SmallFilmVideoBean>() {
            @Override
            public SmallFilmVideoBean call(String url) {
                SmallFilmVideoBean item = new SmallFilmVideoBean();

                try {
                    Document doc = Jsoup.connect(url).timeout(10000).get();
                    Log.e("songwang", doc.toString());
                    Elements cates = doc.select("script");
                    Element tagur;
                    if (cates.size() > 0) {
                        for (Element data : cates) {
                            if (data.data().contains("ff_urls")) {
                                tagur = data;
                                String[] split = tagur.data().split(",");
                                if (split.length >= 8) {
                                    String s = split[6].replaceAll("\"", "");
                                    s = s.replace("\\", "/");
                                    item.setPlayUrl(s);
                                    item.setTitle(getIntent().getStringExtra("title"));
                                    break;
                                }

                            }
                        }
                    }

                } catch (IOException e) {
                    Log.e("songwang", e.toString());
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
                Log.e("songwang", e.toString());
            }

            @Override
            public void onNext(SmallFilmVideoBean item) {
                mItem = item;
                mLoadingPage.state = AppConstants.STATE_SUCCESS;
                mLoadingPage.showPage();
            }
        });
    }
}
