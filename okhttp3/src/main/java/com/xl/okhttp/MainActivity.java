package com.xl.okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okhttp3.x 网络框架使用
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //指定文件类型
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.button1){
            getAsynGET("http://www.baidu.com");
            //getSyncHttp("http://www.baidu.com");
        }
        if(id == R.id.button2){
            getAsynPOST("http://www.hao123.com");
        }
        if(id == R.id.button3){
            try {
                postAsynFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(id == R.id.button4){
            setCacheForOkHttp("http://www.cn.bing.com");
        }

    }

    /**
     * 异步GET请求网络,与okhttp2.x一样，回调值不在ui线程里边
     */
    private void getAsynGET(String url){
        OkHttpClient httpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", "Failure.....");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();//获取响应文数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(str);
                        Toast.makeText(getApplication(), "GET请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 同步GET请求与okhttp2.x一样，回调值不在ui线程里边
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
     * 异步POST请求网络,与okhttp2.x的区别在于，okhttp3中弃用FormEncodingBuilder()来获取RequstBady
     * 用更强大的FormBody.Budilder代替
     */

    private void getAsynPOST(String url){
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("size", "10").build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call =  httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure","Failure......");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();//获取响应文数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(str);
                        Toast.makeText(getApplication(), "POST请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }





    /**
     * 缓存设置，超时时间
     * 和OkHttp2.x有区别的是不能通过OkHttpClient直接设置超时时间和缓存了，
     * 而是通过OkHttpClient.Builder来设置，
     * 通过builder配置好OkHttpClient后用builder.build()来返回OkHttpClient，
     * 所以我们通常不会调用new OkHttpClient()来得到OkHttpClient，而是通过builder.build()：
     *
     */

    private void setCacheForOkHttp(String str) {

        File sdcache = getExternalCacheDir();//缓存路径
        int cacheSize = 10 * 1024 * 1024;//缓存大小

        OkHttpClient.Builder builder  = new OkHttpClient.Builder();
        builder.cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        //设置链接，响应超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);

        OkHttpClient mOkHttpClient = builder.build();

        //只读取网络数据
        final Request request = new Request.Builder().url(str).cacheControl(CacheControl.FORCE_NETWORK).build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure", "Failure.....");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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

    /**
     * 异步上传文件
     * @throws IOException
     */

    private void postAsynFile() throws IOException {
        OkHttpClient mOkHttpClient=new OkHttpClient();
        InputStream is = getResources().getAssets().open("wangshu.txt");
        InputStreamReader isr = new InputStreamReader(is,"utf8");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null){
            builder.append(line);
        }
        String path = builder.toString();
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, path))
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //response.body().byteStream();  文件下载，通过方法得到一个输入流，然后在输出到SD卡里边
                Log.i("wangshu:",response.body().string());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
