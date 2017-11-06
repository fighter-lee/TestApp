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
import android.widget.Toast;

import butterknife.BindView;
import top.fighter_lee.testapp.R;
import top.fighter_lee.testapp.base.BaseActivity;
import top.fighter_lee.testapp.base.WebviewFragment;
import top.fighter_lee.testapp.inter.FragmentKeyDown;
import top.fighter_lee.testapp.inter.WebviewBackListener;
import top.fighter_lee.testapp.ui.fragment.HomeFragment;
import top.fighter_lee.testapp.utils.FragmentUtils;

public class CaiActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    @BindView(R.id.fl_home)
    FrameLayout flHome;
//    @BindView(R.id.navigation)
//    BottomNavigationView navigation;
    @BindView(R.id.container)
    ConstraintLayout container;

    public static boolean isshow = false;

    private Context context = this;
    boolean isExit;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
            }
            return false;
        }
    };
    private HomeFragment homeFragment;
    private WebviewBackListener mListener;

    @Override
    protected void initView(Bundle savedInstanceState) {
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        homeFragment = new HomeFragment();
        FragmentUtils.addFragment(getSupportFragmentManager(), homeFragment, R.id.fl_home, false);

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_cai;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment fragment = FragmentUtils.getTopShowFragment(getSupportFragmentManager());
        if (fragment instanceof WebviewFragment) {
            WebviewFragment mAgentWebFragment = (WebviewFragment) fragment;
            FragmentKeyDown mFragmentKeyDown = mAgentWebFragment;
            if (mFragmentKeyDown.onFragmentKeyDown(keyCode, event)) {
                return true;
            } else {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    exit();
                    return false;
                }
            }
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);

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

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }

    };

    public void setWebviewListener(WebviewBackListener listener) {
        this.mListener = listener;
    }
}
