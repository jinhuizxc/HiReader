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
import com.example.jh.hireader.bean.GuokrNews;
import com.example.jh.hireader.interfaces.OnRecyclerViewOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinhui  on 2017/4/23
 * 邮箱: 1004260403@qq.com
 */

public class GuokrAdapter extends RecyclerView.Adapter<GuokrAdapter.GuokrViewHolder> {

    private final Context mContext;
    private List<GuokrNews.result> list =new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private OnRecyclerViewOnClickListener mOnRecyclerViewOnClickListener;
    public GuokrAdapter(Context context, List<GuokrNews.result> list){
        this.mContext = context;
        this.list = list;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    @Override
    public GuokrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_normal_item,parent,false);

        return new GuokrViewHolder(view,mOnRecyclerViewOnClickListener);
    }

    @Override
    public void onBindViewHolder(GuokrViewHolder holder, int position) {
        Glide.with(mContext).load(list.get(position).getHeadline_img_tb())
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.icon_error)
                .centerCrop()
                .into(((GuokrViewHolder) holder).imageView);
        holder.textView.setText(list.get(position).getTitle());
    }
    public void setOnItemClick(OnRecyclerViewOnClickListener onRecyclerViewOnClickListener){
        this.mOnRecyclerViewOnClickListener = onRecyclerViewOnClickListener;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GuokrViewHolder extends  RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        private ImageView imageView;
        private TextView textView;
        OnRecyclerViewOnClickListener listener;
        public GuokrViewHolder(View itemView,OnRecyclerViewOnClickListener listener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.recycleview_normal_imageview);
            textView = (TextView) itemView.findViewById(R.id.recycleview_normal_textview);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener!=null){
                listener.onItemClick(v,getLayoutPosition());
            }

        }
    }
}
