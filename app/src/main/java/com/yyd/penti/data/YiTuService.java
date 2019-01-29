package com.yyd.penti.data;

import android.text.TextUtils;

import com.yyd.penti.data.bean.YiTu;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


/**
 * Created by le on 2016/9/21.
 */

public class YiTuService {
    private static final String TAG = "YiTuService";
    public static Observable<YiTu> getYiTus(int page) {
        String url = "http://www.dapenti.com/blog/blog.asp?name=tupian&page="+page;
        //https://www.dapenti.com/blog/readapp2.asp?name=tupian&id=137574
        Observable<YiTu> yiTuObservable = Observable.create((ObservableOnSubscribe<Document>) emitter -> {
            Connection connection = Jsoup.connect(url + page);
            Document document = connection.get();
            emitter.onNext(document);
            emitter.onComplete();
        }).map(document -> document.getElementsByClass("oblog_text"))
                .flatMap((Function<Elements, ObservableSource<Element>>) Observable::fromIterable)
                .filter(element -> !TextUtils.isEmpty(element.getElementsByTag("img").attr("src")))
                .map(element -> new YiTu(element.attr("src"), element.text()));

        return yiTuObservable;
    }

}
