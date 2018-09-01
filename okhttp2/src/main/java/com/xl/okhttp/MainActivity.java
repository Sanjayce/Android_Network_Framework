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

        engine.getSyncHttp("http://www.hao123.com");

        engine.postAsynHttp("http://www.hao123.com",new ResultCallback() {
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
}
