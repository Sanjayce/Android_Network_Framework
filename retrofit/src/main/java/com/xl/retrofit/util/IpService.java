package com.xl.retrofit.util;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Retrofit提供的请求方式注解有@GET和@POST等，分别代表GET请求和POST请求;
 * 我们在这里访问的界面是“getIpInfo.php”。
 * 参数注解有@PATH和@Query等，@Query就是我们的请求的键值对的设置;
 * 在这里@Query(“ip”)代表键，“String ip”则代表值。
 */

public interface IpService {

    @GET("getIpInfo.php")
    Call<IpModle> getIpMsg(@Query("ip") String ip);
}

/**

//@QueryMap 将所有的参数集成在一个Map统一传递
interface BlueService {
    @GET("book/service")
    Call<IpModle> getSearchBooks(@QueryMap Map<String, String> options);
}

//@Path 用来替换路径。

interface ApiStores {
    @GET("adat/sk/{cityId}.html")
    Call<IpModle> getWeather(@Path("cityId") String cityId);
}

//@Body与@POST 注解一起使用，提供查询主体内容

interface ApiService {
    @POST("client/shipper/getCarType")
    Call<IpModle> getCarType(@Body String apiInfo);
}

//@Headers用来添加头部信息
interface SomeService {
    @GET("some/endpoint")
    @Headers( { , } )  //静态添加多个请求头
    Call<IpModle> someEndpoint(@Header("Location") String location);
}
 **/