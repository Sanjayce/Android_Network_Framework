package com.sanjayce.rxjava_retrofit.http;

import com.sanjayce.rxjava_retrofit.entity.HttpResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


/**
 * 接口
 */

public interface ServerApi {
    @GET("top250")
    Observable<HttpResult> getTopMovie(@Query("start") int start, @Query("count") int count);
}
