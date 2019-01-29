package com.yyd.penti.ui.duanzi;

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
import com.yyd.penti.data.bean.LeHuo;
import com.yyd.penti.ui.tugua.TuguaAdapter;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

public class DuanziAdapter extends RecyclerView.Adapter<DuanziAdapter.ViewHolder> {

    private Context context;
    private List<DuanZi.DataBean> dataBeanList;

    public DuanziAdapter(Context context, List<DuanZi.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
    }

    @NonNull
    @Override
    public DuanziAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dz, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DuanziAdapter.ViewHolder viewHolder, int i) {
        DuanZi.DataBean dataBean = dataBeanList.get(i);
        viewHolder.textView.setHtml(dataBean.getDescription(), new HtmlHttpImageGetter(viewHolder.textView));
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    public void addData(List<DuanZi.DataBean> data) {
        int position = 0;
        if (dataBeanList.size() != 0) {
            position = dataBeanList.size();
        }
        dataBeanList.addAll(data);
        notifyItemRangeInserted(position, data.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HtmlTextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
