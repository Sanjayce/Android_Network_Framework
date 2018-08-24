package com.xl.okhttp;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 *OkHttp封装，返回结果回调
 */

public abstract class ResultCallback<T> {

    public abstract void onFailure(Request request, Exception e);

    public abstract void onResponse(Response response);
}
