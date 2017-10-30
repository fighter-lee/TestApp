package top.fighter_lee.testapp.callback;


import com.kingja.loadsir.callback.Callback;

import top.fighter_lee.testapp.R;


public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}
