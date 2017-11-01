package top.fighter_lee.testapp.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.fighter_lee.testapp.info.NetApi;

/**
 * @author fighter_lee
 * @date 2017/11/1
 */
public class Network {

    private static NetApi netApi;

    public static NetApi getNetApi() {
        if (netApi == null) {
            synchronized (Network.class) {
                if (netApi == null) {
                    new Network();
                }
            }
        }
        return netApi;
    }

    public Network() {
        //添加日志拦截，用于查看json数据

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        netApi = new Retrofit.Builder()
                .baseUrl("http://app.412988.com/Lottery_server/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NetApi.class);

    }
}
