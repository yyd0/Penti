package com.yyd.penti.ui.yitu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yyd.penti.R;
import com.yyd.penti.data.bean.DuanZi;
import com.yyd.penti.data.bean.YiTu;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

public class YituAdapter extends RecyclerView.Adapter<YituAdapter.ViewHolder> {

    private Context context;
    private List<YiTu> dataBeanList;

    public YituAdapter(Context context, List<YiTu> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
    }

    @NonNull
    @Override
    public YituAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_yt, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YituAdapter.ViewHolder viewHolder, int i) {
        YiTu yiTu = dataBeanList.get(i);
        viewHolder.textView.setText(yiTu.getDesc());
        Glide.with(context).load(yiTu.getImgUrl()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public void addItem(YiTu yiTu) {
        dataBeanList.add(yiTu);
        notifyItemInserted(dataBeanList.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_title);
            imageView = itemView.findViewById(R.id.iv_img);
        }
    }
}
