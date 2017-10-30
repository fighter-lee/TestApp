package top.fighter_lee.testapp.gloable;

import android.app.Application;
import android.content.Context;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * @author fighter_lee
 * @date 2017/10/30
 */
public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        initUmeng();

        initBmob();


    }

    private void initBmob() {
        BmobConfig config =new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("Your Application ID")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(10)
                .build();
        Bmob.initialize(config);
    }

    private void initUmeng() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }
}
