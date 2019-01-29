package com.yyd.penti.ui.lehuo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yyd.penti.R;
import com.yyd.penti.data.bean.LeHuo;
import com.yyd.penti.ui.detail.LHDetailActivity;

import java.util.List;

public class LehuoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public void addData(List<LeHuo.DataBean> data) {
        int position = 0;
        if (dataBeanList.size() != 0) {
            position = dataBeanList.size();
        }
        dataBeanList.addAll(data);
        notifyItemRangeInserted(position, data.size());
    }

    public enum ITEM_TYPE {
        ITEM_TYPE_IMAGE,
        ITEM_TYPE_TEXT
    }
    private Context context;
    private List<LeHuo.DataBean> dataBeanList;

    public LehuoAdapter(Context context, List<LeHuo.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_TEXT.ordinal()) {
            return new TextViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lh_tv,
                    parent, false));
        } else {
            return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lh_img,
                    parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LeHuo.DataBean dataBean = dataBeanList.get(position);
        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).tv.setText(dataBean.getTitle());
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).tv.setText(dataBean.getTitle());
            Glide.with(context).load(dataBean.getImgurl()).into(((ImageViewHolder) holder).iv);
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LHDetailActivity.class);
            intent.putExtra("title", dataBean.getTitle());
            intent.putExtra("desc", dataBean.getDescription());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        LeHuo.DataBean dataBean = dataBeanList.get(position);
        if (dataBean.getImgurl() == null)
            return ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
        else
            return ITEM_TYPE.ITEM_TYPE_IMAGE.ordinal();
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public TextViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private ImageView iv;

        public ImageViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
