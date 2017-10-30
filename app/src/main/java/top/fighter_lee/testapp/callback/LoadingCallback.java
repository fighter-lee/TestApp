package top.fighter_lee.testapp.callback;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;

import top.fighter_lee.testapp.R;


public class LoadingCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }

    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
