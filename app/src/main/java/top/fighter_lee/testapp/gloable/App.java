package top.fighter_lee.testapp.gloable;

import android.app.Application;
import android.content.Context;

import com.kingja.loadsir.core.LoadSir;

import cn.jpush.android.api.JPushInterface;
import top.fighter_lee.testapp.callback.ErrorCallback;
import top.fighter_lee.testapp.callback.LoadingCallback;

/**
 * @author fighter_lee
 * @date 2017/10/30
 */
public class App extends Application {

    public static Context context;
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initLoadView();

//        initBmob();

    }

    private void initLoadView() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
    }

//    private void initBmob() {
//        BmobConfig config =new BmobConfig.Builder(this)
//                //设置appkey
//                .setApplicationId("bfedbae1aa244189c0db3c691d779687")
//                //请求超时时间（单位为秒）：默认15s
//                .setConnectTimeout(10)
//                .build();
//        Bmob.initialize(config);
//    }

}
