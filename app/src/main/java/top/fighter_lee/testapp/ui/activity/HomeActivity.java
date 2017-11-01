package top.fighter_lee.testapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import top.fighter_lee.testapp.R;
import top.fighter_lee.testapp.base.BaseActivity;
import top.fighter_lee.testapp.inter.WebviewBackListener;
import top.fighter_lee.testapp.ui.fragment.HomeFragment;
import top.fighter_lee.testapp.ui.fragment.PageFragment;
import top.fighter_lee.testapp.utils.FragmentUtils;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
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
                    FragmentUtils.hideAllShowFragment(homeFragment);
                    return true;
                case R.id.navigation_page:
                    FragmentUtils.hideAllShowFragment(pageFragment);
                    return true;
            }
            return false;
        }
    };
    private HomeFragment homeFragment;
    private WebviewBackListener mListener;
    private PageFragment pageFragment;

    @Override
    protected void initView(Bundle savedInstanceState) {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        homeFragment = new HomeFragment();
        pageFragment = new PageFragment();
        FragmentUtils.addFragment(getSupportFragmentManager(), pageFragment, R.id.fl_home,true);
        FragmentUtils.addFragment(getSupportFragmentManager(), homeFragment, R.id.fl_home,false);
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
