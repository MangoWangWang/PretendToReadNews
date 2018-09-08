package com.intexh.pretendtoreadnews.ui.bus.base;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.adapter.SmallFilmListAdapter;
import com.intexh.pretendtoreadnews.base.ui.BaseContentFragment;
import com.intexh.pretendtoreadnews.model.bus.SmallFilmItemBean;
import com.intexh.pretendtoreadnews.ui.bus.SmallFilmVideoActivity;

import rx.Subscription;

/**
 * Created by AndroidIntexh1 on 2018/9/5.
 */

public abstract class SmallFilmListFragment extends BaseContentFragment {

    public RecyclerView recyclerView;
    public SmallFilmListAdapter adapter;
    public int currentPage = 1;
    public String baseUrl = "";
    public boolean isLoading = false;

    public Subscription subscription;

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
                intent.putExtra("url",((SmallFilmItemBean)adapter.getData().get(position)).getUrl());
                intent.putExtra("title",((SmallFilmItemBean)adapter.getData().get(position)).getTitle());
                startActivity(intent);
            }
        });
    }

    protected abstract void getFileFromServer();

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
