package com.youzi.service.di;

import android.content.Context;

import com.youzi.framework.common.util.persistentcookie.PersistentCookieJarManager;
import com.youzi.service.BuildConfig;
import com.youzi.service.api.ApiConverterFactory;
import com.youzi.service.api.apis.ClientApi;
import com.youzi.service.api.intercepters.ClientApiParamsInterceptor;

import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by zzw on 2018/1/11
 */
@Module
public class ApiModule {

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
        String decodeMessage = message;
        try {
            decodeMessage = URLDecoder.decode(message, "UTF-8");
        } catch (Exception ignored) {
        }

        Platform.get().log(Platform.INFO, decodeMessage, null);
    });

    @Provides
    @Singleton
    @Named("client")
    public static OkHttpClient provideClientOkHttpClient(Context context) {
//        HttpsTrustManager trustManager = new HttpsTrustManager();
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
//                .addInterceptor(new LoginTokenInterceptor())
                .hostnameVerifier((s, sslSession) -> true)
                .addInterceptor(new ClientApiParamsInterceptor())
//                .sslSocketFactory(createSSLSocketFactory(trustManager), trustManager)
                .addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(PersistentCookieJarManager.getInstance(context))
                .build();
    }


    @Provides
    @Singleton
    @Named("client")
    public static Retrofit provideClientRetrofit(@Named("client") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ApiConverterFactory.create())
                .baseUrl(BuildConfig.HOST)
                .build();
    }


    @Provides
    @Singleton
    public static ClientApi provideClientApi(@Named("client") Retrofit retrofit) {
        return retrofit.create(ClientApi.class);
    }


    private static SSLSocketFactory createSSLSocketFactory(HttpsTrustManager httpsTrustManager) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{httpsTrustManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return ssfFactory;
    }

    public static class HttpsTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
