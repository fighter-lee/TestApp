package top.fighter_lee.testapp.engine;

import okhttp3.HttpUrl;
import top.fighter_lee.testapp.info.NetApi;
import top.fighter_lee.testapp.net.NetConfig;
import top.fighter_lee.testapp.net.RetrofitDao;

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
                    netApi = RetrofitDao.buildRetrofit(new RetrofitDao.IBuildPublicParams() {
                        @Override
                        public HttpUrl.Builder buildPublicParams(HttpUrl.Builder builder) {
                            return Network.buildPublicParams(builder);
                        }
                    }).create(NetApi.class);
                }
            }
        }
        return netApi;
    }

//    public Network() {
//        //添加日志拦截，用于查看json数据
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(5000, TimeUnit.MILLISECONDS)
//                .connectTimeout(5000, TimeUnit.MILLISECONDS)
//                .build();
//
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
//
//        netApi = new Retrofit.Builder()
//                .baseUrl("http://app.412988.com/Lottery_server/")
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build()
//                .create(NetApi.class);

//    }

    private static HttpUrl.Builder buildPublicParams(HttpUrl.Builder builder) {
        builder.addQueryParameter("showapi_sign", NetConfig.TICKET_SECRET);
        builder.addQueryParameter("showapi_appid", NetConfig.TICKET_APP_ID);
        return builder;
    }
}
