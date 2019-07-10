package com.example.hp.challengecup.api.cloudmusic;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CloudMusicApi {
    String host = "http://39.108.123.251:8080/";

    @FormUrlEncoded
    @POST("CloudMusicProject/login.do")
    Observable<LoginInfo> login(@Field("phonenum") String phonenum,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("CloudMusicProject/register.do")
    Observable<LoginInfo> register(@Field("username")String username,
                                   @Field("phonenum")String phonenum,
                                   @Field("password")String password);
}
