package top.fighter_lee.testapp.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import top.fighter_lee.testapp.R;
import top.fighter_lee.testapp.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void initView(Bundle savedInstanceState) {
        LoadService loadService = LoadSir.getDefault().register(this, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                // your retry logic
            }
        });

//        loadService.showSuccess();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }
}
