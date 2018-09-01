package com.xl.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.xl.retrofit.util.IpModle;
import com.xl.retrofit.util.IpService;
import com.xl.retrofit.util.IpServiceForPost;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Retrofit是Square公司开发的一款针对Android网络请求的框架，Retrofit2底层基于OkHttp实现
 */

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.ip);
        getRetrofitForGET("59.108.54.37");
        getRetrofitForPOST("59.108.54.37");
    }

    /**
     * Retrofit基本使用 ---> 定义一个请求网络接口，指定请求方法和获取回调方法
     * baseUrl加上之前 @GET(“getIpInfo.php”)接口定义的参数形成完整的请求地址；
     * addConverterFactory用于指定返回的参数数据类型，这里我们支持String和Gson类型。
     * 这里是异步请求网络，回调的Callback是运行在(UI)主线程的;
     * 如果想同步请求网络请使用 call.execute();
     * 如果想中断网络请求则可以使用 call.cancel()。
     */

    private void getRetrofitForGET(String ip) {

        String url = "http://ip.taobao.com/service/";

        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();
        httpClient.connectTimeout(5, TimeUnit.SECONDS);


        //创建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(url)
                //增加返回值为String的支持
                //.addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为json的支持
                //.addConverterFactory(GsonConverterFactory.create())
                .build();

        //用Retrofit创建接口文件
        IpService ipService = retrofit.create(IpService.class);
        Call<IpModle> call = ipService.getIpMsg(ip);

        //用Call请求网络并处理回调;
        call.enqueue(new Callback<IpModle>() {
            @Override
            public void onResponse(Call<IpModle> call, Response<IpModle> response) {
                String str = response.body().getData().toString();
                textView.setText(str);
            }

            @Override
            public void onFailure(Call<IpModle> call, Throwable t) {
                Log.e("onFailure", "Failure.......");
            }
        });
    }

    private void getRetrofitForPOST(String ip) {

        String url = "http://ip.taobao.com/service/";

        //创建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                //增加返回值为String的支持
                //.addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为json的支持
                //.addConverterFactory(GsonConverterFactory.create())
                .build();

        //用Retrofit创建接口文件
        IpServiceForPost ipService = retrofit.create(IpServiceForPost.class);
        Call<IpModle> call = ipService.getIpMsg(ip);

        //用Call请求网络并处理回调;
        call.enqueue(new Callback<IpModle>() {
            @Override
            public void onResponse(Call<IpModle> call, Response<IpModle> response) {
                String str = response.body().getData().toString();
                textView.setText(str);
            }

            @Override
            public void onFailure(Call<IpModle> call, Throwable t) {
                Log.e("onFailure", "Failure.......");
            }
        });
    }
}
