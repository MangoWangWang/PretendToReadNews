package com.intexh.pretendtoreadnews.ui.bus.one;

import android.text.TextUtils;
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

import static com.intexh.pretendtoreadnews.base.app.AppConstants.videoService_one;
import static com.intexh.pretendtoreadnews.base.app.AppConstants.videoService_three;
import static com.intexh.pretendtoreadnews.base.app.AppConstants.videoService_two;

public class SmallFilmOneVideoActivity extends SmallFilmVideoActivity {


    @Override
    protected void getFileFromServer() {
        String playUrl =  AppConstants.videoOneHost + getIntent().getStringExtra("url");
        subscription = Observable.just(playUrl).subscribeOn(Schedulers.io()).map(new Func1<String, SmallFilmVideoBean>() {
            @Override
            public SmallFilmVideoBean call(String url) {
                SmallFilmVideoBean item = new SmallFilmVideoBean();

                try {
                    Document doc = Jsoup.connect(url).timeout(10000).get();
                    Log.e("songwang", doc.toString() );
                    Elements cates = doc.select("script");
                    Element tauger ;
                    if(cates.size()>0)
                    {
                        for (Element element:cates)
                        {
                            if (!TextUtils.isEmpty(element.data()))
                            {
                                if (element.data().contains("CN"))
                                {
                                    String tempUrl = element.data().trim();
                                    // 去掉空格
                                    tempUrl = tempUrl.replaceAll(" ", "");
                                    // 去掉双引号
                                    tempUrl = tempUrl.replaceAll("\"", "");
                                    // 去掉";
                                    tempUrl = tempUrl.replaceAll(";", "");
                                    // 去掉换行符
//                                   tempUrl = tempUrl.replaceAll("/n","");
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
                                        break;
                                    }else
                                    {
                                        Log.e("songwang1","解析出错");
                                        break;
                                    }
                                }
                            }
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
}
