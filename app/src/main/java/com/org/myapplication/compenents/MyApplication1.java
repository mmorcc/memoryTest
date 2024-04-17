package com.org.myapplication.compenents;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.marswin89.marsdaemon.DaemonApplication;
import com.marswin89.marsdaemon.DaemonConfigurations;
import com.org.myapplication.utils.SharedPreferencesUtils;
import com.org.myapplication.utils.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Implementation 1<br/>
 * override one method is ok.<br/>
 *
 * Created by Mars on 12/24/15.
 */
public class MyApplication1 extends DaemonApplication {
    private static MyApplication1 INSTANCE;
    private static Retrofit mRetrofit;

    private static SharedPreferencesUtils sp;
    private static String ipStr="192.168.1.10";
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        sp=SharedPreferencesUtils.getInstance("isLogin");

        reSetNet();
        setupCrashHandler();
        Util.initData();
    }
    private void setupCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            @Override public void uncaughtException(Thread thread, Throwable throwable) {
                handleCrash(throwable);
            }
        });
    }
    private void handleCrash(Throwable throwable) { // Handle the crash here (e.g., save crash report, log the crash, etc.)
        saveCrashReport(throwable);
        logCrash(throwable);
    }
    private void saveCrashReport(Throwable throwable)
    {
        Util.writeFileToLocalStorage(throwable.getMessage());
        // Save the crash report to a file or send it to a remote server
         }
     private void logCrash(Throwable throwable)
    {
        // Log the crash using a logging library } private void exitApplication() { System.exit(1); // Terminate the application }
    }
    public static SharedPreferencesUtils getSp() {
        return sp;
    }
    public static Retrofit getNet() {
        return mRetrofit;
    }
    public static void reSetNet(){
        if(sp.get("ip")==null||sp.get("ip").equals("")){

        }else {
            ipStr=sp.get("ip");
        }

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    Log.e("OKHttp-----", text);
                    writeFileToLocalStorage("call_log.txt",text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("OKHttp----ee-", message);
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
                .baseUrl("http://"+ipStr+":8083/")

                .client(okHttpClient)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())



                .build();
    }

    public static MyApplication1 get() {
        return INSTANCE;
    }


    /**
     * you can override this method instead of {@link android.app.Application attachBaseContext}
     * @param base
     */
    @Override
    public void attachBaseContextByDaemon(Context base) {
        super.attachBaseContextByDaemon(base);
    }


    /**
     * give the configuration to lib in this callback
     * @return
     */
    @Override
    protected DaemonConfigurations getDaemonConfigurations() {
        DaemonConfigurations.DaemonConfiguration configuration1 = new DaemonConfigurations.DaemonConfiguration(
                "com.marswin89.marsdaemon.demo:process1",
                Service1.class.getCanonicalName(),
                Receiver1.class.getCanonicalName());

        DaemonConfigurations.DaemonConfiguration configuration2 = new DaemonConfigurations.DaemonConfiguration(
                "com.marswin89.marsdaemon.demo:process2",
                Service2.class.getCanonicalName(),
                Receiver2.class.getCanonicalName());

        DaemonConfigurations.DaemonListener listener = new MyDaemonListener();
        //return new DaemonConfigurations(configuration1, configuration2);//listener can be null
        return new DaemonConfigurations(configuration1, configuration2, listener);
    }


    class MyDaemonListener implements DaemonConfigurations.DaemonListener{
        @Override
        public void onPersistentStart(Context context) {
        }

        @Override
        public void onDaemonAssistantStart(Context context) {
        }

        @Override
        public void onWatchDaemonDaed() {
        }
    }
    public static void writeFileToLocalStorage(String fileName, String content) {
//        try {
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
//            FileWriter writer = new FileWriter(file, true);
//            writer.append(content);
//            writer.flush();
//            writer.close();
//            // 文件写入成功
//        } catch (IOException e) {
//            e.printStackTrace();
//            // 文件写入发生错误
//        }
    }
}
