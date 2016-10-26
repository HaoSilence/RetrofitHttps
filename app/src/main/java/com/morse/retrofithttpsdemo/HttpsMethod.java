package com.morse.retrofithttpsdemo;

import android.content.Context;

import java.io.IOException;

import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author：Morse
 * time：2016/9/14 21:31
 * Descripte：
 */
public class HttpsMethod {

    public static HttpsMethod getInstance() {
        return HttpsInstance.mInstance;
    }

    public void createHeader(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient
                .Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS));

        //添加SSL校验
        builder = socketFactory(context, builder);

        //添加头
        builder = header(builder);

        builder = certificatePinner(builder);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
    }

    /**
     * 添加头元素
     *
     * @param builder
     * @return
     */
    private OkHttpClient.Builder header(OkHttpClient.Builder builder) {
        return builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("mac", "f8:00:ea:10:45")
                        .addHeader("uuid", "gdeflatfgfg5454545e")
                        .addHeader("userId", "Fea2405144")
                        .addHeader("netWork", "wifi")
                        .build();
                return chain.proceed(request);
            }
        });
    }

    /**
     * 添加证书
     *
     * @param context
     * @param builder
     * @return
     */
    public OkHttpClient.Builder socketFactory(Context context, OkHttpClient.Builder builder) {
        return builder.socketFactory(HttpsFactroy.getSSLSocketFactory_Certificate(context, "BKS", R.raw.srca))
                .hostnameVerifier(HttpsFactroy.getHostnameVerifier(new String[]{}));
    }

    private OkHttpClient.Builder certificatePinner(OkHttpClient.Builder builder) {
        return builder.certificatePinner(new CertificatePinner.Builder()
                .add("", "sha1/DmxUShsZuNiqPQsX2Oi9uv2sCnw=")
                .add("", "sha1/SXxoaOSEzPC6BgGmxAt/EAcsajw=")
                .add("", "sha1/blhOM3W9V/bVQhsWAcLYwPU6n24=")
                .add("", "sha1/T5x9IXmcrQ7YuQxXnxoCmeeQ84c=")
                .build());
    }

    private static class HttpsInstance {
        public static HttpsMethod mInstance = new HttpsMethod();
    }

}
