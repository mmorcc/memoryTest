package com.org.myapplication;

import android.app.Application;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyApplication extends Application {
    private static MyApplication INSTANCE;
    private static Retrofit mRetrofit;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);



        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    Log.e("OKHttp-----", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("OKHttp-----", message);
                }
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.addInterceptor(interceptor);
//        client.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request()
//                        .newBuilder()
//                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                        .addHeader("Accept-Encoding", "gzip, deflate")
//                        .addHeader("Connection", "keep-alive")
//                        .addHeader("Accept", "*/*")
//                        .addHeader("Cookie", "add cookies here")
//                        .build();
//                return chain.proceed(request);
//            }
//
//        });
        OkHttpClient okHttpClient = client.build();

        ////步骤4:构建Retrofit实例
        mRetrofit = new Retrofit.Builder()
                //设置网络请求BaseUrl地址
                .baseUrl("http://192.168.137.1:8080/")
                .client(okHttpClient)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getNet() {
        return mRetrofit;
    }

    public static MyApplication get() {
        return INSTANCE;
    }
}
