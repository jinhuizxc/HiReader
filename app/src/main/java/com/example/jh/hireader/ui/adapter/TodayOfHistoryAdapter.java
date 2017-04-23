package com.example.jh.hireader.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.jh.hireader.R;
import com.example.jh.hireader.bean.TodayOfHistoryBean;
import com.example.jh.hireader.interfaces.OnRecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by jinhui  on 2017/4/23
 * 邮箱: 1004260403@qq.com
 */

public class TodayOfHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context context;
    private final LayoutInflater inflater;
    private List<TodayOfHistoryBean.ResultBean> list;
    private static final int TYPE_NORMAL = 0x00;
    private static final int TYPE_NO_IMG = 0x01;

    private OnRecyclerViewOnClickListener listener;

    public TodayOfHistoryAdapter(Context context, List<TodayOfHistoryBean.ResultBean> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                return new NormalViewHolder(inflater.inflate(R.layout.recyclerview_normal_item, parent, false), listener);
            case TYPE_NO_IMG:
                return new NoImgViewHolder(inflater.inflate(R.layout.recyclerview_no_img_item, parent, false), listener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  NormalViewHolder){
            // 加载图片
            Glide.with(context)
                    .load(list.get(position).getPic())
                    .asBitmap()
                    .placeholder(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.icon_error)
                    .centerCrop()
                    .into(((NormalViewHolder)holder).ivHeadlineImg);

            ((NormalViewHolder)holder).tvTitle.setText(list.get(position).getTitle());
        }else if(holder instanceof NoImgViewHolder){
            ((NoImgViewHolder)holder).tvTitle.setText(list.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getPic()==""||list.get(position).getPic()==null){
            return TYPE_NO_IMG;
        }else{
            return TYPE_NORMAL;
        }
    }

    // 监听item的方法
    public void setItemClickListener(OnRecyclerViewOnClickListener listener){
        this.listener = listener;
    }
    public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView ivHeadlineImg;
        TextView tvTitle;

        OnRecyclerViewOnClickListener listener;

        public NormalViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);
            ivHeadlineImg = (ImageView) itemView.findViewById(R.id.recycleview_normal_imageview);
            tvTitle = (TextView) itemView.findViewById(R.id.recycleview_normal_textview);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null){
                listener.onItemClick(view, getLayoutPosition());
            }
        }
    }

    public class NoImgViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        OnRecyclerViewOnClickListener listener;

        public NoImgViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.recycleview_no_img_textview);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null){
                listener.onItemClick(view, getLayoutPosition());
            }
        }
    }

}
