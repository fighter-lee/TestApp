package top.fighter_lee.testapp.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <Retrofit结合OKHttp实现>
 * <p>
 * 关于OkHttp的Interceptor：https://github.com/square/okhttp/wiki/Interceptors
 * <p>
 * 缓存相关： http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0115/3873.html
 * <p>
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0131/3930.html
 * <p>
 * 网络层处理类，主要是创建Retrofit对象，并添加Okhttp拦截等...
 *
 * @data: 2016/2/17 11:06
 * @version: V1.0
 */
public class RetrofitDao {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private Retrofit mRetrofit;

    public static Retrofit buildRetrofit(IBuildPublicParams iBuildPublicParams, CookieJar cookieJar) {
        return new RetrofitDao(iBuildPublicParams, cookieJar).mRetrofit;
    }

    public static Retrofit buildRetrofit(IBuildPublicParams iBuildPublicParams) {
        return new RetrofitDao(iBuildPublicParams, new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                return new ArrayList<>();
            }
        }).mRetrofit;
    }

    private RetrofitDao(IBuildPublicParams iBuildPublicParams, CookieJar cookieJar) {
        if (mRetrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .cookieJar(cookieJar)
                    .addInterceptor(new HttpInterceptor(iBuildPublicParams))
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://app.412988.com/Lottery_server/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
    }

    public interface IBuildPublicParams {
        HttpUrl.Builder buildPublicParams(HttpUrl.Builder builder);
    }
}
