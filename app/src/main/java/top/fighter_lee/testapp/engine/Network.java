package top.fighter_lee.testapp.engine;

import top.fighter_lee.testapp.info.NetApi;

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

    }
}
