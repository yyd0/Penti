package com.yyd.penti.data;

import android.content.Context;

import com.yyd.penti.App;
import com.yyd.penti.data.bean.DuanZi;
import com.yyd.penti.data.bean.LeHuo;
import com.yyd.penti.data.bean.LoadingImg;
import com.yyd.penti.data.bean.TuGua;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class PentiReq {
    private static final String URL_BASE = "http://appb.dapenti.com/";
    private static final PentiReq ourInstance = new PentiReq();
    private final PentiService pentiService;

    public static PentiReq getInstance() {
        return ourInstance;
    }

    private PentiReq() {
        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new HttpLoggingInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pentiService = retrofit.create(PentiService.class);
    }
    public Observable<LoadingImg> getLoadingImg() {
        return pentiService.getLoadingImg();
    }

    public Observable<TuGua> getTuGua(int p) {
        return pentiService.getTuGua(p, 10);
    }

    public Observable<DuanZi> getDuanZi(int p, int limit) {
        return pentiService.getDuanZi(p, limit);
    }

    public Observable<LeHuo> getLeHuo(int p, int limit) {
        return pentiService.getLeHuo(p, limit);
    }
}
