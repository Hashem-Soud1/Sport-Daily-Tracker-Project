package com.example.activedaytracker.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiConnection {

    private static RestApiConnection instance = null;
    private ApiService myAPIService;


    private String BaseUrl="https://192.168.0.108/ADT/";
    public static String photoUrl="http://192.168.0.108/ADT/photo/";

    private RestApiConnection() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseUrl)                .
                addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUnsafeOkHttpClient())
                .build();
        myAPIService = retrofit.create(ApiService.class);
    }


    public static RestApiConnection getInstance() {
        if (instance == null) {
            synchronized (RestApiConnection.class){
                instance = new RestApiConnection();
            }
        }
        return instance;
    }

    public ApiService getMyApi() {
        return myAPIService;
    }

    public static Interceptor logHttpRequest(HttpLoggingInterceptor.Level level) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

//        set your desired log level
        logging.setLevel(level);

        return logging;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder
                    .addInterceptor(logHttpRequest(HttpLoggingInterceptor.Level.BODY))
                    // To see the messages on logcat.
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

