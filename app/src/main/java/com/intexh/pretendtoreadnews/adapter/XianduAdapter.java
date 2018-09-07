package com.intexh.pretendtoreadnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.intexh.pretendtoreadnews.R;
import com.intexh.pretendtoreadnews.model.EasyRead.XianduItem;
import com.intexh.pretendtoreadnews.utils.GlideHelper;
import com.intexh.pretendtoreadnews.utils.WebUtils;

import java.util.List;

/**
 * Created by AndroidIntexh1 on 2018/9/5.
 */


public class XianduAdapter extends RecyclerView.Adapter<XianduAdapter.XianViewHolder> {

    private List<XianduItem> xiandus;
    private Context context;
    private LayoutInflater inflater;

    public XianduAdapter(Context context, List<XianduItem> list) {
        this.context = context;
        this.xiandus = list;
        this.inflater = LayoutInflater.from(context);
    }

    public void setNewData(List<XianduItem> data) {
        this.xiandus = data;
        notifyDataSetChanged();
    }

    public List<XianduItem> getData() {
        return xiandus;
    }

    public void addData(int position, List<XianduItem> data) {
        this.xiandus.addAll(position, data);
        this.notifyItemRangeInserted(position, data.size());
    }

    @Override
    public XianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_xiandu, parent, false);
        XianViewHolder holder = new XianViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final XianViewHolder holder, int position) {
        final XianduItem item = xiandus.get(position);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebUtils.openInternal(context, item.getUrl());
            }
        });
        holder.tv_name.setText(String.format("%s. %s", position + 1, item.getName()));
        holder.tv_info.setText(item.getUpdateTime() + " • " + item.getFrom());
        GlideHelper.INSTANCE.loadCircleImage(holder.iv,item.getIcon());
    }

    @Override
    public int getItemCount() {
        return xiandus == null ? 0 : xiandus.size();
    }

    @Override
    public long getItemId(int position) {
        return xiandus.get(position).hashCode();
    }

    class XianViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_info;
        ImageView iv;
        View rootView;

        public XianViewHolder(View view) {
            super(view);
            rootView = view;
            iv = (ImageView) view.findViewById(R.id.iv_xiantu_icon);
            tv_name = (TextView) view.findViewById(R.id.tv_xiandu_name);
            tv_info = (TextView) view.findViewById(R.id.tv_xiandu_info);
        }

    }

}
