package com.intexh.pretendtoreadnews.ui.bus;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.adapter.SmallFilmListAdapter;
import com.intexh.pretendtoreadnews.base.app.AppConstants;
import com.intexh.pretendtoreadnews.base.ui.BaseContentFragment;
import com.intexh.pretendtoreadnews.model.bus.SmallFilmItemBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by AndroidIntexh1 on 2018/9/5.
 */

public class SmallFilmListFragment extends BaseContentFragment {

    private RecyclerView recyclerView;
    private SmallFilmListAdapter adapter;
    private int currentPage = 1;
    private String baseUrl = "";
    private boolean isLoading = false;

    private Subscription subscription;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initViews() {
        super.initViews();
//        baseUrl = getArguments().getString("url");
        recyclerView = findView(R.id.rv_gank);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new SmallFilmListAdapter( null);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    getFileFromServer();
                }
            }
        });
        recyclerView.addItemDecoration(new GridDivider(getActivity(), 5,  this.getResources().getColor(R.color.grey_hex_a0)));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(),SmallFilmVideoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getFileFromServer() {
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

    @Override
    protected void lazyFetchData() {
        currentPage = 1;
        if (adapter!=null)
        {
            adapter.setNewData(null);
        }
        getFileFromServer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
