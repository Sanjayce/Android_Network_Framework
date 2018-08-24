package com.xl.moonvolley;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * 安卓网络框架Volley
 */

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.imageView);
        //获取创建消息列队对象
        mQueue = Volley.newRequestQueue(getApplicationContext());
        //useStringRequest("http://www.baidu.com");
        useJsonRequest("http://www.imooc.com/api/teacher?type=4&num=30");
        useImageRequst("http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg");
        //useImageLoader("http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg");
    }

    /**
     * StringRequest
     */

    private void useStringRequest(String url) {

        StringRequest mRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.getMessage(), error);
            }
        });
        mQueue.add(mRequest);
    }

    /**
     * JsonRequest
     */

    private void useJsonRequest(String url) {

        JsonObjectRequest mObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textView.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse", error.getMessage(), error);
                    }
                });
        mQueue.add(mObjectRequest);
    }

    /**
     * ImageRequest
     */

    private void useImageRequst(String url) {

        ImageRequest mImageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
               imageView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.getMessage(), error);
            }
        });
        mQueue.add(mImageRequest);
    }

    /**
     * ImageLoader
     */

    private void useImageLoader(String url){

        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {

            @Override
            public Bitmap getBitmap(String url) {
                Log.e("getBitmap", url);
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                Log.e("putBitmap", bitmap.toString());
            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        imageLoader.get(url,listener);
    }

}
