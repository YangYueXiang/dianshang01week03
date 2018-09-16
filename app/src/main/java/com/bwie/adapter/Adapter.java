package com.bwie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.week03.MainActivity;
import com.bwie.week03.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import bean.Bean;

/**
 * Created by YangYueXiang
 * on 2018/9/15
 */
public class Adapter extends RecyclerView.Adapter<Adapter.oneholder> {
    private Context context;
    private List<Bean.DataBean> list;

    public Adapter(Context context, List<Bean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public oneholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.clude1, null);
        oneholder oneholder = new oneholder(view);
        return oneholder;
    }

    @Override
    public void onBindViewHolder(@NonNull oneholder holder, int position) {
        holder.tv_title.setText(list.get(position).getType());
        Uri uri = Uri.parse(list.get(position).getUrl());
        holder.img_01.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建视图
    class oneholder extends RecyclerView.ViewHolder{

        private final SimpleDraweeView img_01;
        private final TextView tv_title;

        public oneholder(View itemView) {
            super(itemView);
            img_01 = itemView.findViewById(R.id.img_01);
            tv_title = itemView.findViewById(R.id.tv_title);

        }
    }
}
