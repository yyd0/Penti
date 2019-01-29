package com.yyd.penti.ui.tugua;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.yyd.penti.data.PentiService;
import com.yyd.penti.data.bean.TuGua;
import com.yyd.penti.ui.widget.DividerItemDecoration;
import com.yyd.penti.ui.widget.ProgressDialogFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TuguaFragment extends Fragment {
    private static final String TAG = "TuguaFragment";
    List<TuGua.DataBean> dataBeans = new ArrayList<>();
    private RecyclerView recyclerView;
    private Disposable disposable;
    private ProgressDialogFragment dialogFragment;
    private int lastVisibleItemPosition;
    private int page = 1;
    private TuguaAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogFragment = new ProgressDialogFragment();
        View view = inflater.inflate(R.layout.fragment_rv, container, false);
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
        adapter = new TuguaAdapter(dataBeans, getActivity());
        recyclerView.setAdapter(adapter);
        getData();

    }

    private void getData() {
        disposable = PentiReq.getInstance().getTuGua(page)
                .doOnSubscribe(disposable -> dialogFragment.show(getFragmentManager(), null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(tuGua -> tuGua.getError() == 0)
                .flatMap((Function<TuGua, ObservableSource<TuGua.DataBean>>) tuGua -> Observable.fromIterable(tuGua.getData()))
                .filter(dataBean -> !"AD".equals(dataBean.getTitle()))
                .subscribe(adapter::addItem, throwable -> {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    dialogFragment.dismiss();
                }, () -> {
                    swipeRefreshLayout.setRefreshing(false);
                    dialogFragment.dismiss();
                    if (dataBeans.size() == 0)
                        showEmptyView();
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private void showEmptyView() {

    }

}
