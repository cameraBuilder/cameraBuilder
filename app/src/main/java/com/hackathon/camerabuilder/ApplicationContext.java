package com.hackathon.camerabuilder;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LifecycleObserver;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.hackathon.camerabuilder.api.Repository;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import static org.apache.http.conn.ssl.SSLSocketFactory.SSL;


public class ApplicationContext extends Application implements LifecycleObserver {

    private static ApplicationContext applicationContext;
    public Repository repository;

    // to let all requests pass and trust all devices for sake of development
    final  static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {

                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                               String authType) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                               String authType) {
                }
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };



    public ApplicationContext() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        final SSLContext sslContext;
        applicationContext = this;
        try {
            sslContext = SSLContext.getInstance(SSL);
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
            OkHttpClient httpClient = builder.connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(1200, TimeUnit.SECONDS)
                    .authenticator((route, response) -> null)
                    .build();
            repository = new Repository(getSharedPreferences("com.hackathon.camerabuilder", Context.MODE_PRIVATE), httpClient);
            ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, httpClient).build();
            Fresco.initialize(this, config);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }



    public static ApplicationContext getInstance() {
        if (applicationContext == null) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext ;
    }

}
