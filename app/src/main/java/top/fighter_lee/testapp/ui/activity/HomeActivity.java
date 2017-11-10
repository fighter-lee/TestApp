package top.fighter_lee.testapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import top.fighter_lee.testapp.R;
import top.fighter_lee.testapp.base.BaseActivity;
import top.fighter_lee.testapp.base.WebviewFragment;
import top.fighter_lee.testapp.inter.FragmentKeyDown;
import top.fighter_lee.testapp.inter.WebviewBackListener;
import top.fighter_lee.testapp.ui.fragment.HomeFragment;
import top.fighter_lee.testapp.ui.fragment.PageFragment;
import top.fighter_lee.testapp.utils.FragmentUtils;
import top.fighter_lee.testapp.utils.SPUtils;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    public static final String SHARE_URL = "share_url";
    @BindView(R.id.fl_home)
    FrameLayout flHome;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.container)
    ConstraintLayout container;
    private Context context = this;
    boolean isExit;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (FragmentUtils.getTopShowFragment(getSupportFragmentManager()).toString() == homeFragment.toString()) {
                        FragmentUtils.hideAllShowFragment(homeFragment);
                    }
                    return true;
                case R.id.navigation_pay:
                    FragmentUtils.hideAllShowFragment(pageFragment1);
                    return true;

                case R.id.navigation_refresh:
                    for (WebviewBackListener mListener : mListeners) {
                        if (mListener != null) {
                            mListener.pressRefresh();
                        }
                    }
                    mHandler.sendEmptyMessageDelayed(0, 2000);
                    return true;
                case R.id.navigation_back:
                    onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK,KeyEvent.KEYCODE_BACK));
                    mHandler.sendEmptyMessageDelayed(0, 2000);
                    return true;
                case R.id.navigation_share:
                    mHandler.sendEmptyMessageDelayed(0, 2000);
                    Intent textIntent = new Intent(Intent.ACTION_SEND);
                    textIntent.setType("text/plain");
                    textIntent.putExtra(Intent.EXTRA_TEXT, "135彩票："+new SPUtils("Test").getString(SHARE_URL));
                    startActivity(Intent.createChooser(textIntent, "分享"));
                    return true;
            }
            return false;
        }
    };
    private HomeFragment homeFragment;
    private ArrayList<WebviewBackListener> mListeners = new ArrayList<>();
    private HomeFragment pageFragment1;

    @Override
    protected void initView(Bundle savedInstanceState) {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        homeFragment = new HomeFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(PageFragment.KEY_WEB_URL, "http://m.zhcw.com/");
        bundle2.putInt(HomeFragment.KEY_PAGE_NUM, 1);
        homeFragment.setArguments(bundle2);

        Bundle bundle1 = new Bundle();
        bundle1.putString(PageFragment.KEY_WEB_URL, "http://m.500.com/info/article/");
        bundle1.putInt(HomeFragment.KEY_PAGE_NUM, 2);
        pageFragment1 = new HomeFragment();
        pageFragment1.setArguments(bundle1);

        FragmentUtils.addFragment(getSupportFragmentManager(), pageFragment1, R.id.fl_home, true);
        FragmentUtils.addFragment(getSupportFragmentManager(), homeFragment, R.id.fl_home, false);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Fragment fragment = FragmentUtils.getTopShowFragment(getSupportFragmentManager());
            if (fragment instanceof WebviewFragment) {
                WebviewFragment mAgentWebFragment = (WebviewFragment) fragment;
                FragmentKeyDown mFragmentKeyDown = mAgentWebFragment;
                if (mFragmentKeyDown.onFragmentKeyDown(keyCode, event)) {
                    return true;
                } else {
                    exit();
                    return super.onKeyDown(keyCode, event);
                }
            }
//            exit();
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);

    }

    public void exit() {
//        if (!isExit) {
//            isExit = true;
//            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//            mHandler.sendEmptyMessageDelayed(0, 2000);
//        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
//        }
    }


    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
            navigation.setSelectedItemId(R.id.navigation_home);
        }

    };

    public void setWebviewListener(WebviewBackListener listener) {

        mListeners.add(listener);
    }
}
