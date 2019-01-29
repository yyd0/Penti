package com.yyd.penti.ui.duanzi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yyd.penti.R;
import com.yyd.penti.data.PentiReq;
import com.yyd.penti.data.bean.DuanZi;
import com.yyd.penti.ui.lehuo.LehuoAdapter;
import com.yyd.penti.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.yyd.penti.ui.widget.DividerItemDecoration.HORIZONTAL_LIST;

public class DuanziFragment extends Fragment {
    private RecyclerView recyclerView;
    private Disposable disposable;
    private DialogFragment dialogFragment;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page =1;
    private DuanziAdapter adapter;
    List<DuanZi.DataBean> dataBeanList = new ArrayList<>();
    private int lastVisibleItemPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        dialogFragment = new DialogFragment();
        swipeRefreshLayout = ((SwipeRefreshLayout) view.findViewById(R.id.srl));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            page = 1;
            swipeRefreshLayout.setRefreshing(true);
            adapter.notifyItemRangeRemoved(0, dataBeanList.size());
            dataBeanList.clear();
            getData();
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    // load more
                    page = page + 1;
                    getData();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
        return view;
    }

    private void getData() {
        disposable = PentiReq.getInstance().getDuanZi(page, 10)
                .doOnSubscribe(disposable1 -> dialogFragment.show(getFragmentManager(), null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(duanZi -> {
                    dialogFragment.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.addData(duanZi.getData());
                }, throwable -> {
                    swipeRefreshLayout.setRefreshing(false);
                    dialogFragment.dismiss();
                    Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new DuanziAdapter(getActivity(), dataBeanList);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(true);
        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
