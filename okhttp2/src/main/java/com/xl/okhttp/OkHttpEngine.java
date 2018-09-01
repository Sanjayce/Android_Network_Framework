package com.xl.okhttp;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp封装，获取OkHttpClient对象，设置响应属性，回调返回值函数到UI线程
 */

public class OkHttpEngine {

    private static OkHttpEngine mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    /**
     * 单例获取OkHttpClient对象
     *
     * @return
     */

    public static OkHttpEngine getmInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpEngine();
        }
        return mInstance;
    }

    /**
     * 初始化请求属性
     */

    private OkHttpEngine() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(15, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(20, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
        mHandler = new Handler();
    }

    /**
     * 设置缓存机制
     *
     * @param context
     */
    public void setHttpCache(Context context) {
        File directory = context.getExternalCacheDir();
        int maxSize = 10 * 1024 * 1024;
        mOkHttpClient.setCache(new Cache(directory.getAbsoluteFile(), maxSize));
    }

    /**
     * 异步GET请求
     */
    public void getAscyHttp(String url,ResultCallback callback) {
        //获取请求Request
        final Request request = new Request.Builder().url(url).build();
        //请求回调
        Call call = mOkHttpClient.newCall(request);
        dealResult(call,callback);
    }

    /**
     * 同步GET请求
     */
    public void getSyncHttp(String url) {
        try {
            //获取请求Request
            final Request request = new Request.Builder().url(url).build();
            //请求回调
            Call call = mOkHttpClient.newCall(request);
            Response response = call.execute();
            if (response.isSuccessful()) {
                String str = response.body().toString();
                Log.i("Response", str);
            } else {
                Log.i("Response", "**********");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步POST请求
     */
    public void postAsynHttp(String url,ResultCallback callback) {
        //请求报文
        RequestBody formBody = new FormEncodingBuilder().add("size", "10").build();
        //添加请求报文到请求
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        dealResult(call,callback);
    }

    /**
     * 回调返回值函数
     *
     * @param call
     * @param callback
     */
    private void dealResult(Call call, final ResultCallback callback) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailedCallback(request, e, callback);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                sendSuccessCallback(response, callback);
            }

            //将响应值，直接转给ui线程

            private void sendSuccessCallback(final Response object, final ResultCallback callback) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onResponse(object);
                        }
                    }
                });
            }

            private void sendFailedCallback(final Request request, final Exception e, final ResultCallback callback) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null)
                            callback.onFailure(request, e);
                    }
                });
            }
        });
    }

}
