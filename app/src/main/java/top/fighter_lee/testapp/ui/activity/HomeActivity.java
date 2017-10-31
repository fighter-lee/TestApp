package top.fighter_lee.testapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import top.fighter_lee.testapp.R;
import top.fighter_lee.testapp.base.BaseActivity;
import top.fighter_lee.testapp.inter.WebviewBackListener;
import top.fighter_lee.testapp.ui.fragment.HomeFragment;
import top.fighter_lee.testapp.utils.FragmentUtils;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    @BindView(R.id.fl_home)
    FrameLayout flHome;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.container)
    ConstraintLayout container;
    private SHARE_MEDIA share_media;
    private Context context = this;
    boolean isExit;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentUtils.showFragment(homeFragment);
                    return true;
                case R.id.navigation_back:
                    if (null != homeFragment && homeFragment == FragmentUtils.getTopShowFragment(getSupportFragmentManager())){
                        if(homeFragment.wvWeb.canGoBack()){
                            if (null != mListener){
                                mListener.pressBack();
                            }
                        }else{
                            onKeyDown(KeyEvent.KEYCODE_BACK,new KeyEvent(1,KeyEvent.KEYCODE_BACK));
                        }
                    }

                    return true;
                case R.id.navigation_refresh:

                    if (null != mListener){
                        mListener.pressRefresh();
                    }
                    return true;
                case R.id.navigation_pay:
                    Toast.makeText(context, "支付", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_share:
                    share();
                    return true;
            }
            return false;
        }
    };
    private HomeFragment homeFragment;
    private WebviewBackListener mListener;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        share_media = (SHARE_MEDIA) getIntent().getSerializableExtra("platform");
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        homeFragment = new HomeFragment();
        FragmentUtils.addFragment(getSupportFragmentManager(), homeFragment, R.id.fl_home);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    public void share() {
        new ShareAction(this)
                .withText("下载地址：www.baidu.com")
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Log.d(TAG, "onStart: "+share_media);
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Log.d(TAG, "onResult: "+share_media);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Log.d(TAG, "onError: "+share_media);
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Log.d(TAG, "onCancel: "+share_media);
                    }
                })
                .open();
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
            navigation.setSelectedItemId(R.id.navigation_home);
        }

    };

    public void setWebviewListener(WebviewBackListener listener){
        this.mListener = listener;
    }
}
