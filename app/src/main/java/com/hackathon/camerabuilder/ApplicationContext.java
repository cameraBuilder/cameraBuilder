package com.hackathon.camerabuilder;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LifecycleObserver;
import com.facebook.drawee.backends.pipeline.Fresco;
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
    final  TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                               String authType) {
                }

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
        Fresco.initialize(this);
        applicationContext = this;
    }


    public static ApplicationContext getInstance() {
        if (applicationContext == null) {
            applicationContext = new ApplicationContext();
        }
        if (applicationContext.repository == null) {
            final SSLContext sslContext;
            try {
                // set our dummy ssl certificate that trust all :)
                sslContext = SSLContext.getInstance(SSL);
                sslContext.init(null, applicationContext.trustAllCerts, new java.security.SecureRandom());
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) applicationContext.trustAllCerts[0]);
                builder.hostnameVerifier((hostname, session) -> true);
                OkHttpClient httpClient = builder.connectTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(1200, TimeUnit.SECONDS)
                        .build();
                applicationContext.repository = new Repository(applicationContext.getSharedPreferences("com.hackathon.camerabuilder", Context.MODE_PRIVATE), httpClient);
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }
        }
        return applicationContext ;
    }

}
