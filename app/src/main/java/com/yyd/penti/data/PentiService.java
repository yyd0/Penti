package com.yyd.penti.data;

import com.yyd.penti.data.bean.DuanZi;
import com.yyd.penti.data.bean.LeHuo;
import com.yyd.penti.data.bean.LoadingImg;
import com.yyd.penti.data.bean.TuGua;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PentiService {
    @GET("index.php?s=/Home/api/loading_pic")
    Observable<LoadingImg> getLoadingImg();

    @GET("index.php?s=/Home/api/tugua")
    Observable<TuGua> getTuGua(@Query("p") int p, @Query("limit")int limit);

    @GET("index.php?s=/Home/api/duanzi")
    Observable<DuanZi> getDuanZi(@Query("p") int p, @Query("limit")int limit);

    @GET("index.php?s=/Home/api/lehuo")
    Observable<LeHuo> getLeHuo(@Query("p") int p, @Query("limit")int limit);
}
