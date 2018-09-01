package com.sanjayce.rxjava_retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import com.sanjayce.rxjava_retrofit.entity.HttpResult;
import com.sanjayce.rxjava_retrofit.http.ServerApi;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 原生的RxJava与Retrofit混合使用网络操作!
 */
public class MainActivity extends AppCompatActivity {

    //使用注解，反射用于组件
    @Bind(R.id.buttons)
    Button button;

    @Bind(R.id.text_views)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);//绑定ButterKnife
    }

    @OnClick(R.id.buttons)
    public void onClick(){
        getMoveNetWork();
    }

    /**
     * RxJava与Retrofit结合
     */
    private void getMoveNetWork(){
        String url = "https://api.douban.com/v2/movie/";
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();
        httpClient.connectTimeout(5, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();
        ServerApi serverApi =  retrofit.create(ServerApi.class);
        serverApi.getTopMovie(0,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError",e.toString());
                    }

                    @Override
                    public void onNext(HttpResult httpResult) {
                        textView.setText( httpResult.toString());
                        Log.e("onNext",httpResult.toString());
                    }
                });
    }
}
