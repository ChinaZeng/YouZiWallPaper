package com.youzi.service.api.apis;

import com.youzi.service.api.resp.ListResp;
import com.youzi.service.api.resp.Resp;
import com.youzi.service.api.resp.ThemeBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by zzw on 2019/1/11.
 * 描述:
 */
public interface ClientApi {

    @GET("app/loginPostThirdParty")
    Observable<Resp<String>> loginPostThirdParty(
            @Query("openType") String openType,
            @Query("openId") String openId,
            @Query("nickname") String nickname
    );


    @GET("app/bac_/getPageHomeThemeLst")
    Observable<Resp<ListResp<ThemeBean>>> getPageHomeThemeLst();

    @GET("/app/bac_/getPageFollowLst")
    Observable<Resp<ListResp<ThemeBean>>> getPageFollowLst(
            @Query("token") String token,
            @Query("currentPage") int currentPage,
            @Query("showCount") int showCount
    );

    @GET("/app/bac_/getClassifyLst")
    Observable<Resp<ListResp<ThemeBean>>> getClassifyLst();

    @GET("/app/checkToken")
    Observable<Resp<Object>> checkToken(@Query("token") String token);

    @GET("/app/bac_/insertDataCollection")
    Observable<Resp<Object>> insertDataCollection(
            @Query("Token") String token,
            @Query("home_theme_id") String homeThemeId
    );

    @GET("/app/bac_/insertDataFollow")
    Observable<Resp<Object>> insertDataFollow(
            @Query("Token") String token,
            @Query("home_theme_id") String homeThemeId
    );

}
