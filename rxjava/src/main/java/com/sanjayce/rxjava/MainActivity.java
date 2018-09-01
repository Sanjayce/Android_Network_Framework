package com.sanjayce.rxjava;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * RxJava 1.0版本的基本使用
 * RxAndroid 1.0
 */

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        useRxJava();

    }

    /**
     * 创建观察者
     */

    private void getObserver() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(String s) {
            }
        };

        Log.e("Observer:", observer.toString());
    }

    /**
     * 创建被观察者
     */

    private Observable getObservable() {

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("");
                subscriber.onCompleted();
            }
        });
        Log.e("Observable:", observable.toString());
        return observable;
    }

    /**
     * 创建订阅
     */

    private void getSubscribe() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }
        };
        Log.e("Subscriber:", subscriber.toString());
    }

    /**
     * RxJava基本使用:观察者与订阅作用一致，调用其中一个即可；区别：订阅有onStart(),unsubscribe():
     */

    private void useRxJava() {
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(R.mipmap.ic_launcher);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())// 指定 subscribe()发生在 IO 线程
                .doOnNext(new Action1<Drawable>() {
                    @Override
                    public void call(Drawable drawable) {
                        if (drawable != null) {
                            int w = drawable.getMinimumWidth();
                            int h = drawable.getMinimumHeight();
                            Log.e("W/H:", w + "/" + h);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onNext(Drawable drawable) {
                        imageView.setImageDrawable(drawable);
                    }

                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "OK!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
