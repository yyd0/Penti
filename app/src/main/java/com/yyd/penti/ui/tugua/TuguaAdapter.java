package com.yyd.penti.ui.tugua;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yyd.penti.R;
import com.yyd.penti.data.bean.TuGua;
import com.yyd.penti.ui.detail.DetailActivity;

import java.util.List;

public class TuguaAdapter extends RecyclerView.Adapter<TuguaAdapter.ViewHolder> {
    private List<TuGua.DataBean> dataBeans;
    private Context context;

    public TuguaAdapter(List<TuGua.DataBean> dataBeans, Context context) {
        this.dataBeans = dataBeans;
        this.context = context;
    }

    public void setDataBeans(List<TuGua.DataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tugua, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TuGua.DataBean dataBean = dataBeans.get(i);
        viewHolder.tvTitle.setText(dataBean.getTitle());
        Glide.with(context).load(dataBean.getImgurl()).apply(RequestOptions.noAnimation()).into(viewHolder.ivImg);
//        Glide.with(context).load(dataBean.getImgurl()).apply(RequestOptions.centerCropTransform().centerCrop()).into(viewHolder.ivImg);
        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("title", dataBean.getTitle());
            intent.putExtra("img", dataBean.getImgurl());
            intent.putExtra("desc", dataBean.getDescription());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public void addItem(TuGua.DataBean dataBean) {
        dataBeans.add(dataBean);
        notifyItemInserted(dataBeans.size()-1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivImg = itemView.findViewById(R.id.iv_img);
        }
    }
}
