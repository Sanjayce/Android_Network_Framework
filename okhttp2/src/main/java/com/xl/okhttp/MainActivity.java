package com.xl.okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
 * 关于OkHttp2.* ~3.*网络框架
 * 1  OkHttpClient httpClient = new OkHttpClient();
 * 2  Request request = new Request.Builder().url(**).build();
 * 3  Call call =   httpClient.newCall(request);
 * 区别：使用post时，需多添加RequestBody到Request中
 */

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView2);

        //getAscyHttp("http://www.hao123.com");
        //getSyncHttp("http://www.hao123.com");
        //postAsynHttp("http://api.1-blog.com/biz/bizserver/article/list.do");
        //setCacheForOkHttp("http://www.hao123.com");
        fun();
    }

    private void fun() {

        // 封装后演示
        OkHttpEngine engine = OkHttpEngine.getmInstance();
        engine.setHttpCache(this);
        engine.getAscyHttp("http://www.hao123.com", new ResultCallback() {
            @Override
            public void onFailure(Request request, Exception e) {
                Log.e("onFailure", request.toString());
            }

            @Override
            public void onResponse(Response response) {
                textView.setText(response.toString());
                Toast.makeText(getApplication(), "请求成功", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 异步GET请求
     */
    private void getAscyHttp(String url) {
        try {
            //获取OkHttpClient
            OkHttpClient httpClient = new OkHttpClient();
            //获取请求Request
            final Request request = new Request.Builder().url(url).build();
            //请求回调
            Call call = httpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.e("onFailure", request.toString());
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String str = response.body().string();//获取响应文数据
                    Log.i("onResponse", str);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplication(), "请求成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步GET请求
     */
    private void getSyncHttp(String url) {
        try {
            //获取OkHttpClient
            OkHttpClient httpClient = new OkHttpClient();
            //获取请求Request
            final Request request = new Request.Builder().url(url).build();
            //请求回调
            Call call = httpClient.newCall(request);
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
    private void postAsynHttp(String url) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //请求报文
        RequestBody formBody = new FormEncodingBuilder().add("size", "10").build();
        //添加请求报文到请求
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String str = response.body().string();
                Log.i("onResponse", str);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 缓存设置，超时时间
     */

    private void setCacheForOkHttp(String str) {
        OkHttpClient httpClient = new OkHttpClient();

        File sdcache = getExternalCacheDir();//缓存路径
        int cacheSize = 10 * 1024 * 1024;//缓存大小
        httpClient.setCache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        //设置链接，响应超时
        httpClient.setConnectTimeout(15, TimeUnit.SECONDS);
        httpClient.setWriteTimeout(20, TimeUnit.SECONDS);
        httpClient.setReadTimeout(20, TimeUnit.SECONDS);

        final Request request = new Request.Builder().url(str).build();
        //.cacheControl(CacheControl.FORCE_NETWORK) //只读取网络数据

        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("onFailure", request.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.cacheResponse() != null) {
                    String str = response.cacheResponse().toString();
                    Log.i("onResponse", "cache" + str);
                } else {
                    String str = response.networkResponse().toString();
                    Log.i("onResponse", "network" + str);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
