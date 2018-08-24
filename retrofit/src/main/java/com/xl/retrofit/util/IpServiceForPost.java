package com.xl.retrofit.util;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//传输数据类型为键值对：@Field
//@FormUrlEncoded注解来标明这是一个表单请求
public interface IpServiceForPost {
    @FormUrlEncoded
    @POST("getIpInfo.php")
    Call<IpModle> getIpMsg(@Field("ip") String first);
}