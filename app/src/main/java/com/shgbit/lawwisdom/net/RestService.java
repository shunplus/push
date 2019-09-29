package com.shgbit.lawwisdom.net;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by xushun on  2019/6/1 16:42.
 * Email：shunplus@163.com
 * Des：
 */
public interface RestService {
    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> parms);
}
