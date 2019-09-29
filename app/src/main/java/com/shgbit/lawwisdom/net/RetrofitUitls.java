package com.shgbit.lawwisdom.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by xushun on  2019/6/1 16:35.
 * Email：shunplus@163.com
 * Des：
 */
public class RetrofitUitls {
   private  String HOST="http://192.168.0.99:8080/";
    private static final int DEFAULT_TIMEOUT = 30;
    private static RetrofitUitls instance;
    private RestService apiService;
    private RetrofitUitls (){
        if (instance != null) {
            throw new RuntimeException();
        }
        OkHttpClient  okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(HOST)
                .build();
        apiService = retrofit.create(RestService.class);
    }

    private  static class RetrofitUtilsHold{
        private static  final RetrofitUitls instance=new RetrofitUitls();
    }

    public static  RetrofitUitls getInstance (){
        return  RetrofitUtilsHold.instance;
    }

   public RestService getApiService(){
        return apiService;
   }


}
