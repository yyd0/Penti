package com.yyd.penti.ui.yitu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yyd.penti.R;
import com.yyd.penti.data.YiTuService;
import com.yyd.penti.data.bean.YiTu;
import com.yyd.penti.ui.widget.DividerItemDecoration;
import com.yyd.penti.ui.widget.ProgressDialogFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class YituFragment extends Fragment {
    private RecyclerView recyclerView;
    private Disposable disposable;
    private List<YiTu> dataBeans = new ArrayList<>();
    private YituAdapter adapter;
    private int page =1;
    private int lastVisibleItemPosition;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialogFragment dialogFragment;
    private static final String TAG = "YituFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv, container, false);
        dialogFragment = new ProgressDialogFragment();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
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
        swipeRefreshLayout = ((SwipeRefreshLayout) view.findViewById(R.id.srl));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            page = 1;
            swipeRefreshLayout.setRefreshing(true);
            adapter.notifyItemRangeRemoved(0, dataBeans.size());
            dataBeans.clear();
            getData();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new YituAdapter(getActivity(), dataBeans);
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        disposable = YiTuService.getYiTus(page)
                .doOnSubscribe(disposable1 -> dialogFragment.show(getFragmentManager(), null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(yiTu -> {
                    adapter.addItem(yiTu);
                    Log.d(TAG, "getData: on next");
                }, throwable -> {
                    Log.d(TAG, "getData: on error");
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    dialogFragment.dismiss();
                }, () -> {
                    Log.d(TAG, "getData: on complete");
                    swipeRefreshLayout.setRefreshing(false);
                    dialogFragment.dismiss();
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
